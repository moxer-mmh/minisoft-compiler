package com.minisoft;

import com.minisoft.symbol.SymbolEntity;
import com.minisoft.symbol.SymbolTable;
import org.antlr.v4.runtime.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Symbol Table Builder for the MiniSoft compiler.
 * Responsible for collecting identifier declarations and performing initial semantic checks:
 * - Variable and constant declarations
 * - Array bounds validation
 * - Identifier reference validation
 * - Constant value validation
 * - Division by zero detection for constants
 */
public class SymbolTableBuilder extends MiniSoftBaseListener {
    private SymbolTable symbolTable;
    private boolean hasErrors;
    
    // Maps variable names to a boolean indicating if they currently hold a zero value
    // Used for division by zero detection
    private Map<String, Boolean> zeroValuedVariables;

    /**
     * Constructor initializes the symbol table and error tracking
     */
    public SymbolTableBuilder() {
        this.symbolTable = new SymbolTable();
        this.hasErrors = false;
        this.zeroValuedVariables = new HashMap<>();
    }

    /**
     * Returns the constructed symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Returns whether any semantic errors were detected during symbol table building
     */
    public boolean hasErrors() {
        return hasErrors;
    }

    /**
     * Processes variable declarations and adds them to the symbol table
     */
    @Override
    public void enterVariableDeclaration(MiniSoftParser.VariableDeclarationContext ctx) {
        String type = ctx.type().getText();
        
        // Extract the identifiers from the ID list
        for (int i = 0; i < ctx.idList().ID().size(); i++) {
            String identifier = ctx.idList().ID(i).getText();
            
            // Check for double declaration
            if (symbolTable.symbolExists(identifier)) {
                reportSemanticError(ctx.start, "Double declaration: Variable '" + identifier + "' is already declared");
                continue;
            }
            
            // Handle array declarations
            if (ctx.LBRACK() != null) {
                int arraySize = 0;
                try {
                    arraySize = Integer.parseInt(ctx.INT().getText());
                    if (arraySize <= 0) {
                        reportSemanticError(ctx.start, "Invalid array size: Size must be positive for array '" + identifier + "'");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    reportSemanticError(ctx.start, "Invalid array size: Not a valid integer for array '" + identifier + "'");
                    continue;
                }
                
                SymbolEntity entity = new SymbolEntity(identifier, type, "array", 
                                                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
                entity.setArraySize(arraySize);
                symbolTable.addSymbol(entity);
            } else {
                SymbolEntity entity = new SymbolEntity(identifier, type, "variable", 
                                                    ctx.start.getLine(), ctx.start.getCharPositionInLine());
                symbolTable.addSymbol(entity);
            }
        }
    }

    /**
     * Processes constant declarations and adds them to the symbol table
     */
    @Override
    public void enterConstantDeclaration(MiniSoftParser.ConstantDeclarationContext ctx) {
        String identifier = ctx.ID().getText();
        String type = ctx.type().getText();
        
        // Check for double declaration
        if (symbolTable.symbolExists(identifier)) {
            reportSemanticError(ctx.start, "Double declaration: Constant '" + identifier + "' is already declared");
            return;
        }
        
        SymbolEntity entity = new SymbolEntity(identifier, type, "constant", 
                                            ctx.start.getLine(), ctx.start.getCharPositionInLine());
        
        // Parse and set the value
        MiniSoftParser.ConstValueContext constValueCtx = ctx.constValue();
        try {
            if (constValueCtx.INT() != null) {
                int value = Integer.parseInt(constValueCtx.INT().getText());
                entity.setValue(value);
                if (!type.equals("Int")) {
                    reportSemanticError(ctx.start, "Type mismatch: Integer value assigned to non-integer constant '" + identifier + "'");
                }
            } else if (constValueCtx.FLOAT() != null) {
                float value = Float.parseFloat(constValueCtx.FLOAT().getText());
                entity.setValue(value);
                if (!type.equals("Float")) {
                    reportSemanticError(ctx.start, "Type mismatch: Float value assigned to non-float constant '" + identifier + "'");
                }
            } else {
                // Handle signed values
                String sign = constValueCtx.sign().getText();
                if (constValueCtx.getChild(2) instanceof Token) {
                    Token valueToken = (Token) constValueCtx.getChild(2);
                    if (valueToken.getText().contains(".")) {
                        float value = Float.parseFloat(valueToken.getText());
                        float signedValue = sign.equals("-") ? -value : value;
                        entity.setValue(signedValue);
                        if (!type.equals("Float")) {
                            reportSemanticError(ctx.start, "Type mismatch: Float value assigned to non-float constant '" + identifier + "'");
                        }
                    } else {
                        int value = Integer.parseInt(valueToken.getText());
                        int signedValue = sign.equals("-") ? -value : value;
                        entity.setValue(signedValue);
                        if (!type.equals("Int")) {
                            reportSemanticError(ctx.start, "Type mismatch: Integer value assigned to non-integer constant '" + identifier + "'");
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            reportSemanticError(ctx.start, "Invalid number format for constant '" + identifier + "'");
        }
        
        symbolTable.addSymbol(entity);
    }

    /**
     * Validates primary expressions - identifiers and array accesses
     */
    @Override
    public void enterPrimaryExpression(MiniSoftParser.PrimaryExpressionContext ctx) {
        // Check array access
        if (ctx.ID() != null && ctx.LBRACK() != null) {
            String identifier = ctx.ID().getText();
            SymbolEntity entity = symbolTable.lookupSymbol(identifier);
            
            // Check if identifier exists
            if (entity == null) {
                reportSemanticError(ctx.start, "Undeclared identifier: Variable '" + identifier + "' is not declared");
                return;
            }
            
            // Check if it's an array
            if (!entity.getEntityType().equals("array")) {
                reportSemanticError(ctx.start, "Cannot use array access on non-array variable '" + identifier + "'");
                return;
            }
            
            // Check array bounds if possible
            checkArrayBounds(ctx.expression(), entity, ctx.start);
        }
        // Check for variable use
        else if (ctx.ID() != null) {
            String identifier = ctx.ID().getText();
            SymbolEntity entity = symbolTable.lookupSymbol(identifier);
            
            if (entity == null) {
                reportSemanticError(ctx.start, "Undeclared identifier: Variable '" + identifier + "' is not declared");
            }
        }
    }

    /**
     * Validates assignment statements and checks for type compatibility
     */
    @Override
    public void enterAssignment(MiniSoftParser.AssignmentContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        // Track zero assignments for division by zero detection
        MiniSoftParser.ExpressionContext rightExpr = ctx.expression(0);
        if (rightExpr != null && rightExpr.getChildCount() == 1) {
            // Check for direct assignment of zero
            if (rightExpr.getText().equals("0")) {
                zeroValuedVariables.put(identifier, true);
            } else {
                zeroValuedVariables.put(identifier, false);
            }
        }
        
        // Check for undeclared identifier
        if (entity == null) {
            reportSemanticError(ctx.start, "Undeclared identifier: Variable '" + identifier + "' is not declared");
            return;
        }
        
        // Check for constant modification
        if (entity.getEntityType().equals("constant")) {
            reportSemanticError(ctx.start, "Cannot modify the value of constant '" + identifier + "'");
        }
        
        // Check for array access
        boolean isArrayAccess = ctx.getChildCount() > 3 && ctx.getChild(1).getText().equals("[");
        
        if (isArrayAccess && !entity.getEntityType().equals("array")) {
            reportSemanticError(ctx.start, "Cannot use array access on non-array variable '" + identifier + "'");
        } else if (!isArrayAccess && entity.getEntityType().equals("array")) {
            reportSemanticError(ctx.start, "Array '" + identifier + "' requires an index");
        }
        
        // If array access, check array bounds if index is constant
        if (isArrayAccess) {
            MiniSoftParser.ExpressionContext indexExpr = ctx.expression(0);
            checkArrayBounds(indexExpr, entity, ctx.start);
        }
        
        // Check right side expression
        MiniSoftParser.ExpressionContext rightExprFinal = isArrayAccess ? ctx.expression(1) : ctx.expression(0);
        checkExpression(rightExprFinal, entity.getDataType(), ctx.start);
    }

    /**
     * Detects division by zero in constant expressions
     */
    @Override
    public void enterMultiplicativeExpression(MiniSoftParser.MultiplicativeExpressionContext ctx) {
        // Check for division by zero
        if (ctx.getChildCount() >= 3) {
            for (int i = 0; i < ctx.getChildCount(); i++) {
                if (ctx.getChild(i).getText().equals("/")) {
                    // Get the divisor (right operand of division)
                    if (i + 1 < ctx.getChildCount()) {
                        MiniSoftParser.PrimaryExpressionContext divisor = ctx.primaryExpression(i / 2 + 1);
                        // Check for constant divisor with value zero
                        if (divisor.constValue() != null) {
                            if (divisor.constValue().INT() != null && divisor.constValue().INT().getText().equals("0")) {
                                reportSemanticError(ctx.start, "Division by zero detected");
                            } else if (divisor.constValue().getChildCount() > 2) {
                                Token valueToken = (Token) divisor.constValue().getChild(2);
                                if (valueToken.getText().equals("0")) {
                                    reportSemanticError(ctx.start, "Division by zero detected");
                                }
                            }
                        } 
                        // Check for variable divisors with known zero value
                        else if (divisor.ID() != null) {
                            String varName = divisor.ID().getText();
                            if (zeroValuedVariables.containsKey(varName) && zeroValuedVariables.get(varName)) {
                                reportSemanticError(ctx.start, "Potential division by zero: Variable '" + varName + "' has value 0");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Validates for loop expressions are integers
     */
    @Override
    public void enterForLoop(MiniSoftParser.ForLoopContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        // Check if loop variable exists
        if (entity == null) {
            reportSemanticError(ctx.start, "Undeclared identifier: For loop variable '" + identifier + "' is not declared");
            return;
        }
        
        // Check if loop variable is an integer
        if (!entity.getDataType().equals("Int")) {
            reportSemanticError(ctx.start, "For loop variable must be of type Int, found " + entity.getDataType());
            return;
        }
        
        // Check all loop control expressions (from, to, step) are integers
        checkExpression(ctx.expression(0), "Int", ctx.start); // from
        checkExpression(ctx.expression(1), "Int", ctx.start); // to
        checkExpression(ctx.expression(2), "Int", ctx.start); // step
    }
    
    /**
     * Validates the input statement target is a valid variable
     */
    @Override
    public void enterInputStatement(MiniSoftParser.InputStatementContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        // Check if identifier exists
        if (entity == null) {
            reportSemanticError(ctx.start, "Undeclared identifier: Variable '" + identifier + "' is not declared");
            return;
        }
        
        // Check if it's a constant (cannot be modified)
        if (entity.getEntityType().equals("constant")) {
            reportSemanticError(ctx.start, "Cannot assign input to constant '" + identifier + "'");
        }
        
        // Check if it's an array (requires index)
        if (entity.getEntityType().equals("array")) {
            reportSemanticError(ctx.start, "Cannot assign input to entire array '" + identifier + "', index required");
        }
    }
    
    // Helper methods for semantic analysis
    
    /**
     * Validates array access is within bounds for constant indices
     */
    private void checkArrayBounds(MiniSoftParser.ExpressionContext indexExpr, SymbolEntity arrayEntity, Token errorToken) {
        // Only check static array bounds - for constant expressions
        if (indexExpr.getChildCount() == 1 && 
            indexExpr.logicalOrExpression().getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).multiplicativeExpression(0).getChildCount() == 1 &&
            indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).multiplicativeExpression(0).primaryExpression(0).constValue() != null) {
            
            MiniSoftParser.ConstValueContext constValue = indexExpr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).multiplicativeExpression(0).primaryExpression(0).constValue();
            
            try {
                int index;
                if (constValue.INT() != null) {
                    index = Integer.parseInt(constValue.INT().getText());
                } else if (constValue.getChildCount() > 2) {
                    Token valueToken = (Token) constValue.getChild(2);
                    int value = Integer.parseInt(valueToken.getText());
                    String sign = constValue.sign().getText();
                    index = sign.equals("-") ? -value : value;
                } else {
                    // Not a constant integer
                    return;
                }
                
                // Check if index is negative
                if (index < 0) {
                    reportSemanticError(errorToken, "Array index out of bounds: Negative index " + index + " for array '" + arrayEntity.getName() + "'");
                }
                
                // Check if index is >= array size
                if (index >= arrayEntity.getArraySize()) {
                    reportSemanticError(errorToken, "Array index out of bounds: Index " + index + " exceeds array size " + arrayEntity.getArraySize() + " for array '" + arrayEntity.getName() + "'");
                }
            } catch (NumberFormatException e) {
                // Not a valid integer
            }
        }
    }
    
    /**
     * Validates expression types against expected types
     */
    private void checkExpression(MiniSoftParser.ExpressionContext expr, String expectedType, Token errorToken) {
        // For simple expressions, we can do some static type checking
        if (expr.getChildCount() == 1 && 
            expr.logicalOrExpression().getChildCount() == 1 &&
            expr.logicalOrExpression().logicalAndExpression(0).getChildCount() == 1 &&
            expr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).getChildCount() == 1 &&
            expr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().getChildCount() == 1 &&
            expr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).getChildCount() == 1 &&
            expr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).multiplicativeExpression(0).getChildCount() == 1) {
            
            MiniSoftParser.PrimaryExpressionContext primaryExpr = expr.logicalOrExpression().logicalAndExpression(0).negationExpression(0).comparisonExpression().additiveExpression(0).multiplicativeExpression(0).primaryExpression(0);
            
            // Check type of variable
            if (primaryExpr.ID() != null && primaryExpr.LBRACK() == null) {
                String identifier = primaryExpr.ID().getText();
                SymbolEntity entity = symbolTable.lookupSymbol(identifier);
                if (entity != null && !entity.getDataType().equals(expectedType)) {
                    // Special case: Int can be used where Float is expected (implicit conversion)
                    if (!(entity.getDataType().equals("Int") && expectedType.equals("Float"))) {
                        reportSemanticError(errorToken, "Type mismatch: Expected " + expectedType + 
                                           " but found " + entity.getDataType());
                    }
                }
            }
            
            // Check type of constant
            else if (primaryExpr.constValue() != null) {
                MiniSoftParser.ConstValueContext constValue = primaryExpr.constValue();
                
                // Check for integer constant
                if ((constValue.INT() != null || 
                    (constValue.getChildCount() > 2 && !((Token)constValue.getChild(2)).getText().contains("."))) &&
                    expectedType.equals("Float")) {
                    // Integer can be used where float is expected - implicit conversion
                }
                // Check for float constant
                else if ((constValue.FLOAT() != null || 
                        (constValue.getChildCount() > 2 && ((Token)constValue.getChild(2)).getText().contains("."))) &&
                        expectedType.equals("Int")) {
                    reportSemanticError(errorToken, "Type mismatch: Expected Int but found Float");
                }
            }
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