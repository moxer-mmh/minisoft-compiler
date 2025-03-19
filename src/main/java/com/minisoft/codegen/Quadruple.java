package com.minisoft.codegen;

/**
 * Represents an intermediate code quadruple with operator, operands, and
 * result.
 */
public class Quadruple {
    public static final String ASSIGN = "=";
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";
    public static final String LT = "<";
    public static final String GT = ">";
    public static final String LTE = "<=";
    public static final String GTE = ">=";
    public static final String EQ = "==";
    public static final String NEQ = "!=";
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String NOT = "!";
    public static final String JMP = "JMP";
    public static final String JZ = "JZ";
    public static final String JMPF = "JMPF";
    public static final String LABEL = "LABEL";
    public static final String INPUT = "INPUT";
    public static final String OUTPUT = "OUTPUT";
    public static final String ARRAY_ACCESS = "[]";
    public static final String ARRAY_ASSIGN = "[]=";

    private final String operator;
    private final String operand1;
    private final String operand2;
    private final String result;

    public Quadruple(String operator, String operand1, String operand2, String result) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperand1() {
        return operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s)",
                operator,
                operand1 != null ? operand1 : "_",
                operand2 != null ? operand2 : "_",
                result != null ? result : "_");
    }
}
