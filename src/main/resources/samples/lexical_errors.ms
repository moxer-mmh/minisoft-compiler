MainPrgm LexicalErrors;
Var
    let x: Int;
    let invalid@name: Int;    <! - Error: Invalid identifier with @ - !>
    let 123num: Float;        <! - Error: Identifier starting with number - !>
    
    @define Const PI = 3.14.15;  <! - Error: Invalid float literal with two decimal points - !>
    @define Const MAX = 123ABC;  <! - Error: Invalid integer literal - !>
BeginPg
{
    <! - Unclosed string literal - !>
    output("This string never ends);
    
    <! - Invalid character in program - !>
    x := 10 # 5;             <! - Error: Invalid character # - !>
    
    <! - Invalid escape sequence - !>
    output("Invalid escape \z");
    
    <! - Unclosed comment - !>
    {-- This comment never closes
    
    <! - Invalid numeric literals - !>
    x := 0x123;              <! - Error: Hex literals not supported - !>
    x := 3e10;               <! - Error: Scientific notation not supported - !>
    
    <! - Illegal character in identifier - !>
    let y₂: Int;             <! - Error: Illegal character in identifier - !>
}
EndPg;