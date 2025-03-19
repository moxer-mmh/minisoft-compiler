< !- Simple MiniSoft program to calculate factorial - !>

let n: Int;
let factorial: Int;
@define Const MAX_N: Int=10;

input(n);

< !- Ensure n is in valid range - !>
if (n > MAX_N) then {
    output("Input too large, using maximum value");
    n := MAX_N;
}

if (n < 0) then {
    output("Factorial not defined for negative numbers");
} else {
    factorial := 1;
    
    if (n > 0) then {
        for i from 1 to n step 1 {
            factorial := factorial * i;
        }
    }
    
    output("Factorial calculation: ", n, factorial);
}
