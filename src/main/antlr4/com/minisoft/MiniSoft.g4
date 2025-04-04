grammar MiniSoft;

// Parser Rules
program
    : 'MainPrgm' ID ';'
      'Var'
      declarations
      'BeginPg'
      '{'
      instructions
      '}'
      'EndPg' ';'
    ;

declarations
    : (variableDeclaration | constantDeclaration)*
    ;

variableDeclaration
    : 'let' idList ':' type ';'
    | 'let' idList ':' '[' type ';' INT ']' ';'
    ;

constantDeclaration
    : '@define' 'Const' ID ':' type '=' constValue ';'
    ;

idList
    : ID (',' ID)*
    ;

type
    : 'Int'
    | 'Float'
    ;

constValue
    : INT
    | FLOAT
    | '(' sign INT ')'
    | '(' sign FLOAT ')'
    ;

sign
    : '+'
    | '-'
    ;

instructions
    : instruction*
    ;

instruction
    : assignment
    | ifStatement
    | doWhileLoop
    | forLoop
    | inputStatement
    | outputStatement
    ;

assignment
    : ID ':=' expression ';'
    | ID '[' expression ']' ':=' expression ';'
    ;

ifStatement
    : 'if' '(' condition ')' 'then'
      '{'
      instructions
      '}' 
      ('else' 
      '{'
      instructions
      '}')?
    ;

doWhileLoop
    : 'do' 
      '{'
      instructions
      '}' 
      'while' '(' condition ')' ';'
    ;

forLoop
    : 'for' ID 'from' expression 'to' expression 'step' expression 
      '{'
      instructions
      '}'
    ;

inputStatement
    : 'input' '(' ID ')' ';'
    ;

outputStatement
    : 'output' '(' outputArgList ')' ';'
    ;

outputArgList
    : outputArg (',' outputArg)*
    ;

outputArg
    : STRING
    | expression
    ;

// Fix the left-recursive expressions by using ANTLR's preferred structure
expression
    : additiveExpression
    ;

additiveExpression
    : multiplicativeExpression (additiveOp multiplicativeExpression)*
    ;

additiveOp
    : '+'
    | '-'
    ;

multiplicativeExpression
    : primaryExpression (multiplicativeOp primaryExpression)*
    ;

multiplicativeOp
    : '*'
    | '/'
    ;

primaryExpression
    : ID
    | ID '[' expression ']'
    | constValue
    | '(' expression ')'
    ;

condition
    : logicalOrExpression
    ;

logicalOrExpression
    : logicalAndExpression ('OR' logicalAndExpression)*
    ;

logicalAndExpression
    : negationExpression ('AND' negationExpression)*
    ;

negationExpression
    : '!' negationExpression
    | comparisonExpression
    ;

comparisonExpression
    : additiveExpression comparisonOperator additiveExpression
    | '(' logicalOrExpression ')'
    | additiveExpression
    ;

comparisonOperator
    : '>'
    | '<'
    | '>='
    | '<='
    | '=='
    | '!='
    ;

// Lexer Rules
ID : [a-z][a-zA-Z0-9_]* {
        // Check for invalid patterns: more than 14 chars, ending with _, or consecutive _
        String text = getText();
        if (text.length() > 14) {
            // Handle the error - in a real compiler you might want to throw an exception or log this
            System.err.println("Error: Identifier '" + text + "' exceeds maximum length of 14 characters");
        }
        if (text.endsWith("_")) {
            System.err.println("Error: Identifier '" + text + "' cannot end with an underscore");
        }
        if (text.contains("__")) {
            System.err.println("Error: Identifier '" + text + "' cannot contain consecutive underscores");
        }
    };

INT : [0-9]+ ;
FLOAT : [0-9]+ '.' [0-9]+ ;
STRING : '"' (~["\r\n] | '\\"')* '"' ;

// Comments
SINGLE_LINE_COMMENT : '<' '!' '-' .*? '-' '!' '>' -> skip ;
MULTI_LINE_COMMENT : '{' '-' '-' .*? '-' '-' '}' -> skip ;

// Whitespace
WS : [ \t\r\n]+ -> skip ;