package com.minisoft.symboltable;

public class Symbol {
    private final String name;
    private final SymbolType type;
    private final boolean isConstant;
    private Object value;
    private int arraySize;
    private final int line;
    private final int column;

    public Symbol(String name, SymbolType type, boolean isConstant, int line, int column) {
        this.name = name;
        this.type = type;
        this.isConstant = isConstant;
        this.line = line;
        this.column = column;
    }

    public Symbol(String name, SymbolType type, Object value, boolean isConstant, int line, int column) {
        this(name, type, isConstant, line, column);
        this.value = value;
    }

    public Symbol(String name, SymbolType type, int arraySize, int line, int column) {
        this(name, type, false, line, column);
        this.arraySize = arraySize;
    }

    public String getName() {
        return name;
    }

    public SymbolType getType() {
        return type;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean isArray() {
        return type == SymbolType.INT_ARRAY || type == SymbolType.FLOAT_ARRAY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Symbol{name='").append(name).append('\'');
        sb.append(", type=").append(type);

        if (isArray()) {
            sb.append(", arraySize=").append(arraySize);
        } else {
            sb.append(", value=").append(value);
        }

        sb.append(", isConstant=").append(isConstant);
        sb.append(", line=").append(line);
        sb.append(", column=").append(column);
        sb.append('}');

        return sb.toString();
    }
}
