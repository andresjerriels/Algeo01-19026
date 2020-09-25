import java.text.DecimalFormat;

public class InversMatriks {
    public Matriks matriks;

    public InversMatriks(Matriks M){
        this.matriks = M;
    }

    public void bentukMatriksInvers() {
        int KolAwal = this.matriks.NKolEff;
        this.matriks.NKolEff += this.matriks.NBrsEff;

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = KolAwal; j<= this.matriks.getLastIdxKol(); j++){
                if (j == KolAwal){
                    this.matriks.Elmt[i][j] = 1;
                } else {
                    this.matriks.Elmt[i][j] = 0;
                }
            }
            KolAwal++;
        }
        this.matriks.EliminasiGaussJordan();
    }

    public void hasilInvers() {
        Matriks tmp = this.matriks;

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            int x = 0;
            for(int j = (this.matriks.NBrsEff); j <= this.matriks.getLastIdxKol(); j++){
                this.matriks.Elmt[i][x] = tmp.Elmt[i][j];
                x++;
            }
        }

        this.matriks.NKolEff -= this.matriks.NBrsEff;
    }

    public boolean IsInversible() {
        int KolAwal = this.matriks.NKolEff / 2;
        int x = 0, i = 0, j = 0;
        boolean inversible = true;
        while((i <= this.matriks.getLastIdxBrs()) && inversible){
            while((j < KolAwal) && inversible){
                if (j == x){
                    inversible = this.matriks.Elmt[i][j] == 1;
                } else {
                    inversible = this.matriks.Elmt[i][j] == 0;
                }
                j++;
            }
            i++;
            x++;
        }
        return inversible;
    }

    public void SPLInvers(){
        Matriks B = new Matriks(this.matriks.NBrsEff, 1);
        for (int i = 0; i <= B.getLastIdxBrs(); i++) {
            B.Elmt[i][0] = this.matriks.Elmt[i][this.matriks.getLastIdxKol()];
            this.matriks.Elmt[i][this.matriks.getLastIdxKol()] = 0;
        }

        this.matriks.NKolEff -= 1;
        this.bentukMatriksInvers();
        if (this.IsInversible()){
            this.hasilInvers();
            this.matriks.TulisMatriks();
            this.matriks = this.matriks.KalidenganMatriks(B);
            this.TulisSolusiSPL();
        } else {
            System.out.println("Matriks tidak memiliki balikan.");
        }
    }

    public void TulisSolusiSPL(){
        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.print("x" + (i+1) + " = " + df.format(this.matriks.Elmt[i][0]) + "   ");
        }
    }
}
