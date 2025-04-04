package com.minisoft;

import com.minisoft.symbol.SymbolTable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar minisoft-compiler.jar <source-file>");
            System.exit(1);
        }
        
        String sourceFilePath = args[0];
        
        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(sourceFilePath)));
            
            // Lexical analysis
            MiniSoftLexer lexer = new MiniSoftLexer(CharStreams.fromString(sourceCode));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
                                       int line, int charPositionInLine, String msg, RecognitionException e) {
                    System.err.println("[Lexical Error] Line " + line + ":" + charPositionInLine + " - " + msg);
                }
            });
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // Syntax analysis
            MiniSoftParser parser = new MiniSoftParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, 
                                       int line, int charPositionInLine, String msg, RecognitionException e) {
                    System.err.println("[Syntax Error] Line " + line + ":" + charPositionInLine + 
                                      " - " + msg);
                }
            });
            
            // Parse the input
            ParseTree tree = parser.program();
            
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Compilation failed with " + parser.getNumberOfSyntaxErrors() + " syntax errors.");
                System.exit(1);
            }
            
            // Build symbol table
            SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(symbolTableBuilder, tree);
            
            SymbolTable symbolTable = symbolTableBuilder.getSymbolTable();
            
            // Show compilation results
            if (!symbolTableBuilder.hasErrors()) {
                System.out.println("Compilation successful!");
                symbolTable.displaySymbolTable();
            } else {
                System.err.println("Compilation failed with semantic errors.");
                symbolTable.displaySymbolTable();
                System.exit(1);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading source file: " + e.getMessage());
            System.exit(1);
        }
    }
}