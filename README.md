# MiniSoft Compiler

This project is a compiler for the MiniSoft language, a simple programming language for educational purposes.

## Components

Here's a summary of what each file does:

1. **MiniSoft.g4** : ANTLR grammar that defines the lexical and syntactic rules for the MiniSoft language.
2. **SymbolEntity.java** : Represents an entry in the symbol table for variables, constants, and arrays.
3. **SymbolTable.java** : Manages the storage and retrieval of symbols in the compiler.
4. **SymbolTableBuilder.java** : ANTLR listener that builds the symbol table during parsing and performs basic semantic analysis.
5. **Main.java** : The main driver class that orchestrates the compilation process.
6. **example.ms** : An example MiniSoft program to test the compiler.
7. **pom.xml** : Maven project configuration for dependencies and build steps.
8. **compile-and-run.bat** and  **run.bat** : Batch files to compile and run the compiler.

## Usage

To use the compiler:

1. Run `compile-and-run.bat` to build the project and test it on the example file.
2. Use `run.bat [filename]` to run the compiler on a specific MiniSoft source file.
