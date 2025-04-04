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
        
        // Get the list of identifiers from idList rule (not identifierList)
        MiniSoftParser.IdListContext idListCtx = ctx.idList();
        for (int i = 0; i < idListCtx.ID().size(); i++) {
            String identifier = idListCtx.ID(i).getText();
            
            if (symbolTable.symbolExists(identifier)) {
                reportSemanticError(ctx.start, "Variable '" + identifier + "' is already declared");
                continue;
            }
            
            if (isArray) {
                String entityType = "array";
                // Access INT token directly, not INTEGER()
                int arraySize = Integer.parseInt(ctx.INT().getText());
                
                SymbolEntity entity = new SymbolEntity(identifier, type, entityType, 
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
        // Use ID token instead of IDENTIFIER
        String identifier = ctx.ID().getText();
        String type = ctx.type().getText();
        
        if (symbolTable.symbolExists(identifier)) {
            reportSemanticError(ctx.start, "Constant '" + identifier + "' is already declared");
            return;
        }
        
        SymbolEntity entity = new SymbolEntity(identifier, type, "constant", 
                                            ctx.start.getLine(), ctx.start.getCharPositionInLine());
        
        // Parse and set the value using constValue instead of value
        MiniSoftParser.ConstValueContext constValueCtx = ctx.constValue();
        if (constValueCtx.INT() != null) {
            entity.setValue(Integer.parseInt(constValueCtx.INT().getText()));
        } else if (constValueCtx.FLOAT() != null) {
            entity.setValue(Float.parseFloat(constValueCtx.FLOAT().getText()));
        } else {
            // Handle signed values
            String sign = constValueCtx.getChild(1).getText();
            if (constValueCtx.getChild(2) instanceof Token) {
                Token valueToken = (Token) constValueCtx.getChild(2);
                if (valueToken.getText().contains(".")) {
                    float value = Float.parseFloat(valueToken.getText());
                    entity.setValue(sign.equals("-") ? -value : value);
                } else {
                    int value = Integer.parseInt(valueToken.getText());
                    entity.setValue(sign.equals("-") ? -value : value);
                }
            }
        }
        
        symbolTable.addSymbol(entity);
    }

    @Override
    public void enterAssignment(MiniSoftParser.AssignmentContext ctx) {
        // Updated to match the assignment rule from grammar
        String identifier = ctx.ID().getText();
        SymbolEntity entity = symbolTable.lookupSymbol(identifier);
        
        if (entity == null) {
            reportSemanticError(ctx.start, "Variable '" + identifier + "' is not declared");
            return;
        }
        
        if (entity.getEntityType().equals("constant")) {
            reportSemanticError(ctx.start, "Cannot modify the value of constant '" + identifier + "'");
        }
        
        // Check if this is an array access
        boolean isArrayAccess = ctx.getChildCount() > 3 && ctx.getChild(1).getText().equals("[");
        
        if (isArrayAccess && !entity.getEntityType().equals("array")) {
            reportSemanticError(ctx.start, "Cannot use array access on non-array variable '" + identifier + "'");
        } else if (!isArrayAccess && entity.getEntityType().equals("array")) {
            reportSemanticError(ctx.start, "Array '" + identifier + "' requires an index");
        }
        
        // Additional semantic checks would go here (type compatibility, array index bounds, etc.)
    }

    private void reportSemanticError(Token token, String message) {
        System.err.println("[Semantic Error] Line " + token.getLine() + ":" + token.getCharPositionInLine() +
                        " - " + message);
        hasErrors = true;
    }
}