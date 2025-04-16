MainPrgm SemanticErrors;
Var
    <! - Variable and type errors - !>
    let a: Int;
    let b: Float;
    let c: Int;
    let arr: [Int; 5];
    let dup: Int;
    let dup: Float;         <! - Error: Duplicate declaration - !>
    @define Const MAX: Int = 100;
BeginPg
{
    <! - Type mismatch errors - !>
    a := 5.45;
    
    <! - Undeclared variables - !>
    undeclared := 100;      <! - Error: Undeclared identifier - !>
    
    <! - Type mismatch in operations - !>
    a := a + b;             <! - Error: Adding Int and Float - !>
    
    <! - Constants modification - !>
    MAX := 200;             <! - Error: Modifying a constant - !>
    
    <! - Incorrect array access - !>
    a[0] := 5;              <! - Error: Scalar used as array - !>
    arr := 10;              <! - Error: Array used as scalar - !>
    arr[5] := 10;           <! - Error: Array index out of bounds - !>

    <! - Type errors in expressions - !>
    if (a > 0) then {
        c := c + 1;
    }
    
    <! - Type errors in logical expressions - !>
    if (a == b) then {      <! - Error: Comparing Int and Float - !>
        c := c + 1;
    }
    
    <! - Division by zero - !>
    a := 10 / 0;            <! - Error: Division by zero - !>
    
    <! - Input to invalid target - !>
    input(MAX);             <! - Error: Input to constant - !>
    input(arr);             <! - Error: Input to entire array - !>
}
EndPg;