package com.minisoft.codegen;

import com.minisoft.parser.MiniSoftBaseVisitor;
import com.minisoft.parser.MiniSoftParser;
import com.minisoft.symboltable.Symbol;
import com.minisoft.symboltable.SymbolTable;
import com.minisoft.symboltable.SymbolType;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CodeGenerator extends MiniSoftBaseVisitor<String> {
    private final SymbolTable symbolTable;
    private final List<Quadruple> quadruples;
    private int tempCounter; // Removing erroneous & character
    private int labelCounter;

    // Stacks for managing control flow
    private final Stack<String> loopStartLabels;
    private final Stack<String> loopEndLabels;
    private final Stack<String> ifEndLabels;
    private final Stack<String> elseLabels;

    public CodeGenerator(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
        this.quadruples = new ArrayList<>();
        this.tempCounter = 0;
        this.labelCounter = 0;
        this.loopStartLabels = new Stack<>();
        this.loopEndLabels = new Stack<>();
        this.ifEndLabels = new Stack<>();
        this.elseLabels = new Stack<>();
    }

    public List<Quadruple> getQuadruples() {
        return quadruples;
    }

    private String newTemp() {
        return "t" + (tempCounter++);
    }

    private String newLabel() {
        return "L" + (labelCounter++);
    }

    private void emit(String op, String arg1, String arg2, String result) {
        quadruples.add(new Quadruple(op, arg1, arg2, result));
    }

    /**
     * Prints all generated quadruples to standard output.
     */
    public void printQuadruples() {
        for (int i = 0; i < quadruples.size(); i++) {
            System.out.printf("%d: %s\n", i, quadruples.get(i));
        }
    }

    @Override
    public String visitProgram(MiniSoftParser.ProgramContext ctx) {
        // Process all statements
        for (MiniSoftParser.StatementContext stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }
        return null;
    }

    @Override
    public String visitVariableDeclaration(MiniSoftParser.VariableDeclarationContext ctx) {
        // No code generation needed for declarations
        return null;
    }

    @Override
    public String visitArrayDeclaration(MiniSoftParser.ArrayDeclarationContext ctx) {
        // No code generation needed for array declarations
        return null;
    }

    @Override
    public String visitConstantDeclaration(MiniSoftParser.ConstantDeclarationContext ctx) {
        String name = ctx.identifier().getText();
        String value = visit(ctx.expression());

        // Assign the constant value
        emit(Quadruple.ASSIGN, value, null, name);
        return null;
    }

    @Override
    public String visitAssignment(MiniSoftParser.AssignmentContext ctx) {
        String target = ctx.identifier().getText();
        String value = visit(ctx.expression());

        // Generate assignment quadruple
        emit(Quadruple.ASSIGN, value, null, target);
        return null;
    }

    @Override
    public String visitIfStatement(MiniSoftParser.IfStatementContext ctx) {
        // Generate labels for control flow
        String elseLabel = newLabel();
        String endLabel = newLabel();

        // Push labels onto stacks
        elseLabels.push(elseLabel);
        ifEndLabels.push(endLabel);

        // Process condition
        String conditionResult = visit(ctx.condition());

        // If condition is false, jump to else label
        emit(Quadruple.JZ, conditionResult, null, elseLabel);

        // Process 'then' block statements
        // In the grammar, statements appear directly in the if block
        for (int i = 0; i < ctx.statement(0).getChildCount(); i++) {
            if (ctx.statement(0).getChild(i) instanceof MiniSoftParser.StatementContext) {
                visit(ctx.statement(0).getChild(i));
            }
        }

        // After 'then' block, jump to end (skip the else block)
        emit(Quadruple.JMP, null, null, endLabel);

        // Emit else label
        emit(Quadruple.LABEL, null, null, elseLabel);

        // Process 'else' block if it exists
        if (ctx.statement().size() > 1) {
            for (int i = 0; i < ctx.statement(1).getChildCount(); i++) {
                if (ctx.statement(1).getChild(i) instanceof MiniSoftParser.StatementContext) {
                    visit(ctx.statement(1).getChild(i));
                }
            }
        }

        // Emit end label
        emit(Quadruple.LABEL, null, null, endLabel);

        // Pop labels from stacks
        elseLabels.pop();
        ifEndLabels.pop();

        return null;
    }

    @Override
    public String visitDoWhileLoop(MiniSoftParser.DoWhileLoopContext ctx) {
        // Generate labels for loop start and end
        String loopStartLabel = newLabel();
        String loopEndLabel = newLabel();

        // Push labels onto stacks
        loopStartLabels.push(loopStartLabel);
        loopEndLabels.push(loopEndLabel);

        // Emit loop start label
        emit(Quadruple.LABEL, null, null, loopStartLabel);

        // Process loop body
        for (MiniSoftParser.StatementContext stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }

        // Process condition
        String conditionResult = visit(ctx.condition());

        // If condition is true, jump back to start
        emit(Quadruple.JMPF, conditionResult, null, loopStartLabel);

        // Emit loop end label
        emit(Quadruple.LABEL, null, null, loopEndLabel);

        // Pop labels from stacks
        loopStartLabels.pop();
        loopEndLabels.pop();

        return null;
    }

    @Override
    public String visitForLoop(MiniSoftParser.ForLoopContext ctx) {
        String loopVar = ctx.identifier().getText();

        // Generate labels for loop
        String loopStartLabel = newLabel();
        String loopEndLabel = newLabel();

        // Push labels onto stacks
        loopStartLabels.push(loopStartLabel);
        loopEndLabels.push(loopEndLabel);

        // Initialize loop variable
        String fromValue = visit(ctx.expression(0));
        emit(Quadruple.ASSIGN, fromValue, null, loopVar);

        // Evaluate end value and step once (optimization)
        String toValue = visit(ctx.expression(1));
        String stepValue = visit(ctx.expression(2));

        // Store to and step values in temporary variables
        String toTemp = newTemp();
        String stepTemp = newTemp();
        emit(Quadruple.ASSIGN, toValue, null, toTemp);
        emit(Quadruple.ASSIGN, stepValue, null, stepTemp);

        // Loop start label
        emit(Quadruple.LABEL, null, null, loopStartLabel);

        // Check loop condition (loopVar <= toTemp)
        String conditionTemp = newTemp();
        emit(Quadruple.LTE, loopVar, toTemp, conditionTemp);

        // If condition is false, exit loop
        emit(Quadruple.JZ, conditionTemp, null, loopEndLabel);

        // Process all statements in the loop body
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof MiniSoftParser.StatementContext) {
                visit(ctx.getChild(i));
            }
        }

        // Increment loop variable
        String incrementTemp = newTemp();
        emit(Quadruple.ADD, loopVar, stepTemp, incrementTemp);
        emit(Quadruple.ASSIGN, incrementTemp, null, loopVar);

        // Jump back to condition check
        emit(Quadruple.JMP, null, null, loopStartLabel);

        // Loop end label
        emit(Quadruple.LABEL, null, null, loopEndLabel);

        // Pop labels from stacks
        loopStartLabels.pop();
        loopEndLabels.pop();

        return null;
    }

    @Override
    public String visitInputStatement(MiniSoftParser.InputStatementContext ctx) {
        String target = ctx.identifier().getText();

        // Generate input quadruple
        emit(Quadruple.INPUT, null, null, target);

        return null;
    }

    @Override
    public String visitOutputStatement(MiniSoftParser.OutputStatementContext ctx) {
        // If there is a string literal
        if (ctx.STRING() != null) {
            String message = ctx.STRING().getText();

            // Generate output quadruple for string
            emit(Quadruple.OUTPUT, message, null, null);

            // Process additional variables to output
            for (MiniSoftParser.IdentifierContext idCtx : ctx.identifier()) {
                String varName = idCtx.getText();
                emit(Quadruple.OUTPUT, varName, null, null);
            }
        }
        // If there is only one identifier
        else if (ctx.identifier().size() == 1) {
            String varName = ctx.identifier(0).getText();
            emit(Quadruple.OUTPUT, varName, null, null);
        }

        return null;
    }

    @Override
    public String visitComparisonCondition(MiniSoftParser.ComparisonConditionContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String resultTemp = newTemp();

        // Determine comparison operator
        String operator;
        switch (ctx.comparisonOperator().getText()) {
            case "<":
                operator = Quadruple.LT;
                break;
            case ">":
                operator = Quadruple.GT;
                break;
            case "<=":
                operator = Quadruple.LTE;
                break;
            case ">=":
                operator = Quadruple.GTE;
                break;
            case "==":
                operator = Quadruple.EQ;
                break;
            case "!=":
                operator = Quadruple.NEQ;
                break;
            default:
                operator = Quadruple.EQ; // Default, shouldn't happen
        }

        // Generate comparison quadruple
        emit(operator, left, right, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitAndCondition(MiniSoftParser.AndConditionContext ctx) {
        String left = visit(ctx.condition(0));
        String right = visit(ctx.condition(1));
        String resultTemp = newTemp();

        // Generate AND operation
        emit(Quadruple.AND, left, right, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitOrCondition(MiniSoftParser.OrConditionContext ctx) {
        String left = visit(ctx.condition(0));
        String right = visit(ctx.condition(1));
        String resultTemp = newTemp();

        // Generate OR operation
        emit(Quadruple.OR, left, right, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitNotCondition(MiniSoftParser.NotConditionContext ctx) {
        String condition = visit(ctx.condition());
        String resultTemp = newTemp();

        // Generate NOT operation
        emit(Quadruple.NOT, condition, null, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitParenthesizedCondition(MiniSoftParser.ParenthesizedConditionContext ctx) {
        // Just visit the inner condition - no additional quadruples needed
        return visit(ctx.condition());
    }

    @Override
    public String visitPrimaryExpr(MiniSoftParser.PrimaryExprContext ctx) {
        return visit(ctx.primary());
    }

    @Override
    public String visitArrayAccessExpr(MiniSoftParser.ArrayAccessExprContext ctx) {
        String arrayName = ctx.identifier().getText();
        String index = visit(ctx.expression());
        String resultTemp = newTemp();

        // Generate array access quadruple
        emit(Quadruple.ARRAY_ACCESS, arrayName, index, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitParenExpr(MiniSoftParser.ParenExprContext ctx) {
        // Just visit the inner expression - no additional quadruples needed
        return visit(ctx.expression());
    }

    @Override
    public String visitNegationExpr(MiniSoftParser.NegationExprContext ctx) {
        String expr = visit(ctx.expression());
        String resultTemp = newTemp();

        // For negation, we use subtraction with 0
        emit(Quadruple.SUB, "0", expr, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitMulDivExpr(MiniSoftParser.MulDivExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String resultTemp = newTemp();

        // Determine operator
        String operator = ctx.getText().contains("*") ? Quadruple.MUL : Quadruple.DIV;

        // Generate multiplication/division quadruple
        emit(operator, left, right, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitAddSubExpr(MiniSoftParser.AddSubExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String resultTemp = newTemp();

        // Determine operator
        String operator = ctx.getText().contains("+") ? Quadruple.ADD : Quadruple.SUB;

        // Generate addition/subtraction quadruple
        emit(operator, left, right, resultTemp);

        return resultTemp;
    }

    @Override
    public String visitPrimary(MiniSoftParser.PrimaryContext ctx) {
        // If it's an identifier, just return the name
        if (ctx.identifier() != null) {
            return ctx.identifier().getText();
        }
        // If it's an integer or float literal, return the text value
        else if (ctx.INTEGER() != null) {
            return ctx.INTEGER().getText();
        } else if (ctx.FLOAT() != null) {
            return ctx.FLOAT().getText();
        }

        // Default case (shouldn't happen with valid syntax)
        return "0";
    }
}
