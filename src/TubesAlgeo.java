import java.io.IOException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class TubesAlgeo {

    public static void TulisMenuWelcome() {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# SELAMAT DATANG!                                                                             #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Andres Jerriel Sinabutar           13519218                                                 #");
        System.out.println ("# Gde Anantha Priharsena             13519026                                                 #");
        System.out.println ("# Shifa Salsabiila                   13519106                                                 #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik 'Y' (untuk memulai program) atau Ketik 'N' (untuk keluar dari program) pada keyboard: #");
    }

    public static void TulisMenuUtama() {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# MENU UTAMA                                                                                  #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu menu di bawah ini!                                                 #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Sistem Persamaan Linier                                                                  #");
        System.out.println ("# 2. Determinan                                                                               #");
        System.out.println ("# 3. Matriks Balikan                                                                          #");
        System.out.println ("# 4. Interpolasi Polinom                                                                      #");
        System.out.println ("# 5. Regresi Linier Berganda                                                                  #");
        System.out.println ("# 6. Keluar Program                                                                           #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1', '2', '3', '4', '5', atau '6' pada keyboard:                                      #");
    }

    public static void MasukanMenuUtamaSalah(){
        System.out.println ("# Masukkan tidak valid!                                                                       #");
        System.out.println ("# Ketik '1', '2', '3', atau '4' pada keyboard:                                                #");
    }

    public static void TulisMenuPenyelesaian () {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# METODE PENYELESAIAN SPL                                                                     #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu metode di bawah ini!                                               #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Metode Eliminasi Gauss                                                                   #");
        System.out.println ("# 2. Metode Eliminasi Gauss-Jordan                                                            #");
        System.out.println ("# 3. Metode Matriks Balikan                                                                   #");
        System.out.println ("# 4. Kaidah Cramer                                                                            #");
        System.out.println ("# 5. Kembali ke MENU UTAMA                                                                    #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1', '2', '3', '4', atau '5' pada keyboard:                                           #");
    }

    public static void MasukanMetodeSalah(){
        System.out.println ("# Masukkan tidak valid!                                                                       #");
        System.out.println ("# Ketik '1', '2', '3', '4', '5' pada keyboard:                                                #");
    }

    public static void TulisMenuDeterminan () {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# METODE PERHITUNGAN DETERMINAN                                                               #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu metode di bawah ini!                                               #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Metode Reduksi Baris                                                                     #");
        System.out.println ("# 2. Metode Ekspansi Kofaktor                                                                 #");
        System.out.println ("# 3. Kembali ke MENU UTAMA                                                                    #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1', '2', atau '3' pada keyboard:                                                     #");
    }

    public static void MasukanMetodeDeterminanSalah(){
        System.out.println ("# Masukkan tidak valid!                                                                       #");
        System.out.println ("# Ketik '1', '2', '3', '4', '5' pada keyboard:                                                #");
    }

    public static void TulisMenuInvers() {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# METODE PERHITUNGAN MATRIKS BALIKAN                                                          #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu metode di bawah ini!                                               #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Metode Reduksi Baris                                                                     #");
        System.out.println ("# 2. Metode Ekspansi Kofaktor                                                                 #");
        System.out.println ("# 3. Kembali ke MENU UTAMA                                                                    #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1', '2', atau '3' pada keyboard:                                                     #");
    }

    public static void MasukanMetodeInversSalah(){
        System.out.println ("# Masukkan tidak valid!                                                                       #");
        System.out.println ("# Ketik '1', '2', '3', '4', '5' pada keyboard:                                                #");
    }

    public static void TulisSolusiDet(double det) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("#=============================================================================================#");
        System.out.println ("# PENULISAN SOLUSI DETERMINAN                                                                 #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

        int pilihan = scan.nextInt();
        while (pilihan<1 || pilihan>2){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.nextInt();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        if (pilihan==1) {
            System.out.println("Determinan matriks A adalah "+ df.format(det));
        } else {
            // Simpan dalam file
            Scanner sc = new Scanner(System.in);
            System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
            System.out.println("Contoh: solutions/SolusiDet.txt");
            String namafile = "../"+sc.nextLine();

            try (FileOutputStream file = new FileOutputStream(namafile)) {
                byte[] b;
                String s = ("Determinan matriks A adalah "+ df.format(det)+"\n");
                System.out.print(s);
                b = s.getBytes();
                file.write(b);
                System.out.println("Hasil perhitungan determinan berhasil disimpan kedalam file");
            }
        }

    }



    public static void main(String [] args) throws IOException {
        //START
        TulisMenuWelcome();
        Scanner scan = new Scanner(System.in);
        char status = scan.next().charAt(0);
        while(status == 'Y'){
            // Selama status = Y maka program akan berjalan
            TulisMenuUtama();
            // Menerima masukkan user untuk pilihan operasi yang ingin dilakukan
            scan = new Scanner(System.in);
            int operasi = scan.nextInt();
            while(operasi<1 || operasi>6){
                MasukanMenuUtamaSalah();
                operasi = scan.nextInt();
            }
            if(operasi==1){
                // SPL
                TulisMenuPenyelesaian();
                // Menerima masukkan user untuk pilihan metode untuk menyelesaikan SPL
                int metode = scan.nextInt();
                while(metode<1 || metode>5){
                    MasukanMetodeSalah();
                    metode = scan.nextInt();
                }
                if(metode == 1){
                    //Elminisasi Gauss
                    MatriksInit Mtemp = new MatriksInit(1); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    A.SPLGauss();
                } else if (metode == 2){
                    //Elminisasi Gauss-Jordan
                    MatriksInit Mtemp = new MatriksInit(1); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    A.SPLGaussJordan();
                } else if (metode == 3){
                    //Matriks Balikan
                    MatriksInit Mtemp = new MatriksInit(1); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    InversMatriks InversA = new InversMatriks(A);
                    InversA.SPLInvers();

                } else if(metode==4) {
                    //Kaidah Cramer
                    MatriksInit Mtemp = new MatriksInit(1); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    double[] determinan = new double[A.NBrsEff]; 
                    determinan = A.SPLCramer(false);
                }
                // else, metode==5 membuat program kembali ke menu utama
            } else if(operasi==2){
                // Determinan
                TulisMenuDeterminan();
                // Menerima masukkan user untuk pilihan metode untuk mengitung determinan sebuah matriks
                int metode = scan.nextInt();
                while(metode<1 || metode>3){
                    MasukanMetodeDeterminanSalah();
                    metode = scan.nextInt();
                }
                if(metode == 1){
                    // Metode Reduksi Baris
                    MatriksInit Mtemp = new MatriksInit(2); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    double determinan = A.DeterminanOBE();
                    TulisSolusiDet(determinan);
                } else if (metode == 2){
                    // Metode Ekspansi Kofaktor 
                    MatriksInit Mtemp = new MatriksInit(2); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    double determinan = A.DeterminanDenganKofaktor();
                    TulisSolusiDet(determinan);
                } // else, metode==3 membuat program kembali ke menu utama

            } else if(operasi==3){
                //Matriks Balikan
                TulisMenuInvers();
                // Menerima masukkan user untuk pilihan metode untuk membuat matriks balikan dari sebuah matriks
                int metode = scan.nextInt();
                while(metode<1 || metode>3){
                    MasukanMetodeInversSalah();
                    metode = scan.nextInt();
                }
                if(metode == 1){
                    // Metode Reduksi Baris
                    MatriksInit Mtemp = new MatriksInit(3); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    InversMatriks InversA = new InversMatriks(A);
                    if (InversA.IsInversible()){
                        InversA.OBEMatriksInvers();
                        InversA.hasilInversOBE();
                        InversA.TulisMatriksInvers();
                    } else {
                        System.out.println("Matriks tidak memiliki balikan.");
                    }
                } else if (metode == 2){
                    // Metode Ekspansi Kofaktor 
                    MatriksInit Mtemp = new MatriksInit(3); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    InversMatriks InversA = new InversMatriks(A);
                    if (!InversA.IsInversible()){
                        System.out.println("Matriks tidak memiliki balikan.");
                    } else {
                        InversA.hasilInversKofaktor();
                        InversA.TulisMatriksInvers();
                    }

                }
            } else if (operasi == 4) {
                // Interpolasi Polinom
                MatriksInit Mtemp = new MatriksInit(4);
                Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);

                Mtemp.toMatriks(A);
                A.MakeMatriksInterpolasi(A.NBrsEff);
                A.SolusiInterpolasi();

            } else if(operasi==5){
                //Regresi Linear Berganda
                MatriksInit Mtemp = new MatriksInit(5); 
        	    Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
                Mtemp.toMatriks(A);
                A.MultipleLinearRegression();
            }else{
                // Keluar Program
                status = 'N';
            }
        }        
    }
}

