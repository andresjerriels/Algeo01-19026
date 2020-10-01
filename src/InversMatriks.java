import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class InversMatriks {
    // ATTRIBUTES
    public Matriks matriks;

    // CONSTRUCTOR
    public InversMatriks(Matriks M){
        this.matriks = M;
    }

    // METHODS
    // Menggunakan Operasi Baris Elementer
    public void OBEMatriksInvers() {
        /* I.S. Matriks terdefinisi dengan ukuran dan elemen dari pengguna
           F.S. Menghasilkan matriks eselon baris tereduksi */

        // Memperlebar jumlah kolom efektif matriks untuk penyisipan matriks identitas
        int KolAwal = this.matriks.NKolEff;
        this.matriks.NKolEff += this.matriks.NBrsEff;

        // Menambahkan matriks singular ke sisi kanan matriks
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
        /* I.S. Matriks berbentuk eselon baris tereduksi dan mengandung matriks identitas
           F.S. Menghasilkan matriks invers sesuai dengan masukan pengguna */

        // Inisialisasi matriks termporary
        Matriks tmp = this.matriks;

        // Memindahkan matriks invers ke sebelah kiri matriks
        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            int x = 0;
            for(int j = (this.matriks.NBrsEff); j <= this.matriks.getLastIdxKol(); j++){
                this.matriks.Elmt[i][x] = tmp.Elmt[i][j];
                x++;
            }
        }

        // Mengurangi jumlah kolom efektif matriks sehingga menyisakan matriks inversnya saja
        this.matriks.NKolEff -= this.matriks.NBrsEff;
    }

    public boolean IsInversible() {
        /* Mengembalikan nilai true jika matriks memiliki balikan, dan false jika sebaliknya.
        Matriks memiliki balikan jika merupakan matriks persegi dan determinannya tidak nol */

        boolean inversible = false;
        if ((this.matriks.NKolEff == this.matriks.NBrsEff) && (this.matriks.DeterminanDenganKofaktor() != 0)) {
            inversible = true;
        }
        return inversible;
    }

    // Menggunakan Determinan dan Kofaktor
    public void hitungKofaktor(Matriks temp, int p, int q){
        /* I.S. Matriks temp adalah matriks kosong yang terdefinisi, p dan q berturut-turut adalah indeks
                baris dan kolom yang ingin dicari kofaktornya
           F.S. Menghasilkan minor dari matriks atribut pada baris p dan kolom q dan menyimpannya
                ke dalam matriks temp  */

        int brs = 0, kol = 0;

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = 0; j <= this.matriks.getLastIdxKol(); j++){
                // Menyimpan elemen minor ke dalam temp, yaitu elemen saat indeks i ≠ p dan i ≠ q
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
        /* I.S. Matriks terdefinisi dengan ukuran dan elemen dari pengguna dan invertible
           F.S. Menghasilkan matriks invers yang sesuai */

        // Inisialisasi dan assignment sebuah matriks adjoin dan sebuah float determinan
        Matriks adj = new Matriks(this.matriks.NBrsEff, this.matriks.NKolEff);
        double det = this.matriks.DeterminanDenganKofaktor();

        // Menghitung matriks adjoin
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

        // Membagi seluruh elemen adjoin dengan determinan matriks
        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = 0; j <= this.matriks.getLastIdxKol(); j++) {
                this.matriks.Elmt[i][j] /= det;
            }
        }
    }

    public void TulisMatriksInvers() throws IOException {
        /* I.S. Matriks invers terdefinisi
           F.S. Menulis hasil matriks invers yang dapat ditampilkan di layar atau disimpan dalam sebuah file .txt */

        Scanner scan = new Scanner(System.in);
        System.out.println ("#=============================================================================================#");
        System.out.println ("# PENULISAN MATRIKS BALIKAN                                                                   #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

        String pilihan = scan.nextLine();
        while (!pilihan.equals("1") && !pilihan.equals("2")){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.nextLine();
        }

        if (pilihan.equals("1")) {
            System.out.println("Invers dari matriks di atas adalah ");
            this.matriks.TulisMatriks();
        } else {
            System.out.println("Invers dari matriks di atas adalah ");
            this.matriks.TulisMatriksKeFile();
        }
    }

    // Menyelesaikan SPL menggunakan Matriks Invers
    public void SPLInvers() throws IOException{
        /* I.S. Matriks terdefinisi dengan ukuran dan elemen dari pengguna
           F.S. Jika matriks SPL memiliki balikan dan solusinya unik, solusi akan ditampilkan ke layar atau
                disimpan ke dalam file .txt. Jika matriks SPL tidak memiliki balikan, akan ditampilkan
                “Matriks tidak memiliki balikan”. */

        // Memindahkan elemen-elemen kolom terakhir ke dalam matriks yang berbeda yaitu B
        Matriks B = new Matriks(this.matriks.NBrsEff, 1);
        for (int i = 0; i <= B.getLastIdxBrs(); i++) {
            B.Elmt[i][0] = this.matriks.Elmt[i][this.matriks.getLastIdxKol()];
            this.matriks.Elmt[i][this.matriks.getLastIdxKol()] = 0;
        }

        // Jumlah kolom efektif dikurangi 1
        this.matriks.NKolEff -= 1;

        if (this.IsInversible()){
            // Jika matriks invertible, maka proses penghitungan invers matriks dilakukan
            this.OBEMatriksInvers();
            this.hasilInversOBE();
            this.matriks = this.matriks.KalidenganMatriks(B);
            this.TulisSolusiSPL();
        } else {
            System.out.println("Matriks tidak memiliki balikan.");
        }
    }

    public void TulisSolusiSPL() throws IOException {
        /* I.S. Matriks terdefinisi dan berbentuk eselon baris tereduksi
           F.S. Menulis solusi SPL yang dapat ditampilkan ke layar atau disimpan ke dalam file dengan format .txt */

        Scanner scan = new Scanner(System.in);
        System.out.println ("#=============================================================================================#");
        System.out.println ("# PENULISAN SOLUSI SPL                                                                        #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                               #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

        // Menerima masukan pilihan dari pengguna
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
        /* I.S. Matriks terdefinisi dan berbentuk eselon baris tereduksi
           F.S. Menulis solusi SPL yang dapat ditampilkan ke layar */

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.print("x" + (i+1) + " = " + df.format(this.matriks.Elmt[i][0]) + "\n");
        }
    }

    public void SolusiSPLKeFile() throws IOException {
        /* I.S. Matriks terdefinisi dan berbentuk eselon baris tereduksi
           F.S. Menulis solusi SPL disimpan dalam file dengan format .txt */

        // Menerima masukan nama file dari panggilan
        Scanner scan = new Scanner(System.in);
        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
        System.out.println("Contoh: solutions/SolusiSPL.txt");
        String namafile = "../"+scan.nextLine();

        // Menampilkan solusi ke layar dan menyimpannya ke file
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
