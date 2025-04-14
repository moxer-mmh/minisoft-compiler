package com.minisoft;

import com.minisoft.symbol.SymbolEntity;
import com.minisoft.symbol.SymbolTable;
import org.antlr.v4.runtime.Token;

public class SymbolTableBuilder extends MiniSoftBaseListener {
    private SymbolTable symbolTable;
    private boolean hasErrors;

    public SymbolTableBuilder() {
        this.symbolTable = new SymbolTable();
        this.hasErrors = false;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    @Override
    public void enterVariableDeclaration(MiniSoftParser.VariableDeclarationContext ctx) {
        String type = ctx.type().getText();
        boolean isArray = ctx.getChildCount() > 5 && ctx.getChild(3).getText().equals("[");
        
        // Get the list of identifiers
        MiniSoftParser.IdListContext idListCtx = ctx.idList();
        for (int i = 0; i < idListCtx.ID().size(); i++) {
            String identifier = idListCtx.ID(i).getText();
            
            // Check for double declaration
            if (symbolTable.symbolExists(identifier)) {
                reportSemanticError(ctx.start, "Double declaration: Variable '" + identifier + "' is already declared");
                continue;
            }
            
            if (isArray) {
                int arraySize = Integer.parseInt(ctx.INT().getText());
                if (arraySize <= 0) {
                    reportSemanticError(ctx.start, "Array size must be a positive integer for array '" + identifier + "'");
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

    @Override
    public void enterAssignment(MiniSoftParser.AssignmentContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
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
        MiniSoftParser.ExpressionContext rightExpr = isArrayAccess ? ctx.expression(1) : ctx.expression(0);
        checkExpression(rightExpr, entity.getDataType(), ctx.start);
    }
    
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
            
            // Check if identifier exists
            if (entity == null) {
                reportSemanticError(ctx.start, "Undeclared identifier: Variable '" + identifier + "' is not declared");
            }
        }
    }
    
    @Override
    public void enterMultiplicativeExpression(MiniSoftParser.MultiplicativeExpressionContext ctx) {
        // Check for division by zero with constants
        if (ctx.DIV() != null) {
            for (int i = 1; i < ctx.primaryExpression().size(); i++) {
                MiniSoftParser.PrimaryExpressionContext divisor = ctx.primaryExpression(i);
                if (divisor.constValue() != null) {
                    // Check for direct zero constant
                    if (divisor.constValue().INT() != null && divisor.constValue().INT().getText().equals("0")) {
                        reportSemanticError(ctx.start, "Division by zero detected");
                    }
                    // Check for negative zero or positive zero
                    else if (divisor.constValue().getChildCount() > 2) {
                        Token valueToken = null;
                        if (divisor.constValue().getChild(2) instanceof Token) {
                            valueToken = (Token) divisor.constValue().getChild(2);
                            if (valueToken.getText().equals("0")) {
                                reportSemanticError(ctx.start, "Division by zero detected");
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void enterForLoop(MiniSoftParser.ForLoopContext ctx) {
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        // Check if identifier exists
        if (entity == null) {
            reportSemanticError(ctx.start, "Undeclared identifier: Loop variable '" + identifier + "' is not declared");
            return;
        }
        
        // Check if it's a constant (cannot be modified in loop)
        if (entity.getEntityType().equals("constant")) {
            reportSemanticError(ctx.start, "Cannot use constant '" + identifier + "' as a loop variable");
        }
        
        // Check if the loop variable is of Integer type
        if (!entity.getDataType().equals("Int")) {
            reportSemanticError(ctx.start, "Loop variable '" + identifier + "' must be of type Int");
        }
        
        // Check expressions for initialization, condition, and step
        checkExpression(ctx.expression(0), "Int", ctx.start); // from
        checkExpression(ctx.expression(1), "Int", ctx.start); // to
        checkExpression(ctx.expression(2), "Int", ctx.start); // step
    }
    
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
    
    private void checkArrayBounds(MiniSoftParser.ExpressionContext indexExpr, SymbolEntity arrayEntity, Token errorToken) {
        // Check if index is a constant value
        if (indexExpr.getChildCount() == 1 && indexExpr.getChild(0) instanceof MiniSoftParser.LogicalOrExpressionContext) {
            MiniSoftParser.LogicalOrExpressionContext logicalExpr = (MiniSoftParser.LogicalOrExpressionContext) indexExpr.getChild(0);
            if (logicalExpr.getChildCount() == 1) {
                MiniSoftParser.LogicalAndExpressionContext andExpr = logicalExpr.logicalAndExpression(0);
                if (andExpr.getChildCount() == 1) {
                    MiniSoftParser.NegationExpressionContext negExpr = andExpr.negationExpression(0);
                    if (negExpr.getChildCount() == 1) {
                        MiniSoftParser.ComparisonExpressionContext compExpr = negExpr.comparisonExpression();
                        if (compExpr.getChildCount() == 1) {
                            MiniSoftParser.AdditiveExpressionContext addExpr = compExpr.additiveExpression(0);
                            if (addExpr.getChildCount() == 1) {
                                MiniSoftParser.MultiplicativeExpressionContext multExpr = addExpr.multiplicativeExpression(0);
                                if (multExpr.getChildCount() == 1) {
                                    MiniSoftParser.PrimaryExpressionContext primaryExpr = multExpr.primaryExpression(0);
                                    if (primaryExpr.constValue() != null) {
                                        // We have a constant index
                                        MiniSoftParser.ConstValueContext constValue = primaryExpr.constValue();
                                        try {
                                            int index = -1;
                                            if (constValue.INT() != null) {
                                                index = Integer.parseInt(constValue.INT().getText());
                                            } else if (constValue.getChildCount() > 2) {
                                                String sign = constValue.getChild(1).getText();
                                                Token valueToken = (Token) constValue.getChild(2);
                                                int value = Integer.parseInt(valueToken.getText());
                                                index = sign.equals("-") ? -value : value;
                                            }
                                            
                                            // Check bounds
                                            if (index < 0) {
                                                reportSemanticError(errorToken, "Array index cannot be negative: " + index);
                                            } else if (index >= arrayEntity.getArraySize()) {
                                                reportSemanticError(errorToken, "Array index out of bounds: " + index + 
                                                                  " (size: " + arrayEntity.getArraySize() + ")");
                                            }
                                        } catch (NumberFormatException e) {
                                            // Not an integer index
                                            reportSemanticError(errorToken, "Array index must be an integer");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void checkExpression(MiniSoftParser.ExpressionContext expr, String expectedType, Token errorToken) {
        // This is a simplified type checking implementation
        // In a real compiler, you'd need more sophisticated type inference
        
        // If expression contains function calls or complex expressions, 
        // you would need recursive type checking
        
        // For now, we'll do basic type checking for constants
        if (expr.getChildCount() == 1 && expr.getChild(0) instanceof MiniSoftParser.LogicalOrExpressionContext) {
            MiniSoftParser.LogicalOrExpressionContext logicalExpr = (MiniSoftParser.LogicalOrExpressionContext) expr.getChild(0);
            if (logicalExpr.getChildCount() == 1) {
                MiniSoftParser.LogicalAndExpressionContext andExpr = logicalExpr.logicalAndExpression(0);
                if (andExpr.getChildCount() == 1) {
                    MiniSoftParser.NegationExpressionContext negExpr = andExpr.negationExpression(0);
                    if (negExpr.getChildCount() == 1) {
                        MiniSoftParser.ComparisonExpressionContext compExpr = negExpr.comparisonExpression();
                        if (compExpr.getChildCount() == 1) {
                            MiniSoftParser.AdditiveExpressionContext addExpr = compExpr.additiveExpression(0);
                            if (addExpr.getChildCount() == 1) {
                                MiniSoftParser.MultiplicativeExpressionContext multExpr = addExpr.multiplicativeExpression(0);
                                if (multExpr.getChildCount() == 1) {
                                    MiniSoftParser.PrimaryExpressionContext primaryExpr = multExpr.primaryExpression(0);
                                    
                                    // Check type of ID reference
                                    if (primaryExpr.ID() != null && primaryExpr.LBRACK() == null) {
                                        String id = primaryExpr.ID().getText();
                                        SymbolEntity entity = symbolTable.lookupSymbol(id);
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
                        }
                    }
                }
            }
        }
    }

    private void reportSemanticError(Token token, String message) {
        System.err.println("[Semantic Error] Line " + token.getLine() + ":" + token.getCharPositionInLine() +
                        " - " + message);
        hasErrors = true;
    }
}