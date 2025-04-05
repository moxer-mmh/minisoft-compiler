grammar MiniSoft;

// Parser Rules
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

declarations
    : (variableDeclaration | constantDeclaration)*
    ;

variableDeclaration
    // Adjusted array declaration based on example.ms: let A,B:[Int;10];
    : LET idList COLON type SEMI
    | LET idList COLON LBRACK type SEMI INT RBRACK SEMI
    ;

constantDeclaration
    : DEFINE CONST ID COLON type ASSIGN constValue SEMI
    ;

idList
    : ID (COMMA ID)*
    ;

type
    : INT_TYPE
    | FLOAT_TYPE
    ;

constValue
    : INT
    | FLOAT
    | LPAREN sign INT RPAREN
    | LPAREN sign FLOAT RPAREN
    ;

sign
    : PLUS
    | MINUS
    ;

expression
    : logicalOrExpression
    ;

logicalOrExpression
    : logicalAndExpression (OR logicalAndExpression)*
    ;

logicalAndExpression
    : negationExpression (AND negationExpression)*
    ;

negationExpression
    : NOT negationExpression
    | comparisonExpression
    ;

comparisonExpression
    : additiveExpression comparisonOperator additiveExpression
    | LPAREN logicalOrExpression RPAREN // Use logicalOrExpression for parenthesized expressions
    | additiveExpression // Allow single additive expression
    ;


additiveExpression
    : multiplicativeExpression ( ( PLUS | MINUS ) multiplicativeExpression )*
    ;

multiplicativeExpression
    : primaryExpression ( ( MUL | DIV ) primaryExpression )*
    ;

primaryExpression
    : constValue
    | ID
    | ID LBRACK expression RBRACK // Array access
    | LPAREN expression RPAREN    // Parentheses for grouping
     ;

comparisonOperator
    : GT | LT | GE | LE | EQ | NE
    ;

condition
    : logicalOrExpression
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
    : ID VAR_ASSIGN expression SEMI
    | ID LBRACK expression RBRACK VAR_ASSIGN expression SEMI
    ;

ifStatement
    : IF LPAREN condition RPAREN THEN
      LBRACE instructions RBRACE
      (ELSE LBRACE instructions RBRACE)?
    ;

doWhileLoop
    : DO
      LBRACE instructions RBRACE
      WHILE LPAREN condition RPAREN SEMI
    ;

forLoop
    : FOR ID FROM expression TO expression STEP expression
      LBRACE instructions RBRACE
    ;

inputStatement
    : INPUT LPAREN ID RPAREN SEMI
    ;

outputStatement
    : OUTPUT LPAREN outputArgList RPAREN SEMI
    ;

outputArgList
    : outputArg (COMMA outputArg)*
    ;

outputArg
    : STRING
    | expression
    ;

// Lexer Rules (Order: Comments, Keywords, Operators, Literals, WS)

// Comments - Place these FIRST - Adjusted SINGLE_LINE_COMMENT for spaces
SINGLE_LINE_COMMENT : '<!' ' ' '-' .*? '-' ' ' '!' '>' -> skip ; // Match <! space - ... - space !>
MULTI_LINE_COMMENT  : '{--' .*? '--}' -> skip ;                // Match {-- ... --}

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

// Punctuation and Operators (Define AFTER comments and keywords)
SEMI : ';';
COLON : ':';
LBRACK : '[';
RBRACK : ']';
ASSIGN : '='; // For constant declaration
COMMA : ',';
LPAREN : '(';
RPAREN : ')';
PLUS : '+';
MINUS : '-'; // Also in comments, but comment rule is first
NOT : '!';   // Also in comments
MUL : '*';
DIV : '/';
GT : '>';   // Also in comments
LT : '<';   // Defined AFTER SINGLE_LINE_COMMENT
GE : '>=';
LE : '<=';
EQ : '==';
NE : '!=';
VAR_ASSIGN : ':=';
LBRACE : '{'; // Defined AFTER MULTI_LINE_COMMENT
RBRACE : '}'; // Defined AFTER MULTI_LINE_COMMENT

// General Identifiers and Literals (Define last)
ID : [a-zA-Z][a-zA-Z0-9_]* ;
INT : [0-9]+ ;
FLOAT : [0-9]+ '.' [0-9]+ ;
STRING : '"' (~["\r\n] | '\\"')* '"' ;

// Whitespace (Skipped)
WS : [ \t\r\n]+ -> skip ;
