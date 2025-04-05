MainPrgm L3_software;
Var
    <! - Partie declaration - !>
    let x,y,z: Int;
    let A,B:[Int;10];
    @define Const Software: Int= 91 ;
BeginPg
{
    {--Ceci est un commentaire sur
    Plusieurs lignes --}
    x := 5;
    y := (x + 3) * 2;
    
    if (x > 3) then
    {
        output("x is greater than 3", x);
    } else {
        output("x is not greater than 3", x);
    }
    
    do {
        x := x - 1;
    } while (x > 0);
}
EndPg;
