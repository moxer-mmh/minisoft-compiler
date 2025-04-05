# MiniSoft Compiler

A compiler implementation for the MiniSoft programming language, designed for educational purposes.

## Project Overview

This project implements a compiler for the MiniSoft language, featuring a complete frontend with lexical analysis, syntax analysis, and symbol table generation. The compiler is built using ANTLR4 for grammar definition and parsing, with Java as the implementation language.

## Current Implementation Status

The compiler currently supports:

- ✅ **Lexical Analysis**: Tokenization of MiniSoft source code using ANTLR4-generated lexer
- ✅ **Syntax Analysis**: Parsing of tokens into an Abstract Syntax Tree using ANTLR4-generated parser
- ✅ **Symbol Table Generation**: Collection and validation of program identifiers, types, and scopes

## Future Work

The following features are planned for future implementation:

- ⏳ **Semantic Analysis**: Type checking and semantic validation
- ⏳ **Intermediate Code Generation**: Production of quadruples or other intermediate representation
- ⏳ **Code Optimization**: Basic optimization techniques
- ⏳ **Target Code Generation**: Compilation to executable format

## Project Structure

```
minisoft-compiler/
├── src/
│   ├── main/
│   │   ├── antlr4/com/minisoft/        # ANTLR grammar definitions
│   │   │   └── MiniSoft.g4             # MiniSoft language grammar
│   │   └── java/com/minisoft/
│   │       ├── Main.java               # Compiler entry point
│   │       ├── SymbolTableBuilder.java # Symbol table construction
│   │       └── symbol/
│   │           ├── SymbolEntity.java   # Symbol table entries
│   │           └── SymbolTable.java    # Symbol table management
├── target/                             # Generated and compiled files
├── pom.xml                             # Maven project configuration
├── compile-and-run.bat                 # Utility script for Windows
└── run.bat                             # Script to run the compiler
```

## MiniSoft Language

MiniSoft is a simple programming language with:

- Variable and constant declarations
- Basic data types (Int, Float)
- Array support
- Control structures (if-else, loops)
- Input/output operations

## Usage

### Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher

### Building the Project

To build the compiler from source:

```bash
mvn clean package
```

This will generate a JAR file with all dependencies in the `target` directory.

### Running the Compiler

You can use the provided batch files for convenience:

1. To build and test with the example file:

   ```bash
   .\compile-and-run.bat
   ```
2. To compile a specific MiniSoft source file:

   ```bash
   .\run.bat path/to/your/source.ms
   ```

Alternatively, you can run the JAR directly:

```bash
java -jar target/minisoft-compiler-1.0-SNAPSHOT-jar-with-dependencies.jar path/to/your/source.ms
```

### Compilation Output

When successful, the compiler will display:

- "Compilation successful!" message
- The complete symbol table

If errors are detected, the compiler will report:

- Lexical errors (with line and position)
- Syntax errors (with line and position)
- Semantic errors (in future versions)

## License

This project is intended for educational purposes.

## Acknowledgements

This project was created as part of a compiler design course. The implementation follows standard compiler construction techniques and utilizes the ANTLR parser generator framework.
