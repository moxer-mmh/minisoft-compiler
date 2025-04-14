package com.minisoft;

import com.minisoft.symbol.SymbolTable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.gui.Trees; // Add this import for GUI visualization
import javax.swing.JFrame;
import javax.swing.JPanel;

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
            
            // Display the parse tree in a GUI window
            showParseTreeFrame(parser, tree, "MiniSoft Parse Tree");
            
            if (parser.getNumberOfSyntaxErrors() > 0) {
                System.err.println("Compilation failed with " + parser.getNumberOfSyntaxErrors() + " syntax errors.");
                System.exit(1);
            }
            
            // Build symbol table
            SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(symbolTableBuilder, tree);
            
            SymbolTable symbolTable = symbolTableBuilder.getSymbolTable();
            
            if (symbolTableBuilder.hasErrors()) {
                System.err.println("Compilation failed with semantic errors in symbol table building phase.");
                symbolTable.displaySymbolTable();
                System.exit(1);
            }
            
            // Perform semantic analysis
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(symbolTable);
            walker.walk(semanticAnalyzer, tree);
            
            // Show compilation results
            if (!semanticAnalyzer.hasErrors()) {
                System.out.println("Compilation successful!");
                System.out.println("Semantic analysis completed with no errors.");
                symbolTable.displaySymbolTable();
            } else {
                System.err.println("Compilation failed with semantic errors during type checking.");
                symbolTable.displaySymbolTable();
                System.exit(1);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading source file: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Shows the parse tree in a GUI window
     * @param parser The parser that generated the tree
     * @param tree The parse tree to display
     * @param title The title for the window
     */
    private static void showParseTreeFrame(Parser parser, ParseTree tree, String title) {
        JFrame frame = new JFrame(title);
        JPanel panel = new JPanel();
        
        // Create the visualization and add it to the panel
        Trees.inspect(tree, parser);
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}