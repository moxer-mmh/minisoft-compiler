package com.minisoft.symboltable;

import java.util.*;

public class SymbolTable {
    private final List<Map<String, Symbol>> scopes;

    public SymbolTable() {
        scopes = new ArrayList<>();
        // Initialize global scope
        enterScope();
    }

    public void enterScope() {
        scopes.add(new HashMap<>());
    }

    public void exitScope() {
        if (scopes.size() > 1) { // Keep at least global scope
            scopes.remove(scopes.size() - 1);
        }
    }

    public boolean declare(Symbol symbol) {
        Map<String, Symbol> currentScope = scopes.get(scopes.size() - 1);

        if (currentScope.containsKey(symbol.getName())) {
            return false; // Symbol already exists in current scope
        }

        currentScope.put(symbol.getName(), symbol);
        return true;
    }

    public Symbol lookup(String name) {
        // Search from current scope outward
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol symbol = scopes.get(i).get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null; // Symbol not found
    }

    public Symbol lookupCurrentScope(String name) {
        return scopes.get(scopes.size() - 1).get(name);
    }

    public Collection<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>();
        for (Map<String, Symbol> scope : scopes) {
            allSymbols.addAll(scope.values());
        }
        return allSymbols;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Symbol Table:\n");
        for (int i = 0; i < scopes.size(); i++) {
            sb.append("Scope ").append(i).append(":\n");
            for (Symbol symbol : scopes.get(i).values()) {
                sb.append("  ").append(symbol).append('\n');
            }
        }
        return sb.toString();
    }
}
