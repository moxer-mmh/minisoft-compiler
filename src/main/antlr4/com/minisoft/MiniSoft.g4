grammar MiniSoft;

// Parser rules
program : statement* EOF ;

statement
    : declaration
    | assignment
    | ifStatement
    | loopStatement
    | ioStatement
    ;

declaration
    : variableDeclaration
    | arrayDeclaration
    | constantDeclaration
    ;

variableDeclaration : LET identifier ':' dataType ';' ;
arrayDeclaration : LET identifier ':' '[' dataType ';' INTEGER ']' ';' ;
constantDeclaration : DEFINE CONST identifier ':' dataType '=' expression ';' ;

assignment : identifier ':=' expression ';' ;

ifStatement 
    : IF '(' condition ')' THEN '{' statement* '}' 
      (ELSE '{' statement* '}')?
    ;

loopStatement
    : doWhileLoop
    | forLoop
    ;

doWhileLoop : DO '{' statement* '}' WHILE '(' condition ')' ';' ;

forLoop : FOR identifier FROM expression TO expression STEP expression '{' statement* '}' ;

ioStatement
    : inputStatement
    | outputStatement
    ;

inputStatement : INPUT '(' identifier ')' ';' ;
outputStatement : OUTPUT '(' (STRING (',' identifier)* | identifier) ')' ';' ;

condition
    : expression comparisonOperator expression             #comparisonCondition
    | condition AND condition                              #andCondition
    | condition OR condition                               #orCondition
    | '!' condition                                        #notCondition
    | '(' condition ')'                                    #parenthesizedCondition
    ;

expression
    : primary                                              #primaryExpr
    | identifier '[' expression ']'                        #arrayAccessExpr
    | '(' expression ')'                                   #parenExpr
    | '-' expression                                       #negationExpr
    | expression ('*' | '/') expression                    #mulDivExpr
    | expression ('+' | '-') expression                    #addSubExpr
    ;

primary
    : identifier
    | INTEGER
    | FLOAT
    ;

identifier : IDENTIFIER ;

dataType
    : INT
    | FLOAT_TYPE
    ;

comparisonOperator
    : '<'
    | '>'
    | '>='
    | '<='
    | '=='
    | '!='
    ;

// Lexer rules
LET : 'let' ;
DEFINE : '@define' ;
CONST : 'Const' ;
IF : 'if' ;
THEN : 'then' ;
ELSE : 'else' ;
DO : 'do' ;
WHILE : 'while' ;
FOR : 'for' ;
FROM : 'from' ;
TO : 'to' ;
STEP : 'step' ;
INPUT : 'input' ;
OUTPUT : 'output' ;
INT : 'Int' ;
FLOAT_TYPE : 'Float' ;
AND : 'AND' ;
OR : 'OR' ;

INTEGER : SIGN? DIGIT+ ;
FLOAT : SIGN? DIGIT+ '.' DIGIT* ;
IDENTIFIER : LETTER (LETTER | DIGIT | '_')* ;
STRING : '"' .*? '"' ;

fragment SIGN : '(' ('-' | '+') ')' ;
fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;

// Comments
SINGLE_LINE_COMMENT : '< !-' .*? '- !>' -> skip ;
MULTI_LINE_COMMENT : '{--' .*? '--}' -> skip ;

WS : [ \t\r\n]+ -> skip ;
