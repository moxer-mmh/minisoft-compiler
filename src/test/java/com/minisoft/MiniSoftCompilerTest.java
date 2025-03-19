package com.minisoft;

import com.minisoft.codegen.CodeGenerator;
import com.minisoft.error.ErrorHandler;
import com.minisoft.parser.MiniSoftLexer;
import com.minisoft.parser.MiniSoftParser;
import com.minisoft.semantic.SemanticAnalyzer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiniSoftCompilerTest {

    @Test
    void testSuccessfulCompilation() {
        String sourceCode = "let x: Int;\n" +
                "let y: Float;\n" +
                "x := 10;\n" +
                "y := 3.14;\n" +
                "output(\"Values:\", x, y);\n";

        ErrorHandler errorHandler = new ErrorHandler();

        // Lexical and Syntax analysis
        MiniSoftLexer lexer = new MiniSoftLexer(CharStreams.fromString(sourceCode));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorHandler);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniSoftParser parser = new MiniSoftParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorHandler);

        ParseTree tree = parser.program();

        // Verify no syntax errors
        assertFalse(errorHandler.hasErrors(), "Should not have syntax errors");

        // Semantic analysis
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(errorHandler);
        semanticAnalyzer.visit(tree);

        // Verify no semantic errors
        assertFalse(errorHandler.hasErrors(), "Should not have semantic errors");

        // Intermediate code generation
        CodeGenerator codeGenerator = new CodeGenerator(semanticAnalyzer.getSymbolTable());
        codeGenerator.visit(tree);

        // Verify quadruples were generated
        assertTrue(codeGenerator.getQuadruples().size() > 0, "Should generate quadruples");
    }

    @Test
    void testDuplicateDeclaration() {
        String sourceCode = "let x: Int;\n" +
                "let x: Float;\n"; // Duplicate declaration

        ErrorHandler errorHandler = new ErrorHandler();

        // Lexical and Syntax analysis
        MiniSoftLexer lexer = new MiniSoftLexer(CharStreams.fromString(sourceCode));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorHandler);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniSoftParser parser = new MiniSoftParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorHandler);

        ParseTree tree = parser.program();

        // Verify no syntax errors
        assertFalse(errorHandler.hasErrors(), "Should not have syntax errors");

        // Semantic analysis
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(errorHandler);
        semanticAnalyzer.visit(tree);

        // Verify semantic error was detected
        assertTrue(errorHandler.hasErrors(), "Should detect duplicate declaration");
    }

    @Test
    void testUndeclaredIdentifier() {
        String sourceCode = "x := 10;\n"; // Undeclared identifier

        ErrorHandler errorHandler = new ErrorHandler();

        // Lexical and Syntax analysis
        MiniSoftLexer lexer = new MiniSoftLexer(CharStreams.fromString(sourceCode));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorHandler);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        MiniSoftParser parser = new MiniSoftParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorHandler);

        ParseTree tree = parser.program();

        // Verify no syntax errors
        assertFalse(errorHandler.hasErrors(), "Should not have syntax errors");

        // Semantic analysis
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(errorHandler);
        semanticAnalyzer.visit(tree);

        // Verify semantic error was detected
        assertTrue(errorHandler.hasErrors(), "Should detect undeclared identifier");
    }
}
