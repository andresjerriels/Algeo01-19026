import java.util.Scanner;

public class TubesAlgeo {


    public static void TulisMenuWelcome() {
        System.out.println ("#=============================================================================================#");
        System.out.println ("# SELAMAT DATANG!                                                                             #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Andres Jerriel Sinabutar         - 13519218                                                 #");
        System.out.println ("# Gde Anantha Priharsena           - 13519026                                                 #");
        System.out.println ("# Shifa Salsabiila                 - 13519106                                                 #");
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
        System.out.println ("# Ketik '1', '2', '3', '4', atau '5' pada keyboard:                                                     #");
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


    public static void main(String [] args) {
        
        TulisMenuWelcome();
        Scanner scan = new Scanner(System.in);
        char status = scan.next().charAt(0);
        while(status == 'Y'){
            TulisMenuUtama();
            int operasi = scan.nextInt();
            while(operasi<1 || operasi>6){
                MasukanMenuUtamaSalah();
                operasi = scan.nextInt();
            }
            if(operasi==1){
                // SPL
                TulisMenuPenyelesaian();
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
                }  // else, metode==5 membuat program kembali ke menu utama
            } else if(operasi==2){
                // Determinan
                TulisMenuDeterminan();
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
                    System.out.println("Determinan matriks A adalah "+ determinan);
                } else if (metode == 2){
                    // Metode Ekspansi Kofaktor 
                    MatriksInit Mtemp = new MatriksInit(2); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    double determinan = A.DeterminanDenganKofaktor();
                    System.out.println("Determinan matriks A adalah "+ determinan);
                } // else, metode==3 membuat program kembali ke menu utama

            } else if(operasi==3){
                //Matriks Balikan
                TulisMenuInvers();
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
                    InversA.OBEMatriksInvers();
                    if (InversA.IsInversible()){
                        InversA.hasilInversOBE();
                        InversA.matriks.TulisMatriks();
                        // Kalo nyimpen ke file InversA.matriks.TulisMatriksKeFile()
                    } else {
                        System.out.println("Matriks tidak memiliki balikan.");
                    }
                } else if (metode == 2){
                    // Metode Ekspansi Kofaktor 
                    MatriksInit Mtemp = new MatriksInit(3); 
        	        Matriks A = new Matriks(Mtemp.NBrsEff, Mtemp.NKolEff);
    	
                    Mtemp.toMatriks(A);
                    InversMatriks InversA = new InversMatriks(A);
                    if (InversA.matriks.DeterminanDenganKofaktor() == 0){
                        System.out.println("Matriks tidak memiliki balikan.");
                    } else {
                        InversA.hasilInversKofaktor();
                        InversA.matriks.TulisMatriks();
                        // Kalo nyimpen ke file InversA.matriks.TulisMatriksKeFile()

                    }

                }
            }else if(operasi==5){
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

