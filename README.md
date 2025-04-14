# MiniSoft Compiler

A full-featured compiler implementation for the MiniSoft programming language, designed for educational purposes.

## Project Overview

This project implements a compiler for the MiniSoft language, featuring a complete frontend with lexical analysis, syntax analysis, symbol table generation, and semantic analysis. The compiler is built using ANTLR4 for grammar definition and parsing, with Java as the implementation language.

## Current Implementation Status

The compiler currently supports:

- ✅ **Lexical Analysis**: Tokenization of MiniSoft source code using ANTLR4-generated lexer
- ✅ **Syntax Analysis**: Parsing of tokens into an Abstract Syntax Tree using ANTLR4-generated parser
- ✅ **Symbol Table Generation**: Collection and validation of program identifiers, types, and scopes
- ✅ **Semantic Analysis**: Type checking, expression validation, and static error detection

## Future Work

The following features are planned for future implementation:

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
│   │   ├── java/com/minisoft/
│   │   │   ├── Main.java               # Compiler entry point
│   │   │   ├── SemanticAnalyzer.java   # Semantic analysis implementation
│   │   │   ├── SymbolTableBuilder.java # Symbol table construction
│   │   │   └── symbol/
│   │   │       ├── SymbolEntity.java   # Symbol table entries
│   │   │       └── SymbolTable.java    # Symbol table management
│   │   └── resources/
│   │       └── samples/                # Example MiniSoft programs
│   │           └── example.ms          # Sample MiniSoft code
├── target/                             # Generated and compiled files
├── pom.xml                             # Maven project configuration
├── compile-and-run.bat                 # Utility script for Windows
└── run.bat                             # Script to run the compiler
```

## MiniSoft Language Features

MiniSoft is a structured programming language with the following features:

- **Program Structure**: Programs start with `MainPrgm` identifier and include declaration and executable sections
- **Variable Declarations**: Using `let` keyword with support for multiple variables of the same type
- **Constants**: Defined with `@define Const` syntax
- **Data Types**:
  - `Int`: Integer values
  - `Float`: Floating-point numbers
- **Arrays**: One-dimensional arrays with fixed size (`[type;size]` syntax)
- **Comments**:
  - Single-line: `<! - text - !>`
  - Multi-line: `{-- text --}`
- **Control Structures**:
  - If-else statements: `if (condition) then { ... } else { ... }`
  - Do-while loops: `do { ... } while (condition);`
  - For loops: `for var from expr to expr step expr { ... }`
- **Expressions**:
  - Arithmetic operators: `+`, `-`, `*`, `/`
  - Comparison operators: `>`, `<`, `>=`, `<=`, `==`, `!=`
  - Logical operators: `AND`, `OR`, `!` (NOT)
- **Input/Output**:
  - Input: `input(variable);`
  - Output: `output("message", expression);`

## Semantic Features and Error Checking

The compiler performs extensive semantic analysis including:

- Type checking for expressions and assignments
- Array bounds validation for constant indices
- Variable declaration and scope validation
- Constant modification prevention
- Logical expression type validation
- Division by zero detection (for constant expressions)
- Loop control variable verification

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
- The symbol table showing all identifiers, types, and properties
- A parse tree visualization (GUI window)

If errors are detected, the compiler will report:

- **Lexical Errors**: Issues with tokens and character recognition
- **Syntax Errors**: Problems with the structure of the program
- **Semantic Errors**: Including:
  - Type mismatches
  - Undeclared variables
  - Double declarations
  - Array bounds issues
  - Constant modification attempts
  - Invalid expressions

## Example Code

```
MainPrgm L3_software;
Var
    <! - Partie declaration - !>
    let x,y,z: Int;
    let A,B:[Int;10];
    @define Const Software: Int= 91 ;
BeginPg
{
    {--Ceci est un commentaire sur
    Plusieurs lignes --}
    x := 5;
    y := (x + 3) * 2;
    
    if (x > 3) then
    {
        output("x is greater than 3", x);
    } else {
        output("x is not greater than 3", x);
    }
    
    do {
        x := x - 1;
    } while (x > 0);
}
EndPg;
```

## License

This project is intended for educational purposes.

## Acknowledgements

This project was created as part of a compiler design course. The implementation follows standard compiler construction techniques and utilizes the ANTLR parser generator framework.
