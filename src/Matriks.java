import java.text.DecimalFormat;
import java.util.Scanner;

public class Matriks {
    public double[][] Elmt = new double[100][100];
    public int NBrsEff;
    public int NKolEff;
    public int IdxBrsMin = 0;
    public int IdxBrsMax = 99;
    public int IdxKolMin = 0;
    public int IdxKolMax = 99;

    Matriks(int BrsEff, int KolEff) {
        MakeEmpty(BrsEff, KolEff);
    }
    public void MakeEmpty(int NB, int NK) {
        this.NBrsEff = NB;
        this.NKolEff = NK;

        for (int i=0;i<NBrsEff;i++) {
            for (int j = 0; i < NKolEff; j++) {
                this.Elmt[i][j] = 0.0;
            }
        }
    }

    public int getLastIdxBrs() {
        return this.NBrsEff - 1;
    }

    public int getLastIdxKol() {
        return this.NKolEff - 1;
    }


    public void BacaMatriks() {
        Scanner scan = new Scanner(System.in);
        for (int i=0;i<NBrsEff;i++){
            for (int j=0;j<NKolEff;j++){
                this.Elmt[i][j] = scan.nextDouble();
            }
        }
    }

    public void TulisMatriks() {
        for (int i = this.IdxBrsMin; i < this.NBrsEff; i++) {
            for (int j = this.IdxKolMin; j < this.NKolEff; j++) {
                DecimalFormat df = new DecimalFormat("#.##");

                if ((i != getLastIdxBrs()) && (j == getLastIdxKol())) {
                    System.out.println(df.format(this.Elmt[i][getLastIdxKol()]) + "\n");
                } else if ((i == getLastIdxBrs()) && (j == getLastIdxKol())) {
                    System.out.println(df.format(this.Elmt[getLastIdxBrs()][getLastIdxKol()]));
                } else {
                    System.out.println(df.format(this.Elmt[i][j]) + " ");
                }
            }
        }
    }

    public boolean IsBrsAllZero(int Idx) {
        int j = this.IdxKolMin;
        while (j <= this.NKolEff){
            if (this.Elmt[Idx][j] != 0){
                return false;
            }
            j++;
        }
        return true;
    }

    public boolean IsKolAllZero(int Idx) {
        int i = 0;
        while (i <= this.NBrsEff){
            if (this.Elmt[i][Idx] != 0){
                return false;
            }
            i++;
        }
        return true;
    }

    public void TukarBrs(int IdxBrs1, int IdxBrs2){
        double tmp;
        for (int j = 0; j <= getLastIdxKol();j++){
            tmp = this.Elmt[IdxBrs1][j];
            this.Elmt[IdxBrs1][j] = this.Elmt[IdxBrs2][j];
            this.Elmt[IdxBrs2][j] = tmp;
        }
    }



}