package com.minisoft.symbol;

public class SymbolEntity {
    private String name;
    private String dataType;
    private String entityType; // "variable", "constant", "array"
    private int line;
    private int column;
    private Object value;
    private int arraySize;
    
    public SymbolEntity(String name, String dataType, String entityType, int line, int column) {
        this.name = name;
        this.dataType = dataType;
        this.entityType = entityType;
        this.line = line;
        this.column = column;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public String getEntityType() {
        return entityType;
    }
    
    public int getLine() {
        return line;
    }
    
    public int getColumn() {
        return column;
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
    
    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" : ")
          .append(dataType).append(" (").append(entityType).append(") ");
        
        if (entityType.equals("array")) {
            sb.append("[size=").append(arraySize).append("]");
        } else if (value != null) {
            sb.append("= ").append(value);
        }
        
        return sb.toString();
    }
}