MainPrgm L3_software;
Var
    <! - Partie declaration - !>
    let x,y,z,i: Int;
    let A,B:[Int;10];
    @define Const Software: Int= 91 ;
BeginPg
{
    {--Ceci est un commentaire sur
    Plusieurs lignes --}
    x := 0;
    y := (x + 3) * 2;
    A[9] := 15;


    if (x > 3) then
    {
        output("x is greater than 3", x);
    } else {
        output("x is not greater than 3", x);
    }
    
    do {
        x := x - 1;
    } while (x > 0);

    for i from 1 to 11 step 2 {
        output(i);
    }
}
EndPg;
