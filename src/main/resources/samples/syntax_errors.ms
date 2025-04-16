MainPrgm SyntaxErrors
Var                         <! - Error: Missing semicolon after program name - !>
    let a: Int;
    let b: Float
    let c: Int;             <! - Error: Missing semicolon after declaration - !>
    
    @define Const PI : Float 3.14;  <! - Error: Missing = in constant definition - !>
BeginPg
{
    <! - Missing semicolons - !>
    a := 5                  <! - Error: Missing semicolon - !>
    b := 10.5;
    
    <! - Mismatched parentheses - !>
    c := (a + b;            <! - Error: Unmatched parenthesis - !>
    
    <! - Mismatched braces in control structures - !>
    if (a > b) then {
        output("a is greater");
                            <! - Error: Missing closing brace - !>
    
    <! - Missing keywords - !>
    if (a < b) {            <! - Error: Missing 'then' keyword - !>
        output("a is less");
    }
    
    <! - Invalid assignment - !>
    a + b := 10;            <! - Error: Left side of assignment is not a variable - !>
    
    <! - Wrong keyword order - !>
    do while (a > 0) {      <! - Error: Wrong do-while structure - !>
        a := a - 1;
    }
    
    <! - Missing 'then' in if statement - !>
    if (a == 5)             <! - Error: Missing 'then' keyword - !>
    {
        output("a is 5");
    }
    
    <! - Extra tokens - !>
    a := 5 +;               <! - Error: Expression missing right operand - !>
}
EndPg