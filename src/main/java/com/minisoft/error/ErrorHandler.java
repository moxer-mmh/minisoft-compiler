package com.minisoft.error;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler extends BaseErrorListener {

    public enum ErrorType {
        LEXICAL,
        SYNTAX,
        SEMANTIC_UNDECLARED_IDENTIFIER,
        SEMANTIC_DUPLICATE_DECLARATION,
        SEMANTIC_TYPE_MISMATCH,
        SEMANTIC_ARRAY_BOUNDS,
        SEMANTIC_CONSTANT_MODIFICATION,
        SEMANTIC_DIVISION_BY_ZERO
    }

    public static class CompilerError {
        private final ErrorType type;
        private final String message;
        private final int line;
        private final int column;
        private final String entity;

        public CompilerError(ErrorType type, String message, int line, int column, String entity) {
            this.type = type;
            this.message = message;
            this.line = line;
            this.column = column;
            this.entity = entity;
        }

        @Override
        public String toString() {
            return String.format("[%s] at line %d:%d - %s: %s",
                    type, line, column, entity, message);
        }
    }

    private final List<CompilerError> errors = new ArrayList<>();

    public void addError(ErrorType type, String message, int line, int column, String entity) {
        errors.add(new CompilerError(type, message, line, column, entity));
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
            int line, int charPositionInLine, String msg, RecognitionException e) {
        addError(ErrorType.SYNTAX, msg, line, charPositionInLine, offendingSymbol.toString());
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void printErrors() {
        errors.forEach(System.err::println);
    }

    public List<CompilerError> getErrors() {
        return new ArrayList<>(errors);
    }
}
