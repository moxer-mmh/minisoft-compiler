MainPrgm ComprehensiveTest;
Var
    <! - Variable declarations - !>
    let a, b, c: Int;
    let x, y, z: Float;
    let numbers: [Int; 10];
    let matrix: [Float; 5];
    @define Const PI: Float = 3.14159;
    @define Const MAX_SIZE: Int = 100;
BeginPg
{
    {-- This is a comprehensive test program 
    that demonstrates most language features --}
    
    <! - Basic assignments - !>
    a := 5;
    b := 10;
    c := a + b;
    x := 3.5;
    y := x * 2.0;
    z := x / y + PI;
    
    <! - Array operations - !>
    numbers[0] := 42;
    numbers[1] := a * 2;
    numbers[2] := numbers[0] + numbers[1];
    matrix[4] := x + y;
    
    <! - Expressions with mixed types - !>
    x := a + 2.5;       <! - Int to Float conversion - !>
    y := (b - 3) * x;
    z := (x + y) / 2.0;
    
    <! - If-else statements - !>
    if (a > b) then {
        output("a is greater than b", a);
    } else {
        output("a is not greater than b", b);
    }
    
    if (x <= y AND z != 0) then {
        output("Complex condition is true");
    }
    
    <! - Do-while loop - !>
    a := 5;
    do {
        output("Counter:", a);
        a := a - 1;
    } while (a > 0);
    
    <! - Logical expressions - !>
    if (a < b OR (c > 0 AND !(x > y))) then {
        output("Complex logical expression evaluated to true");
    }
    
    <! - Nested if statements - !>
    if (a == 0) then {
        output("a is zero");
        if (b > 0) then {
            output("and b is positive");
        } else {
            output("and b is not positive");
        }
    }
    
    <! - Input statement - !>
    output("Enter a value for c:");
    input(c);
    output("You entered:", c);
    
    <! - Using constants in expressions - !>
    a := MAX_SIZE / 2;
    x := PI * 2.0;
    
    <! - Multiple operations - !>
    c := (a + b) * (a - b) / 2;
}
EndPg;