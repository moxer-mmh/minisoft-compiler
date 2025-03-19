package com.minisoft.semantic;

import com.minisoft.error.ErrorHandler;
import com.minisoft.parser.MiniSoftBaseVisitor;
import com.minisoft.parser.MiniSoftParser;
import com.minisoft.symboltable.Symbol;
import com.minisoft.symboltable.SymbolTable;
import com.minisoft.symboltable.SymbolType;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class SemanticAnalyzer extends MiniSoftBaseVisitor<SymbolType> {
    private final SymbolTable symbolTable;
    private final ErrorHandler errorHandler;

    public SemanticAnalyzer(ErrorHandler errorHandler) {
        this.symbolTable = new SymbolTable();
        this.errorHandler = errorHandler;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public SymbolType visitVariableDeclaration(MiniSoftParser.VariableDeclarationContext ctx) {
        String name = ctx.identifier().getText();
        SymbolType type;

        if (ctx.dataType().INT() != null) {
            type = SymbolType.INT;
        } else {
            type = SymbolType.FLOAT;
        }

        Token idToken = ctx.identifier().start;
        Symbol symbol = new Symbol(name, type, false, idToken.getLine(), idToken.getCharPositionInLine());

        if (symbolTable.lookupCurrentScope(name) != null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_DUPLICATE_DECLARATION,
                    "Variable '" + name + "' is already declared in this scope",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else if (!isValidIdentifier(name)) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Invalid identifier name - must start with letter, contain only letters, digits, underscore, " +
                            "not exceed 14 chars, not end with underscore, no consecutive underscores",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else {
            symbolTable.declare(symbol);
        }

        return type;
    }

    @Override
    public SymbolType visitArrayDeclaration(MiniSoftParser.ArrayDeclarationContext ctx) {
        String name = ctx.identifier().getText();
        SymbolType baseType;
        SymbolType arrayType;

        if (ctx.dataType().INT() != null) {
            baseType = SymbolType.INT;
            arrayType = SymbolType.INT_ARRAY;
        } else {
            baseType = SymbolType.FLOAT;
            arrayType = SymbolType.FLOAT_ARRAY;
        }

        // Parse array size
        int size;
        try {
            size = Integer.parseInt(ctx.INTEGER().getText());
            if (size <= 0) {
                Token sizeToken = ctx.INTEGER().getSymbol();
                errorHandler.addError(
                        ErrorHandler.ErrorType.SEMANTIC_ARRAY_BOUNDS,
                        "Array size must be a positive integer",
                        sizeToken.getLine(),
                        sizeToken.getCharPositionInLine(),
                        name);
                return arrayType;
            }
        } catch (NumberFormatException e) {
            Token sizeToken = ctx.INTEGER().getSymbol();
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_ARRAY_BOUNDS,
                    "Invalid array size",
                    sizeToken.getLine(),
                    sizeToken.getCharPositionInLine(),
                    name);
            return arrayType;
        }

        Token idToken = ctx.identifier().start;
        Symbol symbol = new Symbol(name, arrayType, size, idToken.getLine(), idToken.getCharPositionInLine());

        if (symbolTable.lookupCurrentScope(name) != null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_DUPLICATE_DECLARATION,
                    "Array '" + name + "' is already declared in this scope",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else if (!isValidIdentifier(name)) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Invalid identifier name - must start with letter, contain only letters, digits, underscore, " +
                            "not exceed 14 chars, not end with underscore, no consecutive underscores",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else {
            symbolTable.declare(symbol);
        }

        return arrayType;
    }

    @Override
    public SymbolType visitConstantDeclaration(MiniSoftParser.ConstantDeclarationContext ctx) {
        String name = ctx.identifier().getText();
        SymbolType type;

        if (ctx.dataType().INT() != null) {
            type = SymbolType.INT;
        } else {
            type = SymbolType.FLOAT;
        }

        // Evaluate constant expression
        SymbolType exprType = visit(ctx.expression());
        if (exprType != type) {
            Token typeToken = ctx.dataType().start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Type mismatch in constant definition. Expected " + type + " but got " + exprType,
                    typeToken.getLine(),
                    typeToken.getCharPositionInLine(),
                    name);
        }

        Token idToken = ctx.identifier().start;
        Symbol symbol = new Symbol(name, type, true, idToken.getLine(), idToken.getCharPositionInLine());

        if (symbolTable.lookupCurrentScope(name) != null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_DUPLICATE_DECLARATION,
                    "Constant '" + name + "' is already declared in this scope",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else if (!isValidIdentifier(name)) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Invalid identifier name - must start with letter, contain only letters, digits, underscore, " +
                            "not exceed 14 chars, not end with underscore, no consecutive underscores",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else {
            symbolTable.declare(symbol);
        }

        return type;
    }

    @Override
    public SymbolType visitAssignment(MiniSoftParser.AssignmentContext ctx) {
        String name = ctx.identifier().getText();
        Token idToken = ctx.identifier().start;

        // Check if identifier exists
        Symbol symbol = symbolTable.lookup(name);
        if (symbol == null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Undeclared identifier '" + name + "'",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
            return SymbolType.INT; // Default to avoid further errors
        }

        // Check if trying to modify a constant
        if (symbol.isConstant()) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_CONSTANT_MODIFICATION,
                    "Cannot modify constant '" + name + "'",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        }

        // Check type compatibility
        SymbolType exprType = visit(ctx.expression());
        if (!areTypesCompatible(symbol.getType(), exprType)) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Type mismatch in assignment. Cannot assign " + exprType + " to " + symbol.getType(),
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        }

        return symbol.getType();
    }

    @Override
    public SymbolType visitIfStatement(MiniSoftParser.IfStatementContext ctx) {
        // Process condition
        visit(ctx.condition());

        // Process then block
        symbolTable.enterScope();
        for (int i = 0; i < ctx.statement(0).getChildCount(); i++) {
            ParseTree child = ctx.statement(0).getChild(i);
            if (child instanceof MiniSoftParser.StatementContext) {
                visit(child);
            }
        }
        symbolTable.exitScope();

        // Process else block if it exists
        if (ctx.statement().size() > 1) {
            symbolTable.enterScope();
            for (int i = 0; i < ctx.statement(1).getChildCount(); i++) {
                ParseTree child = ctx.statement(1).getChild(i);
                if (child instanceof MiniSoftParser.StatementContext) {
                    visit(child);
                }
            }
            symbolTable.exitScope();
        }

        return null;
    }

    @Override
    public SymbolType visitDoWhileLoop(MiniSoftParser.DoWhileLoopContext ctx) {
        symbolTable.enterScope();

        // Process loop body
        for (MiniSoftParser.StatementContext stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }

        // Process condition
        visit(ctx.condition());

        symbolTable.exitScope();
        return null;
    }

    @Override
    public SymbolType visitForLoop(MiniSoftParser.ForLoopContext ctx) {
        symbolTable.enterScope();

        // Check if loop variable exists
        String loopVar = ctx.identifier().getText();
        Symbol symbol = symbolTable.lookup(loopVar);

        if (symbol == null) {
            Token idToken = ctx.identifier().start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Undeclared loop variable '" + loopVar + "'",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    loopVar);
        } else if (symbol.isConstant()) {
            Token idToken = ctx.identifier().start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_CONSTANT_MODIFICATION,
                    "Cannot use constant '" + loopVar + "' as loop variable",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    loopVar);
        }

        // Check loop bounds and step
        for (int i = 0; i < 3; i++) { // from, to, step
            SymbolType exprType = visit(ctx.expression(i));
            if (exprType != SymbolType.INT && exprType != SymbolType.FLOAT) {
                Token exprToken = ctx.expression(i).start;
                errorHandler.addError(
                        ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                        "Loop bounds and step must be numeric",
                        exprToken.getLine(),
                        exprToken.getCharPositionInLine(),
                        ctx.expression(i).getText());
            }
        }

        // Process loop body
        for (MiniSoftParser.StatementContext stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }

        symbolTable.exitScope();
        return null;
    }

    @Override
    public SymbolType visitInputStatement(MiniSoftParser.InputStatementContext ctx) {
        // Check if identifier exists
        String name = ctx.identifier().getText();
        Symbol symbol = symbolTable.lookup(name);

        Token idToken = ctx.identifier().start;
        if (symbol == null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Undeclared identifier '" + name + "' in input statement",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        } else if (symbol.isConstant()) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_CONSTANT_MODIFICATION,
                    "Cannot modify constant '" + name + "' with input statement",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    name);
        }

        return null;
    }

    @Override
    public SymbolType visitOutputStatement(MiniSoftParser.OutputStatementContext ctx) {
        // Check if identifiers exist
        for (MiniSoftParser.IdentifierContext idCtx : ctx.identifier()) {
            String name = idCtx.getText();
            Symbol symbol = symbolTable.lookup(name);

            if (symbol == null) {
                Token idToken = idCtx.start;
                errorHandler.addError(
                        ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                        "Undeclared identifier '" + name + "' in output statement",
                        idToken.getLine(),
                        idToken.getCharPositionInLine(),
                        name);
            }
        }

        return null;
    }

    @Override
    public SymbolType visitComparisonCondition(MiniSoftParser.ComparisonConditionContext ctx) {
        SymbolType leftType = visit(ctx.expression(0));
        SymbolType rightType = visit(ctx.expression(1));

        // Check if comparison types are compatible
        if (!areTypesCompatible(leftType, rightType)) {
            Token opToken = ctx.comparisonOperator().start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Cannot compare incompatible types " + leftType + " and " + rightType,
                    opToken.getLine(),
                    opToken.getCharPositionInLine(),
                    ctx.comparisonOperator().getText());
        }

        return SymbolType.INT; // Boolean represented as INT
    }

    @Override
    public SymbolType visitAndCondition(MiniSoftParser.AndConditionContext ctx) {
        visit(ctx.condition(0));
        visit(ctx.condition(1));
        return SymbolType.INT; // Boolean represented as INT
    }

    @Override
    public SymbolType visitOrCondition(MiniSoftParser.OrConditionContext ctx) {
        visit(ctx.condition(0));
        visit(ctx.condition(1));
        return SymbolType.INT; // Boolean represented as INT
    }

    @Override
    public SymbolType visitNotCondition(MiniSoftParser.NotConditionContext ctx) {
        visit(ctx.condition());
        return SymbolType.INT; // Boolean represented as INT
    }

    @Override
    public SymbolType visitParenthesizedCondition(MiniSoftParser.ParenthesizedConditionContext ctx) {
        return visit(ctx.condition());
    }

    @Override
    public SymbolType visitPrimaryExpr(MiniSoftParser.PrimaryExprContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public SymbolType visitArrayAccessExpr(MiniSoftParser.ArrayAccessExprContext ctx) {
        String arrayName = ctx.identifier().getText();
        Token idToken = ctx.identifier().start;

        // Check if array exists
        Symbol symbol = symbolTable.lookup(arrayName);
        if (symbol == null) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                    "Undeclared array '" + arrayName + "'",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    arrayName);
            return SymbolType.INT; // Default to avoid further errors
        }

        // Check if it's actually an array
        if (!symbol.isArray()) {
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Cannot use array access on non-array variable '" + arrayName + "'",
                    idToken.getLine(),
                    idToken.getCharPositionInLine(),
                    arrayName);
            return SymbolType.INT; // Default to avoid further errors
        }

        // Check array index type
        SymbolType indexType = visit(ctx.expression());
        if (indexType != SymbolType.INT) {
            Token exprToken = ctx.expression().start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Array index must be an integer",
                    exprToken.getLine(),
                    exprToken.getCharPositionInLine(),
                    ctx.expression().getText());
        }

        // Return the base type of array
        return symbol.getType() == SymbolType.INT_ARRAY ? SymbolType.INT : SymbolType.FLOAT;
    }

    @Override
    public SymbolType visitParenExpr(MiniSoftParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public SymbolType visitNegationExpr(MiniSoftParser.NegationExprContext ctx) {
        SymbolType exprType = visit(ctx.expression());

        // Check if negation operand is numeric
        if (exprType != SymbolType.INT && exprType != SymbolType.FLOAT) {
            Token opToken = ctx.start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Cannot negate non-numeric type " + exprType,
                    opToken.getLine(),
                    opToken.getCharPositionInLine(),
                    "-");
            return SymbolType.INT; // Default to avoid further errors
        }

        return exprType;
    }

    @Override
    public SymbolType visitMulDivExpr(MiniSoftParser.MulDivExprContext ctx) {
        SymbolType leftType = visit(ctx.expression(0));
        SymbolType rightType = visit(ctx.expression(1));

        // Check for division by zero (for constant expressions)
        if (ctx.getText().contains("/") && isDivisionByZero(ctx.expression(1))) {
            Token opToken = ctx.start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_DIVISION_BY_ZERO,
                    "Division by zero",
                    opToken.getLine(),
                    opToken.getCharPositionInLine(),
                    "/");
        }

        // Check if operands are numeric
        if ((leftType != SymbolType.INT && leftType != SymbolType.FLOAT) ||
                (rightType != SymbolType.INT && rightType != SymbolType.FLOAT)) {
            Token opToken = ctx.start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Cannot perform arithmetic on non-numeric types " + leftType + " and " + rightType,
                    opToken.getLine(),
                    opToken.getCharPositionInLine(),
                    ctx.getText().contains("*") ? "*" : "/");
            return SymbolType.INT; // Default to avoid further errors
        }

        // If either operand is FLOAT, result is FLOAT
        return (leftType == SymbolType.FLOAT || rightType == SymbolType.FLOAT) ? SymbolType.FLOAT : SymbolType.INT;
    }

    @Override
    public SymbolType visitAddSubExpr(MiniSoftParser.AddSubExprContext ctx) {
        SymbolType leftType = visit(ctx.expression(0));
        SymbolType rightType = visit(ctx.expression(1));

        // Check if operands are numeric
        if ((leftType != SymbolType.INT && leftType != SymbolType.FLOAT) ||
                (rightType != SymbolType.INT && rightType != SymbolType.FLOAT)) {
            Token opToken = ctx.start;
            errorHandler.addError(
                    ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                    "Cannot perform arithmetic on non-numeric types " + leftType + " and " + rightType,
                    opToken.getLine(),
                    opToken.getCharPositionInLine(),
                    ctx.getText().contains("+") ? "+" : "-");
            return SymbolType.INT; // Default to avoid further errors
        }

        // If either operand is FLOAT, result is FLOAT
        return (leftType == SymbolType.FLOAT || rightType == SymbolType.FLOAT) ? SymbolType.FLOAT : SymbolType.INT;
    }

    @Override
    public SymbolType visitPrimary(MiniSoftParser.PrimaryContext ctx) {
        if (ctx.identifier() != null) {
            String name = ctx.identifier().getText();
            Symbol symbol = symbolTable.lookup(name);

            if (symbol == null) {
                Token idToken = ctx.identifier().start;
                errorHandler.addError(
                        ErrorHandler.ErrorType.SEMANTIC_UNDECLARED_IDENTIFIER,
                        "Undeclared identifier '" + name + "'",
                        idToken.getLine(),
                        idToken.getCharPositionInLine(),
                        name);
                return SymbolType.INT; // Default to avoid further errors
            }

            // For arrays, we're referring to the entire array, not an element
            if (symbol.isArray()) {
                Token idToken = ctx.identifier().start;
                errorHandler.addError(
                        ErrorHandler.ErrorType.SEMANTIC_TYPE_MISMATCH,
                        "Cannot use array '" + name + "' without index",
                        idToken.getLine(),
                        idToken.getCharPositionInLine(),
                        name);
                return SymbolType.INT; // Default to avoid further errors
            }

            return symbol.getType();
        } else if (ctx.INTEGER() != null) {
            return SymbolType.INT;
        } else if (ctx.FLOAT() != null) {
            return SymbolType.FLOAT;
        }

        // Default case (should not happen with valid syntax)
        return SymbolType.INT;
    }

    // Helper methods
    private boolean isValidIdentifier(String name) {
        // Must begin with a letter
        if (!Character.isLetter(name.charAt(0))) {
            return false;
        }

        // Cannot end with an underscore
        if (name.endsWith("_")) {
            return false;
        }

        // Cannot contain consecutive underscores
        if (name.contains("__")) {
            return false;
        }

        // Must not exceed 14 characters
        if (name.length() > 14) {
            return false;
        }

        // Can include lowercase letters, digits, and underscores
        for (char c : name.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
    }

    private boolean areTypesCompatible(SymbolType type1, SymbolType type2) {
        // Same types are compatible
        if (type1 == type2) {
            return true;
        }

        // Allow INT to FLOAT conversion (implicit casting)
        if ((type1 == SymbolType.FLOAT && type2 == SymbolType.INT) ||
                (type2 == SymbolType.FLOAT && type1 == SymbolType.INT)) {
            return true;
        }

        return false;
    }

    private boolean isDivisionByZero(MiniSoftParser.ExpressionContext expr) {
        // Check for constant value 0
        if (expr instanceof MiniSoftParser.PrimaryExprContext) {
            MiniSoftParser.PrimaryContext primary = ((MiniSoftParser.PrimaryExprContext) expr).primary();
            if (primary.INTEGER() != null && primary.INTEGER().getText().equals("0")) {
                return true;
            }
            if (primary.FLOAT() != null && primary.FLOAT().getText().equals("0.0")) {
                return true;
            }
        }
        return false;
    }
}
