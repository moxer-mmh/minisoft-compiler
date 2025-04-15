/**
 * Grammar file for the MiniSoft programming language
 * 
 * Defines the syntax for a simple structured programming language with:
 * - Variables and constants
 * - Integer and float data types
 * - Arrays
 * - Control flow (if-else, do-while, for)
 * - Basic expressions with operators
 * - Input/output capabilities
 */
grammar MiniSoft;

// Parser Rules

/**
 * Program structure definition
 */
program
    : MAIN_PRGM ID SEMI
      VAR
      declarations
      BEGIN_PG
      LBRACE
      instructions
      RBRACE
      END_PG SEMI
    ;

/**
 * Declaration section for variables and constants
 */
declarations
    : (variableDeclaration | constantDeclaration)*
    ;

/**
 * Variable declaration syntax
 */
variableDeclaration
    : LET idList COLON type SEMI
    | LET idList COLON LBRACK type SEMI INT RBRACK SEMI
    ;

/**
 * Constant declaration syntax
 */
constantDeclaration
    : DEFINE CONST ID COLON type ASSIGN constValue SEMI
    ;

/**
 * List of identifiers for multi-variable declarations
 */
idList
    : ID (COMMA ID)*
    ;

/**
 * Data types supported by MiniSoft
 */
type
    : INT_TYPE
    | FLOAT_TYPE
    ;

/**
 * Constant value definitions
 */
constValue
    : INT
    | FLOAT
    | LPAREN sign INT RPAREN
    | LPAREN sign FLOAT RPAREN
    ;

/**
 * Sign for numeric values
 */
sign
    : PLUS
    | MINUS
    ;

/**
 * General expression syntax
 */
expression
    : logicalOrExpression
    ;

/**
 * Expression with logical OR operations
 */
logicalOrExpression
    : logicalAndExpression (OR logicalAndExpression)*
    ;

/**
 * Expression with logical AND operations
 */
logicalAndExpression
    : negationExpression (AND negationExpression)*
    ;

/**
 * Expression with logical NOT operation
 */
negationExpression
    : NOT negationExpression
    | comparisonExpression
    ;

/**
 * Expression with comparison operations
 */
comparisonExpression
    : additiveExpression comparisonOperator additiveExpression
    | LPAREN logicalOrExpression RPAREN 
    | additiveExpression 
    ;

/**
 * Expression with addition/subtraction operations
 */
additiveExpression
    : multiplicativeExpression ( ( PLUS | MINUS ) multiplicativeExpression )*
    ;

/**
 * Expression with multiplication/division operations
 */
multiplicativeExpression
    : primaryExpression ( ( MUL | DIV ) primaryExpression )*
    ;

/**
 * Basic expression elements
 */
primaryExpression
    : constValue
    | ID
    | ID LBRACK expression RBRACK // Array access
    | LPAREN expression RPAREN    // Parentheses for grouping
     ;

/**
 * Comparison operator types
 */
comparisonOperator
    : GT | LT | GE | LE | EQ | NE
    ;

/**
 * Condition syntax for control statements
 */
condition
    : logicalOrExpression
    ;

/**
 * Program instructions container
 */
instructions
    : instruction*
    ;

/**
 * Individual instruction types
 */
instruction
    : assignment
    | ifStatement
    | doWhileLoop
    | forLoop
    | inputStatement
    | outputStatement
    ;

/**
 * Assignment statement syntax
 */
assignment
    : ID VAR_ASSIGN expression SEMI
    | ID LBRACK expression RBRACK VAR_ASSIGN expression SEMI
    ;

/**
 * If-else statement syntax
 */
ifStatement
    : IF LPAREN condition RPAREN THEN
      LBRACE instructions RBRACE
      (ELSE LBRACE instructions RBRACE)?
    ;

/**
 * Do-while loop syntax
 */
doWhileLoop
    : DO
      LBRACE instructions RBRACE
      WHILE LPAREN condition RPAREN SEMI
    ;

/**
 * For loop syntax
 */
forLoop
    : FOR ID FROM expression TO expression STEP expression
      LBRACE instructions RBRACE
    ;

/**
 * Input statement syntax
 */
inputStatement
    : INPUT LPAREN ID RPAREN SEMI
    ;

/**
 * Output statement syntax
 */
outputStatement
    : OUTPUT LPAREN outputArgList RPAREN SEMI
    ;

/**
 * Output arguments list
 */
outputArgList
    : outputArg (COMMA outputArg)*
    ;

/**
 * Output argument types
 */
outputArg
    : STRING
    | expression
    ;

// Lexer Rules - Order matters for correct tokenization

// Comments - Place FIRST for priority
SINGLE_LINE_COMMENT : '<!' ' ' '-' .*? '-' ' ' '!' '>' -> skip ; 
MULTI_LINE_COMMENT  : '{--' .*? '--}' -> skip ;                

// Keywords
MAIN_PRGM : 'MainPrgm';
VAR : 'Var';
BEGIN_PG : 'BeginPg';
END_PG : 'EndPg';
LET : 'let';
DEFINE : '@define';
CONST : 'Const';
INT_TYPE : 'Int';
FLOAT_TYPE : 'Float';
IF : 'if';
THEN : 'then';
ELSE : 'else';
DO : 'do';
WHILE : 'while';
FOR : 'for';
FROM : 'from';
TO : 'to';
STEP : 'step';
INPUT : 'input';
OUTPUT : 'output';
OR : 'OR';
AND : 'AND';

// Punctuation and Operators
SEMI : ';';
COLON : ':';
LBRACK : '[';
RBRACK : ']';
ASSIGN : '='; 
COMMA : ',';
LPAREN : '(';
RPAREN : ')';
PLUS : '+';
MINUS : '-'; 
NOT : '!';   
MUL : '*';
DIV : '/';
GT : '>';   
LT : '<';   
GE : '>=';
LE : '<=';
EQ : '==';
NE : '!=';
VAR_ASSIGN : ':=';
LBRACE : '{'; 
RBRACE : '}'; 

// Identifiers and Literals
ID : [a-zA-Z][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
FLOAT : [0-9]+ '.' [0-9]+ ;
STRING : '"' (~["\r\n] | '\\"')* '"' ;

// Whitespace - Skip
WS : [ \t\r\n]+ -> skip ;
