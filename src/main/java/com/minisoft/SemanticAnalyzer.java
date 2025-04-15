package com.minisoft;

import com.minisoft.symbol.SymbolEntity;
import com.minisoft.symbol.SymbolTable;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 * Semantic Analyzer for the MiniSoft compiler.
 * Performs type checking and validation on expressions and statements.
 * Tracks types of expressions throughout the AST and verifies type correctness.
 */
public class SemanticAnalyzer extends MiniSoftBaseListener {
    private SymbolTable symbolTable;
    private boolean hasErrors;
    private ParseTreeProperty<String> expressionTypes; // To track types of expressions

    /**
     * Creates a semantic analyzer with the provided symbol table
     */
    public SemanticAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.hasErrors = false;
        this.expressionTypes = new ParseTreeProperty<>();
    }

    /**
     * Returns whether any semantic errors were detected
     */
    public boolean hasErrors() {
        return hasErrors;
    }

    /**
     * Initialize condition type as Int (boolean equivalent)
     */
    @Override
    public void enterCondition(MiniSoftParser.ConditionContext ctx) {
        expressionTypes.put(ctx, "Int");
    }

    /**
     * Determines and stores types of primary expressions
     */
    @Override
    public void exitPrimaryExpression(MiniSoftParser.PrimaryExpressionContext ctx) {
        if (ctx.ID() != null) {
            String identifier = ctx.ID().getText();
            SymbolEntity entity = symbolTable.lookupSymbol(identifier);
            
            if (entity != null) {
                expressionTypes.put(ctx, entity.getDataType());
            } else {
                // Error already reported by SymbolTableBuilder
                expressionTypes.put(ctx, "unknown");
            }
        } else if (ctx.constValue() != null) {
            MiniSoftParser.ConstValueContext constValue = ctx.constValue();
            if (constValue.INT() != null || 
                (constValue.getChildCount() > 2 && !((Token)constValue.getChild(2)).getText().contains("."))) {
                expressionTypes.put(ctx, "Int");
            } else {
                expressionTypes.put(ctx, "Float");
            }
        } else if (ctx.expression() != null) {
            // Parenthesized expression - propagate type
            String exprType = expressionTypes.get(ctx.expression());
            expressionTypes.put(ctx, exprType != null ? exprType : "unknown");
        }
    }

    /**
     * Checks and propagates types for multiplicative expressions
     */
    @Override
    public void exitMultiplicativeExpression(MiniSoftParser.MultiplicativeExpressionContext ctx) {
        if (ctx.primaryExpression().size() == 1) {
            // Simple expression, propagate type
            String primType = expressionTypes.get(ctx.primaryExpression(0));
            expressionTypes.put(ctx, primType != null ? primType : "unknown");
        } else {
            // Multiple terms with multiplicative operators
            String resultType = "Int"; // Default
            
            for (MiniSoftParser.PrimaryExpressionContext primExpr : ctx.primaryExpression()) {
                String primType = expressionTypes.get(primExpr);
                if (primType != null && primType.equals("Float")) {
                    resultType = "Float"; // If any operand is Float, result is Float
                    break;
                }
            }
            
            expressionTypes.put(ctx, resultType);
        }
    }

    /**
     * Checks and propagates types for additive expressions
     */
    @Override
    public void exitAdditiveExpression(MiniSoftParser.AdditiveExpressionContext ctx) {
        if (ctx.multiplicativeExpression().size() == 1) {
            String multType = expressionTypes.get(ctx.multiplicativeExpression(0));
            expressionTypes.put(ctx, multType != null ? multType : "unknown");
        } else {
            String resultType = "Int";
            
            for (MiniSoftParser.MultiplicativeExpressionContext multExpr : ctx.multiplicativeExpression()) {
                String multType = expressionTypes.get(multExpr);
                if (multType != null && multType.equals("Float")) {
                    resultType = "Float";
                    break;
                }
            }
            
            expressionTypes.put(ctx, resultType);
        }
    }

    /**
     * Validates comparison expressions and sets their types
     */
    @Override
    public void exitComparisonExpression(MiniSoftParser.ComparisonExpressionContext ctx) {
        if (ctx.additiveExpression().size() == 2) {
            // This is an actual comparison
            expressionTypes.put(ctx, "Int"); // Boolean result (0 or 1)
            
            // Check type compatibility between operands
            String leftType = expressionTypes.get(ctx.additiveExpression(0));
            String rightType = expressionTypes.get(ctx.additiveExpression(1));
            
            if (leftType != null && rightType != null &&
                !leftType.equals(rightType) && 
                !(leftType.equals("Int") && rightType.equals("Float")) && 
                !(leftType.equals("Float") && rightType.equals("Int"))) {
                reportSemanticError(ctx.start, "Type mismatch in comparison: " + leftType + " and " + rightType);
            }
        } else if (ctx.logicalOrExpression() != null) {
            // Parenthesized logical expression - propagate type
            String logicalType = expressionTypes.get(ctx.logicalOrExpression());
            expressionTypes.put(ctx, logicalType != null ? logicalType : "Int");
        } else if (ctx.additiveExpression().size() == 1) {
            // Single additive expression, propagate type
            String addType = expressionTypes.get(ctx.additiveExpression(0));
            expressionTypes.put(ctx, addType != null ? addType : "unknown");
        }
    }

    /**
     * Validates logical negation (NOT) expressions
     */
    @Override
    public void exitNegationExpression(MiniSoftParser.NegationExpressionContext ctx) {
        if (ctx.NOT() != null) {
            // Logical negation always results in a boolean (Int)
            expressionTypes.put(ctx, "Int");
            
            // Check that the negated expression is a boolean
            String exprType = expressionTypes.get(ctx.negationExpression());
            if (exprType != null && !exprType.equals("Int")) {
                reportSemanticError(ctx.start, "Logical NOT (!) operator requires boolean operand, found " + exprType);
            }
        } else {
            // No negation, propagate type
            String compType = expressionTypes.get(ctx.comparisonExpression());
            expressionTypes.put(ctx, compType != null ? compType : "Int");
        }
    }

    /**
     * Validates logical AND expressions
     */
    @Override
    public void exitLogicalAndExpression(MiniSoftParser.LogicalAndExpressionContext ctx) {
        if (ctx.AND().size() > 0) {
            expressionTypes.put(ctx, "Int");
            
            // Check all operands are boolean compatible
            for (MiniSoftParser.NegationExpressionContext negExpr : ctx.negationExpression()) {
                String exprType = expressionTypes.get(negExpr);
                if (exprType != null && !exprType.equals("Int")) {
                    reportSemanticError(ctx.start, "Logical AND operator requires boolean operands, found " + exprType);
                }
            }
        } else {
            // No AND, propagate type
            String negType = expressionTypes.get(ctx.negationExpression(0));
            expressionTypes.put(ctx, negType != null ? negType : "Int");
        }
    }

    /**
     * Validates logical OR expressions
     */
    @Override
    public void exitLogicalOrExpression(MiniSoftParser.LogicalOrExpressionContext ctx) {
        if (ctx.OR().size() > 0) {
            expressionTypes.put(ctx, "Int");
            
            // Check all operands are boolean compatible
            for (MiniSoftParser.LogicalAndExpressionContext andExpr : ctx.logicalAndExpression()) {
                String exprType = expressionTypes.get(andExpr);
                if (exprType != null && !exprType.equals("Int")) {
                    reportSemanticError(ctx.start, "Logical OR operator requires boolean operands, found " + exprType);
                }
            }
        } else {
            // No OR, propagate type
            String andType = expressionTypes.get(ctx.logicalAndExpression(0));
            expressionTypes.put(ctx, andType != null ? andType : "Int");
        }
    }

    /**
     * Propagates type information from logical expressions to general expressions
     */
    @Override
    public void exitExpression(MiniSoftParser.ExpressionContext ctx) {
        String logicalType = expressionTypes.get(ctx.logicalOrExpression());
        expressionTypes.put(ctx, logicalType != null ? logicalType : "Int");
    }

    /**
     * Validates assignment statements for type compatibility
     */
    @Override
    public void exitAssignment(MiniSoftParser.AssignmentContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        if (entity != null) {
            boolean isArrayAccess = ctx.getChildCount() > 3 && ctx.getChild(1).getText().equals("[");
            MiniSoftParser.ExpressionContext valueExpr = isArrayAccess ? ctx.expression(1) : ctx.expression(0);
            String valueType = expressionTypes.get(valueExpr);
            
            // Check type compatibility
            if (valueType != null && !entity.getDataType().equals(valueType)) {
                // Special case: Int can be assigned to Float
                if (!(entity.getDataType().equals("Float") && valueType.equals("Int"))) {
                    reportSemanticError(ctx.start, "Type mismatch in assignment: Cannot assign " + 
                                     valueType + " to " + entity.getDataType());
                }
            }
        }
    }

    /**
     * Validates condition expressions are boolean compatible
     */
    @Override
    public void exitCondition(MiniSoftParser.ConditionContext ctx) {
        String condType = expressionTypes.get(ctx.logicalOrExpression());
        
        // Validate condition is boolean compatible
        if (condType != null && !condType.equals("Int")) {
            reportSemanticError(ctx.start, "Condition must evaluate to a boolean, found " + condType);
        }
    }

    /**
     * Validates if statement conditions are boolean compatible
     */
    @Override
    public void exitIfStatement(MiniSoftParser.IfStatementContext ctx) {
        String condType = expressionTypes.get(ctx.condition());
        
        // Set default boolean type if we have no type info
        if (condType == null) {
            condType = "Int"; // Default to boolean type
        }
        
        // Validate condition is boolean compatible
        if (!condType.equals("Int")) {
            reportSemanticError(ctx.start, "If condition must evaluate to a boolean, found " + condType);
        }
    }

    /**
     * Validates do-while loop conditions are boolean compatible
     */
    @Override
    public void exitDoWhileLoop(MiniSoftParser.DoWhileLoopContext ctx) {
        String condType = expressionTypes.get(ctx.condition());
        
        // Set default boolean type if we have no type info
        if (condType == null) {
            condType = "Int"; // Default to boolean type
        }
        
        // Validate condition is boolean compatible
        if (!condType.equals("Int")) {
            reportSemanticError(ctx.start, "While condition must evaluate to a boolean, found " + condType);
        }
    }

    /**
     * Reports a semantic error with location information
     */
    private void reportSemanticError(Token token, String message) {
        System.err.println("[Semantic Error] Line " + token.getLine() + ":" + token.getCharPositionInLine() +
                        " - " + message);
        hasErrors = true;
    }
}