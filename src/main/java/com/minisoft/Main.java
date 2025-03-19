package com.minisoft;

import com.minisoft.codegen.CodeGenerator;
import com.minisoft.error.ErrorHandler;
import com.minisoft.parser.MiniSoftLexer;
import com.minisoft.parser.MiniSoftParser;
import com.minisoft.semantic.SemanticAnalyzer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar minisoft-compiler.jar <source-file>");
            System.exit(1);
        }

        try {
            // Read source file
            String sourceFilePath = args[0];
            String sourceCode = readFile(sourceFilePath);

            // Setup error handler
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

            if (errorHandler.hasErrors()) {
                System.err.println("Compilation failed due to syntax errors:");
                errorHandler.printErrors();
                System.exit(1);
            }

            // Semantic analysis
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(errorHandler);
            semanticAnalyzer.visit(tree);

            if (errorHandler.hasErrors()) {
                System.err.println("Compilation failed due to semantic errors:");
                errorHandler.printErrors();
                System.exit(1);
            }

            // Intermediate code generation
            CodeGenerator codeGenerator = new CodeGenerator(semanticAnalyzer.getSymbolTable());
            codeGenerator.visit(tree);

            System.out.println("Compilation successful!");
            System.out.println("\nGenerated Intermediate Code (Quadruples):");
            codeGenerator.printQuadruples();

        } catch (IOException e) {
            System.err.println("Error reading source file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String readFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        return new String(Files.readAllBytes(filePath));
    }
}
