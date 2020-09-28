import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class InversMatriks {
    public Matriks matriks;

    public InversMatriks(Matriks M){
        this.matriks = M;
    }

    // Menggunakan Operasi Baris Elementer
    public void OBEMatriksInvers() {
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

    public void hasilInversOBE() {
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

    // Menggunakan Determinan dan Kofaktor
    public void hitungKofaktor(Matriks temp, int p, int q){
        int brs = 0, kol = 0;

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = 0; j <= this.matriks.getLastIdxKol(); j++){
                if (i != p && j != q){
                    temp.Elmt[brs][kol++] = this.matriks.Elmt[i][j];

                    if (kol == this.matriks.getLastIdxKol()){
                        kol = 0;
                        brs++;
                    }
                }
            }
        }
    }

    public void hasilInversKofaktor() {
        Matriks adj = new Matriks(this.matriks.NBrsEff, this.matriks.NKolEff);
        double det = this.matriks.DeterminanDenganKofaktor();

        if (this.matriks.NKolEff == 1){
            adj.Elmt[0][0] = 1;
        } else {
            int sign = 1;

            for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++) {
                for (int j = 0; j <= this.matriks.getLastIdxKol(); j++){
                    Matriks temp = new Matriks(this.matriks.NBrsEff, this.matriks.NKolEff);
                    this.hitungKofaktor(temp, i, j);

                    if ((i+j) % 2 == 0){
                        sign = 1;
                    } else {
                        sign = -1;
                    }

                    temp.NBrsEff--;
                    temp.NKolEff--;

                    adj.Elmt[j][i] = sign * temp.DeterminanDenganKofaktor();
                }
            }
        }
        this.matriks = adj;

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = 0; j <= this.matriks.getLastIdxKol(); j++) {
                this.matriks.Elmt[i][j] /= det;
            }
        }
    }

    public void TulisMatriksInvers() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("#=================================================#");
        System.out.println ("# PENULISAN MATRIKS BALIKAN                       #");
        System.out.println ("#-------------------------------------------------#");
        System.out.println ("# Silakan pilih salah pilihan dibawah berikut!    #");
        System.out.println ("#=================================================#");
        System.out.println ("# 1. Tampilkan pada layar                         #");
        System.out.println ("# 2. Simpan dalam file      	                   #");
        System.out.println ("#=================================================#");
        System.out.print ("  Ketik '1' atau '2' pada keyboard: ");

        String pilihan = scan.nextLine();
        while (!pilihan.equals("1") && !pilihan.equals("2")){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.nextLine();
        }

        if (pilihan.equals("1")) {
            this.matriks.TulisMatriks();
        } else {
            this.matriks.TulisMatriksKeFile();
        }
    }

    // Menyelesaikan SPL menggunakan Matriks Invers
    public void SPLInvers() throws IOException{
        Matriks B = new Matriks(this.matriks.NBrsEff, 1);
        for (int i = 0; i <= B.getLastIdxBrs(); i++) {
            B.Elmt[i][0] = this.matriks.Elmt[i][this.matriks.getLastIdxKol()];
            this.matriks.Elmt[i][this.matriks.getLastIdxKol()] = 0;
        }

        this.matriks.NKolEff -= 1;
        this.OBEMatriksInvers();
        if (this.IsInversible()){
            this.hasilInversOBE();
            this.matriks.TulisMatriks();
            this.matriks = this.matriks.KalidenganMatriks(B);
            this.TulisSolusiSPL();
        } else {
            System.out.println("Matriks tidak memiliki balikan.");
        }
    }
    public void TulisSolusiSPL() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("#=================================================#");
        System.out.println ("# PENULISAN SOLUSI SPL                            #");
        System.out.println ("#-------------------------------------------------#");
        System.out.println ("# Silakan pilih salah pilihan dibawah berikut!    #");
        System.out.println ("#=================================================#");
        System.out.println ("# 1. Tampilkan pada layar                         #");
        System.out.println ("# 2. Simpan dalam file      	                   #");
        System.out.println ("#=================================================#");
        System.out.print ("  Ketik '1' atau '2' pada keyboard: ");

        String pilihan = scan.nextLine();
        while (!pilihan.equals("1") && !pilihan.equals("2")){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.nextLine();
        }

        if (pilihan.equals("1")) {
            this.SolusiSPL();
        } else {
            this.SolusiSPLKeFile();
        }

    }

    public void SolusiSPL(){
        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.print("x" + (i+1) + " = " + df.format(this.matriks.Elmt[i][0]) + "\n");
        }
    }

    public void SolusiSPLKeFile() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
        System.out.println("Contoh: solutions/SolusiSPL.txt");
        String namafile = scan.nextLine();

        try (FileOutputStream file = new FileOutputStream(namafile)) {
            byte[] b;
            for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++) {
                DecimalFormat df = new DecimalFormat("#.##");
                String s = ("x" + (i + 1) + " = " + df.format(this.matriks.Elmt[i][0]) + "\n");
                System.out.print(s);
                b = s.getBytes();
                file.write(b);
            }
        }
    }

}
