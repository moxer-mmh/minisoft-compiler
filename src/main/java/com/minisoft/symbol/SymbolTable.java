package com.minisoft.symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, SymbolEntity> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public void addSymbol(SymbolEntity entity) {
        symbols.put(entity.getName(), entity);
    }

    public boolean symbolExists(String name) {
        return symbols.containsKey(name);
    }

    public SymbolEntity lookupSymbol(String name) {
        return symbols.get(name);
    }

    public void printSymbolTable() {
        System.out.println("\n=== Symbol Table ===");
        System.out.println("Name\tType\tEntity Type\tLine:Col\tValue/Size");
        System.out.println("--------------------------------------------------------");

        for (SymbolEntity entity : symbols.values()) {
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getName()).append("\t")
                    .append(entity.getDataType()).append("\t")
                    .append(entity.getEntityType()).append("\t\t")
                    .append(entity.getLine()).append(":").append(entity.getColumn()).append("\t");

            if (entity.getEntityType().equals("array")) {
                sb.append("size=").append(entity.getArraySize());
            } else if (entity.getValue() != null) {
                sb.append(entity.getValue());
            }

            System.out.println(sb.toString());
        }

        System.out.println("--------------------------------------------------------");
    }

    public int size() {
        return symbols.size();
    }

    public Map<String, SymbolEntity> getSymbols() {
        return symbols;
    }

    public void displaySymbolTable() {
        System.out.println("Symbol Table:");
        for (Map.Entry<String, SymbolEntity> entry : symbols.entrySet()) {
            String name = entry.getKey();
            SymbolEntity entity = entry.getValue();
            System.out.println("Name: " + name + ", Type: " + entity.getDataType() +
                    ", Entity Type: " + entity.getEntityType() +
                    ", Line: " + entity.getLine() + ", Column: " + entity.getColumn());
        }
    }
}