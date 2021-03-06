import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Matriks {
	Scanner scan;
	
	//ATTRIBUTES
    public double[][] Elmt = new double[100][100];
    public int NBrsEff;
    public int NKolEff;
    public int IdxBrsMin = 0;
    public int IdxBrsMax = 99;
    public int IdxKolMin = 0;
    public int IdxKolMax = 99;

    //CONSTRUCTOR
    Matriks(int BrsEff, int KolEff) {
        MakeEmpty(BrsEff, KolEff);
    }
    
    public void MakeEmpty(int NB, int NK) {
        this.NBrsEff = NB;
        this.NKolEff = NK;

        for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs();i++) {
            for (int j = this.IdxKolMin; j <= this.getLastIdxKol(); j++) {
                this.Elmt[i][j] = 0.0;
            }
        }
    }

    //=====================PRIMITIF=========================
    //GetLastIdxBrs
    public int getLastIdxBrs() {
        return this.NBrsEff - 1;
    }

    //GetLastIdxKol
    public int getLastIdxKol() {
        return this.NKolEff - 1;
    }

    //Baca Matriks
    public void BacaMatriks() {
    	scan = new Scanner(System.in);
    	for (int i = 0; i < this.NBrsEff; i++) {
			for (int j = 0; j < this.NKolEff; j++) {
				this.Elmt[i][j] = scan.nextDouble();
			}
		}
    }

    //Tulis Matriks
    public void TulisMatriks() {
        for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs(); i++) {
            for (int j = this.IdxKolMin; j <= this.getLastIdxKol(); j++) {
                DecimalFormat df = new DecimalFormat("#.#####");

                if ((i != getLastIdxBrs()) && (j == getLastIdxKol())) {
                    System.out.println(df.format(this.Elmt[i][getLastIdxKol()]));
                } else if ((i == getLastIdxBrs()) && (j == getLastIdxKol())) {
                    System.out.println(df.format(this.Elmt[getLastIdxBrs()][getLastIdxKol()]));
                } else {
                    System.out.print(df.format(this.Elmt[i][j]) + " ");
                }
            }
        }
    }

	public void TulisMatriksKeFile() throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
		System.out.println("Contoh: solutions/SolusiSPL.txt");
		String namafile = "../"+scan.nextLine();

		try(FileOutputStream file = new FileOutputStream(namafile)) {
			byte[] b;
			for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs(); i++) {
				for (int j = this.IdxKolMin; j <= this.getLastIdxKol(); j++) {
					DecimalFormat df = new DecimalFormat("#.##");

					if ((i != this.getLastIdxBrs()) && (j == this.getLastIdxKol())) {
						String s = df.format(this.Elmt[i][this.getLastIdxKol()]) + "\n";
						System.out.print(s);
						b = s.getBytes();
						file.write(b);
					} else if ((i == this.getLastIdxBrs()) && (j == this.getLastIdxKol())) {
						String sa = df.format(this.Elmt[this.getLastIdxBrs()][this.getLastIdxKol()]) + "\n";
						System.out.print(sa);
						b = sa.getBytes();
						file.write(b);
					} else {
						String sc = df.format(this.Elmt[i][j]) + " ";
						System.out.print(sc);
						b = sc.getBytes();
						file.write(b);
					}
				}
			}
		}
	}


	//=====================BASIC OPERATIONS=======================
    // IsBrsAllZero
    public boolean IsBrsAllZero(int Idx) {
        int j = this.IdxKolMin;
        while (j <= this.getLastIdxKol()){
            if (this.Elmt[Idx][j] != 0){
                return false;
            }
            j++;
        }
        return true;
    }
    
    //IsKolAllZero
    public boolean IsKolAllZero(int Idx) {
        int i = this.IdxBrsMin;
        while (i <= this.getLastIdxBrs()){
            if (this.Elmt[i][Idx] != 0){
                return false;
            }
            i++;
        }
        return true;
    }

    //KolAllZeroCr
    public boolean IsKolZeroFromCr(int cr, int cc) {
		boolean allZero = true;
		int i = cr;
		while(allZero && i<this.NBrsEff) {
			if (this.Elmt[i][cc]!=0) {
				allZero = false;
			}
			i++;
		}
		return allZero;
		
	}
    
    
    //GetElmtDiagonal
    double GetElmtDiagonal(int i) {
		return this.Elmt[i][i];
	}
    
    //TukarBrs
    public void TukarBrs(int IdxBrs1, int IdxBrs2){
        double tmp;
        for (int j = this.IdxKolMin; j <= this.getLastIdxKol();j++){
            tmp = this.Elmt[IdxBrs1][j];
            this.Elmt[IdxBrs1][j] = this.Elmt[IdxBrs2][j];
            this.Elmt[IdxBrs2][j] = tmp;
        }
    }

	//TukarKolCramer
	public void TukarKolCramer(int IdxKol1, int IdxKol2, Matriks mx){
		//procedure ini digunakan untuk menukar kolom dengan index IdxKol1 dengan kolom dengan IdxKol2
		double tmp;
		for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs();i++){
			//Menukar elmt[i][IdxKol1] dengan elmt[i][IdxKol2]
            tmp = mx.Elmt[i][IdxKol1];
            mx.Elmt[i][IdxKol1] = this.Elmt[i][IdxKol2];
            this.Elmt[i][IdxKol2] = tmp;
        }
	}
	
  //EliminasiGauss
    public void EliminasiGauss() {
		int cr = 0;
		int cc = 0;
		while (cr < this.NBrsEff && cc < this.NKolEff) {
			boolean colZeroFound = IsKolZeroFromCr(cr,cc);
			boolean rowZeroFound = false;
			
			if (colZeroFound) {
				cc++;
			} else {
				int z = cr;
				while (!rowZeroFound && z<this.NBrsEff) {
					rowZeroFound = IsBrsAllZero(z);
					if (!rowZeroFound) {
						z++;
					}
				}
				if (rowZeroFound) {
					TukarBrs(z,this.NBrsEff-1);
					
				}  
				//cari baris dengan this.Elmt[i][k] paling besar
				int currentP = cr;
				for (int i = cr + 1;i < this.NBrsEff; i++) {
					if (Math.abs(this.Elmt[i][cr]) > Math.abs(this.Elmt[currentP][cr])) {
						currentP = i;
					}
				}
				
				//tukar baris
				TukarBrs(cr, currentP);
				
				//bagi baris ke k agar elemen A[k][k] = 1;
				double divider = this.Elmt[cr][cc];
				for(int i = cc; i < this.NKolEff; i++) {
					this.Elmt[cr][i] /= divider;
				}
				cleanMatriks(this, 1e-9);
				
				//0in nilai di bawah 1 utama currentP
				for (int i = cr + 1; i < this.NBrsEff; i++) {
					double x = -(this.Elmt[i][cc]/this.Elmt[cr][cc]);
					for (int j = cc; j < this.NKolEff; j++) {
						this.Elmt[i][j] += this.Elmt[cr][j] * x;
					}
				}
				cleanMatriks(this, 1e-9);
				
				cc++;
				cr++;
			}	
		}
	}
    
    //EliminasiGaussJordan
    public void EliminasiGaussJordan() {
		
    	EliminasiGauss();
		for (int i=this.NBrsEff-1;i>0;i--) {
			boolean jFound = false;
			int j = 0;
			while(!jFound && j<=this.getLastIdxKol()) {
				if (this.Elmt[i][j]==1) {
					//0in nilai di atas 1 utama
					for (int a = i-1; a>=0; a--) {
						double x = -(this.Elmt[a][j]/this.Elmt[i][j]);
						for (int b = 0; b < this.NKolEff; b++) {
							this.Elmt[a][b] += this.Elmt[i][b] * x;
						}
					}
					jFound = true;
				}
				j++;
			}
		}
		
    }

    // Untuk Perkalian 2 Matriks
    public Matriks KalidenganMatriks(Matriks M){
        Matriks Mnew = new Matriks(this.NBrsEff, M.NKolEff);

        for (int i = 0; i <= Mnew.getLastIdxBrs(); i++){
            for (int j = 0; j <= Mnew.getLastIdxKol(); j++){
                double tmp = 0;
                for (int x = 0; x <= this.getLastIdxKol(); x++){
                    tmp += (this.Elmt[i][x] * M.Elmt[x][j]);
                }
                Mnew.Elmt[i][j] = tmp;
            }
        }
        return Mnew;
    }


	// UNTUK INTERPOLASI
	void MakeMatriksInterpolasi(int NBrs) {
    	/* I.S. Matriks NBrs x 2 terdefinisi dari masukan pengguna
    	   F.S. Mengubah Matriks menjadi matriks yang siap diinterpolasi */

		this.NBrsEff = NBrs;
		this.NKolEff = NBrs + 1;

		for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs(); i++){
			double x = this.Elmt[i][this.IdxBrsMin];
			double y = this.Elmt[i][1];
			for (int j=this.IdxKolMin;j<=this.getLastIdxKol();j++){
				if (j != getLastIdxKol()){
					this.Elmt[i][j] = Math.pow(x, j);
				} else {
					this.Elmt[i][j] = y;
				}
			}
		}
	}

	public void SolusiInterpolasi() throws IOException {
    	/* I.S. Matriks interpolasi terdefinisi
    	   F.S. Menghitung interpolasi polinomial dan solusi yang dihasilkan akan ditampilkan ke layar atau disimpan
    	   		ke dalam file .txt. Jika matriks tidak memiliki solusi interpolasi polinomial, akan ditampilkan
    	   		“Polinom Interpolasi tidak dapat dihitung”. */

		// Inisialisasi berbagai variabel yang dibutuhkan
		Scanner scan = new Scanner(System.in);
		int brsNotZero = 0;
		int cr = this.getLastIdxBrs();
		boolean hasSolution = true;

		// Eliminasi Gauss-Jordan untuk mendapat matriks eselon baris tereduksi
		this.EliminasiGaussJordan();
		while (hasSolution && cr >= 0) {
			int cc = 0;
			boolean rowAllZero = true;

			for (int j = 0; j <= this.getLastIdxKol() - 1; j++) {
				if (this.Elmt[cr][j] != 0) {
					rowAllZero = false;
				}
			}

			if (rowAllZero) {
				if (this.Elmt[cr][this.getLastIdxKol()] != 0) {
					hasSolution = false;
				}
			} else {
				brsNotZero++;
			}

			cr--;
		}

		// Penulisan solusi ke layar atau disimpan ke file
		System.out.println ("#=============================================================================================#");
		System.out.println ("# PENULISAN SOLUSI INTERPOLASI                                                                #");
		System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

		String pilihan = scan.nextLine();
		while (!pilihan.equals("1") && !pilihan.equals("2")) {
			System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
			pilihan = scan.nextLine();
		}

		if (pilihan.equals("1")){
			this.TulisInterpolasi(hasSolution, brsNotZero);
		} else {
			this.TulisInterpolasiKeFile(hasSolution, brsNotZero);
		}
	}

	public void TulisInterpolasi(boolean hasSolution, int brsNotZero) {
    	/* I.S. Matriks terdefinisi dalam bentuk matriks eselon baris tereduksi
    	   F.S. Menampilkan hasil interpolasi ke layar */

		// Format penulisan desimal
		DecimalFormat df = new DecimalFormat("#.######");

		// KASUS 1: SPL tidak memiliki solusi
		if (!hasSolution) {
			System.out.println("Polinom Interpolasi tidak dapat dihitung");
		} else {
			// KASUS 2: SPL memiliki solusi banyak
			if (brsNotZero < this.NKolEff - 1) {
				System.out.println("Polinom Interpolasi tidak dapat dihitung");
			}
			//KASUS 3: Solusi unik
			else {
				System.out.println("Hasil polinom interpolasinya adalah");
				System.out.print("P(x) = ");

				// Perulangan untuk mencetak hasil interpolasi
				for (int i = 0; i <= this.getLastIdxBrs(); i++) {
					if (i == 0) {
						if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
							System.out.print(df.format(this.Elmt[i][this.getLastIdxKol()]));
						} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
							System.out.print(df.format(this.Elmt[i][this.getLastIdxKol()]));
						}
					} else if (i == 1) {
						if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
							if (this.Elmt[i][this.getLastIdxKol()] == 1) {
								System.out.print(" + " + "x");
							} else {
								System.out.print(" + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x");
							}
						} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
							if (this.Elmt[i][this.getLastIdxKol()] == -1) {
								System.out.print(" - " + "x");
							} else {
								System.out.print(" - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x");
							}
						}
					} else {
						if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
							if (this.Elmt[i][this.getLastIdxKol()] == 1) {
								System.out.print(" + " + "x^" + i);
							} else {
								System.out.print(" + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x^" + i);
							}
						} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
							if (this.Elmt[i][this.getLastIdxKol()] == -1) {
								System.out.print(" - " + "x^" + i);
							} else {
								System.out.print(" - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x^" + i);
							}
						}
					}
				}
				System.out.println();
			}
		}

		// Menghitung taksiran
		Scanner scan = new Scanner(System.in);
		double x = 0;
		do{
			System.out.println("Masukkan nilai x yang ingin ditaksir (Jika ingin kembali ke menu utama, masukkan -999):");
			x = scan.nextDouble();
			if (x != -999) {
				double taksiran = this.Taksiran(x);
				System.out.println("P(" + x + ") ≈ " + taksiran);
			}
		} while (x != -999);
	}

	public void TulisInterpolasiKeFile(boolean hasSolution, int brsNotZero) throws IOException {
    	/* I.S. Matriks terdefinisi dalam bentuk matriks eselon baris tereduksi
    	   F.S. Menyimpan hasil interpolasi ke dalam file dan juga menampilkannya di layar */

		Scanner scan = new Scanner(System.in);

		// Menerima masukan nama file dari pengguna
		System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
		System.out.println("Contoh: solutions/SolusiSPL.txt");
		String namafile = "../"+scan.nextLine();

		// Format penulisan desimal
		DecimalFormat df = new DecimalFormat("#.######");

		// Penulisan solusi ke dalam file
		try (FileOutputStream file = new FileOutputStream(namafile)) {
			byte[] b;
			//KASUS 1: HAS NO SOLUTION
			if (!hasSolution) {
				String sa = "Polinom Interpolasi tidak dapat dihitung\n";
				System.out.print(sa);
				b = sa.getBytes();
				file.write(b);
			} else {
				//KASUS 2: INFINITELY MANY SOLUTIONS
				if (brsNotZero < this.NKolEff - 1) {
					String sb = "Polinom Interpolasi tidak dapat dihitung\n";
					System.out.print(sb);
					b = sb.getBytes();
					file.write(b);
				}
				//KASUS 3: UNIQUE SOLUTION
				else {
					String s1 = "Hasil polinom interpolasinya adalah\n";
					System.out.print(s1);
					b = s1.getBytes();
					file.write(b);
					String s2 = "P(x) = ";
					System.out.print(s2);
					b = s2.getBytes();
					file.write(b);

					for (int i = 0; i <= this.getLastIdxBrs(); i++) {
						if (i == 0) {
							if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
								String sa1 = df.format(this.Elmt[i][this.getLastIdxKol()]);
								System.out.print(sa1);
								b = sa1.getBytes();
								file.write(b);
							} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
								String sa2 = df.format(this.Elmt[i][this.getLastIdxKol()]);
								System.out.print(sa2);
								b = sa2.getBytes();
								file.write(b);
							}
						} else if (i == 1) {
							if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
								String sb1;
								if (this.Elmt[i][this.getLastIdxKol()] == 1) {
									sb1 = " + " + "x";
								} else {
									sb1 = " + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x";
								}
								System.out.print(sb1);
								b = sb1.getBytes();
								file.write(b);
							} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
								String sb2;
								if (this.Elmt[i][this.getLastIdxKol()] == -1) {
									sb2 = " - " + "x";
								} else {
									sb2 = " - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x";
								}
								System.out.print(sb2);
								b = sb2.getBytes();
								file.write(b);
							}
						} else {
							if (this.Elmt[i][this.getLastIdxKol()] > 1e-9) {
								String sc1;
								if (this.Elmt[i][this.getLastIdxKol()] == 1) {
									sc1 = " + " + "x^" + i;
								} else {
									sc1 = " + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x^" + i;
								}
								System.out.print(sc1);
								b = sc1.getBytes();
								file.write(b);
							} else if (this.Elmt[i][this.getLastIdxKol()] < -1e-9) {
								String sc2;
								if (this.Elmt[i][this.getLastIdxKol()] == -1) {
									sc2 = " - " + "x^" + i;
								} else {
									sc2 = " - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x^" + i;
								}
								System.out.print(sc2);
								b = sc2.getBytes();
								file.write(b);
							}
						}
					}
					System.out.println();
				}
			}

			// Menghitung taksiran dan menyimpan hasilnya ke dalam file
			double x = 0;
			do {
				System.out.println("Masukkan nilai x yang ingin ditaksir (Jika ingin kembali ke menu utama, masukkan -999):");
				x = scan.nextDouble();
				if (x != -999) {
					double taksiran = this.Taksiran(x);
					String sd = "\nP(" + x + ") ≈ " + taksiran;
					System.out.println("P(" + x + ") ≈ " + taksiran);
					b = sd.getBytes();
					file.write(b);
				}
			} while (x != -999);
		}
	}

	public double Taksiran(double x){
    	/* I.S. Hasil interpolasi polinom terdefinisi dalam sebuah matriks
    	   F.S. Mengembalikan hasil substitusi nilai x ke dalam polinom */

		double result = 0;
		for (int i = 0; i <= this.getLastIdxBrs(); i++) {
			result += Math.pow(x, i) * this.Elmt[i][this.getLastIdxKol()];
		}
		return result;
	}

	//CleanMatrix
    void cleanMatriks(Matriks M, double tolerance) {

        for (int i = 0; i <= M.getLastIdxBrs(); i++){
            for(int j = 0; j <= M.getLastIdxKol(); j++){
                if (Math.abs(M.Elmt[i][j]) < tolerance){
                    M.Elmt[i][j] = 0;
                }
            }
        }
	}
    
    //TulisSolusiSPL
    public String TulisSPLGauss() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println ("#=============================================================================================#");
        System.out.println ("# PENULISAN SOLUSI SPL                                                                        #");
		System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");


        String pilihan = scan.next();
        while (!pilihan.equals("1") && !pilihan.equals("2")){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.next();
        }
        return pilihan;
        

    }
	
	//Untuk Multiple Linear Regression
	//Jumlah Perkalian 2 Kolom
	public double SumMul2BrsMLR(int IdxKol1, int IdxKol2){
		// fungsi ini digunakan untuk menghasilkan jumlah dari perkalian setiap elemen pada kolom ke IdxKol1 dengan kolom ke IdxKol2
		// Untuk merealisasikan Normal Estimation Equation for Multiple Linear Regression
		double sum = 0;
		int n = this.NBrsEff;
		int k = this.NKolEff;
		if(IdxKol1==0 && IdxKol2==0){
			// untuk Elmt[0][0]
			sum = n;
		} else if(IdxKol2 == k){
			if(IdxKol1==0){
				// Kasus untuk Elmt[0][k] pada SPL
				for(int i=0; i<n ;i++){
					sum += this.Elmt[i][0];
				}
			} else{
				// Kasus Untuk kolom ke K selain Elmt[0][k] 
				for(int i=0; i<n ;i++){
					sum += this.Elmt[i][IdxKol1]*this.Elmt[i][0];
				}
			}
		} else if(IdxKol1==0 || IdxKol2==0){
			if(IdxKol1==0){
				// Kasus Untuk Kolom pertama 
				for(int i=0; i<n; i++){
					sum += this.Elmt[i][IdxKol2];
				}
			} else{
				// Kasus untuk baris pertama selain selain Elmt[0][0] dan Elmt[0][k]
				for(int i=0; i<n; i++){
					sum+= this.Elmt[i][IdxKol1];
				}
			}
		} else{
			//Kasus Elmt ditengah
			for(int i=0; i<n; i++){
				sum+= this.Elmt[i][IdxKol1] * this.Elmt[i][IdxKol2];
			}
		}

		return sum;
	}
    
    //ItemsInRow
    public int ItemsInRow(int n, int m) {
    	int elmtCounter = 0;
    	for (int i=0;i<=m;i++) {
    		if (this.Elmt[n][i]!=0) {
    			elmtCounter++;
    		}
    	}
    	return elmtCounter;
    }
    
    //Kolom1Utama
    public int Kolom1Utama(int n) {
    	int j = 0;
    	int idxOne = -1;
    	
    	while(idxOne == -1) {
    		if (this.Elmt[n][j]!=0) {
    			idxOne = j;
    		}
    		j++;
    	}
    	return idxOne;
    }
    
    
    
    
    //=====================MAIN OPERATIONS=======================
    
    //SPL Gauss
    public void SPLGauss() throws IOException {
    	DecimalFormat df = new DecimalFormat("#.##");
    	int brsNotZero = 0;
    	int cr = this.getLastIdxBrs();
    	boolean hasSolution = true; //Variabel penanda apakah solusi SPL ada
    	
    	EliminasiGauss(); //Mengubah matriks ke bentuk matriks eselon
    	while (hasSolution && cr>=0) {
    		int cc = 0;
    		boolean rowAllZero = true;
    	    
    		//Proses pengecekan apakah elemen sebuah baris pada matriks A bernilai nol semua
    		for (int j=0; j<=this.getLastIdxKol()-1;j++) {
    			if (this.Elmt[cr][j]!=0) {
    				rowAllZero = false;
    			}
    		}
    		
    		//Mengecek elemen pada matriks b yang bersangkutan apabila ditemukan sebuah baris nol
    		if (rowAllZero) {
    			if (this.Elmt[cr][this.getLastIdxKol()]!=0) {
    				hasSolution = false; //Deklarasi bahwa SPL tidak memilki solusi
    			} 
    		} else {
				brsNotZero++;
			}
    		
    		cr--;
    	}
    	
    	//Kasus 1: Tidak mempunyai solusi
    	if (!hasSolution) {
    		String printMode = this.TulisSPLGauss();   
    		
    		if (printMode.equals("2")) {
				Scanner scan = new Scanner(System.in);
		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
		        System.out.println("Contoh: solutions/SolusiSPL.txt");
				String namafile = "../"+scan.nextLine();
				
				
				//Tulis hasil ke file
				try (FileOutputStream file = new FileOutputStream(namafile)) {
		            byte[] b;
		            String s =("SPL ini tidak memiliki solusi.\n");
		            b = s.getBytes();
	                file.write(b);
		        }
			}
    		
    		//Tulis hasil ke layar
    		System.out.println("SPL ini tidak memiliki solusi.");
    		
    	} else {
    		//Kasus 2: Solusi banyak
    		if (brsNotZero<this.NKolEff-1) {
    			//Deklarasi array tambahan, dengan spesifikasi:
    			//Array 2D dengan ukuran n x m (n: Jumlah variabel, jumlah persamaan efektif setelah eliminasi + 4)
    			//4 kolom di akhir array solusi dibuat untuk menyimpan data dengan ketentuan:
    				//Kolom 1: penanda apakah sebuah variabel dijadikan variabel parametrik atau tidak (1 jika ya, 0 jika tidak)
    				//Kolom 2: penanda apakah sebuah variabel memiliki solusi tunggal (1 jika ya, 0 jika tidak)
    				//Kolom 3: index variabel parametrik (variabel parametrik ke berapa), jika kolom 1 bernilai 1
    				//Kolom 4: nilai solusi tunggal suatu variabel apabila kolom 2 bernilai 1
    			double[][] solution = new double[this.NKolEff-1][this.NKolEff-brsNotZero+3];
    			//Array 1D berukuran jumlah variabel matriks, menjadi penanda apakah solusinya sudah dideklarasikan atau belum
    			boolean[] solutionDeclared = new boolean[this.NKolEff-1];
    			for (int i=0;i<=this.getLastIdxKol()-1;i++) {
    				solutionDeclared[i] = false; //Inisiasi seluruh nilai solution declared
    			}
    			    			
    			//Menentukan variabel mana saja yang akan dijadikan variabel parametrik
    			int cpar = 0; //Variabel yang menyimpan jumlah variabel yang harus dijadikan variabel parametrik
    			int cr2 = brsNotZero-1;
    			int cc2 = this.getLastIdxKol()-1;
    			
    			//Mecari kolom nol untuk kemudian variabel terkait langsung dijadikan variabel parametrik
    			for (int j=0;j<this.getLastIdxKol()-1;j++) {
    				if (this.IsKolAllZero(j)) {
    					solutionDeclared[j] = true;
    					solution[j][this.NKolEff-brsNotZero-1] = 1;
						solution[j][this.NKolEff-brsNotZero+1] = cpar;
						cpar++;
    				}
    			}
    			
    			//Menentukan variabel lain yang akan dijadikan variabel parametrik
    			while(cpar < this.NKolEff-1-brsNotZero) {
    				cc2 = this.getLastIdxKol()-1;
    				while(cc2>=0 && cpar < this.NKolEff-1-brsNotZero) {
    					if (solutionDeclared[cc2]==false && this.Elmt[cr2][cc2]!=0) {
    						//variabel tidak akan dijadikan variabel parametrik apabila ia memilki solusi unik atau bisa
    						//dideklarasikan dengan variabel parametrik lainnya
        					if(ItemsInRow(cr2,this.getLastIdxKol()-1)==1){
        						
        						solutionDeclared[cc2]=true;
            					solution[cc2][this.NKolEff-brsNotZero] = 1;
            					solution[cc2][this.NKolEff-brsNotZero+2] = this.Elmt[cr2][this.getLastIdxKol()]/this.Elmt[cr2][cc2];
            					
            				}
        					//sebuah variabel dideklarasikan sebagai variabel parametrik
        					else if(cc2!=Kolom1Utama(cr2) && IsKolZeroFromCr(cr2+1,cc2)) {
        						solutionDeclared[cc2]=true;
        						solution[cc2][this.NKolEff-brsNotZero-1] = 1;
        						solution[cc2][this.NKolEff-brsNotZero+1] = cpar;
        						cpar++;
        					}
        					
        				}
    					cc2--;
    				}
    				
    				cr2--;
    				
    			}
    			    			
    			//Mengisi array solusi sesuai dengan jenis solusi setiap variabelnya
    			cr2 = brsNotZero-1;
    			cc2 = 0;
    			for (int i=cr2;i>=0;i--) {
    				cc2 = Kolom1Utama(i);
    				if(solutionDeclared[cc2]==false) {
    					solutionDeclared[cc2]=true;
    					solution[cc2][this.NKolEff-brsNotZero+2] = this.Elmt[i][this.getLastIdxKol()];
    					for (int k=this.getLastIdxKol()-1;k>=cc2+1;k--) {
    						//Jenis solusi 1: bukan merupakan variabel parametrik ataupun solusi tunggal
    						if(solution[k][this.NKolEff-brsNotZero-1]==0 && solution[k][this.NKolEff-brsNotZero]==0) {
    							for (int j=0;j<=cpar-1;j++) {
    								solution[cc2][j] += solution[k][j]*-(this.Elmt[i][k]);
    							}
    							solution[cc2][this.NKolEff-brsNotZero+2] += solution[k][this.NKolEff-brsNotZero+2]*-(this.Elmt[i][k]);
        					}
    						//Jenis solusi 2: Merupakan variabel parametrik
    						else if (solution[k][this.NKolEff-brsNotZero-1]==1 && solution[k][this.NKolEff-brsNotZero]==0) {
    							solution[cc2][(int)solution[k][this.NKolEff-brsNotZero+1]] += this.Elmt[i][k];
    						}
    						//Jenis solusi 3: Memiliki solusi tunggal
    						else {
    							solution[cc2][this.NKolEff-brsNotZero+2] -= solution[k][this.NKolEff-brsNotZero+2]*this.Elmt[i][k];	
    							
    						}
    						
						}
    					
    				}
    				
    			}
    			
    			//Menuliskan hasil
    			String[] variables = {"r","s","t","u","v","w","x","y","z"}; 
    			String printMode = this.TulisSPLGauss();   
    			
    			//Tulis hasil ke file
    			if (printMode.equals("2")) {
    				Scanner scan = new Scanner(System.in);
    		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
    		        System.out.println("Contoh: solutions/SolusiSPL.txt");
					String namafile = "../"+scan.nextLine();
    				
    		        try (FileOutputStream file = new FileOutputStream(namafile)) {
    		            byte[] b;
    		            String s =("SPL ini memiliki solusi banyak, yang mengikuti:\n");
    		            b = s.getBytes();
		                file.write(b);
		                for (int i = this.IdxBrsMin; i <= this.getLastIdxKol()-1; i++) {
    		            	if(solution[i][this.NKolEff-brsNotZero-1]==0 && solution[i][this.NKolEff-brsNotZero]==0) {
    	    					s = ("x"+(i+1)+" = ");
    	    					b = s.getBytes();
    			                file.write(b);
    	    					if (solution[i][this.NKolEff-brsNotZero+2]!=0) {
    	    						s = (df.format(solution[i][this.NKolEff-brsNotZero+2]));
    	    						b = s.getBytes();
    	    		                file.write(b);
    	    					}
    							for (int j=0;j<=cpar-1;j++) {
    								int cOutput = 0;
    								if (solution[i][j]!=0) {
    									if (-(solution[i][j])>0 && (cOutput!=0 || solution[i][this.NKolEff-brsNotZero+2]!=0)){
    										s = ("+");
    										b = s.getBytes();
    						                file.write(b);
    									}
    									if (-(solution[i][j])==-1){
    										s = ("-");
    										b = s.getBytes();
    						                file.write(b);
    									}
    									if (Math.abs(solution[i][j])!=1) {
    										s = (df.format(-(solution[i][j])));
    										b = s.getBytes();
    						                file.write(b);
    									}
    									s = (variables[j]);
    									b = s.getBytes();
    					                file.write(b);
    									cOutput++;
    								}
    							}
    							s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    						} else if (solution[i][this.NKolEff-brsNotZero-1]==1 && solution[i][this.NKolEff-brsNotZero]==0) {
    							s = ("x"+(i+1)+" = "+variables[(int)solution[i][this.NKolEff-brsNotZero+1]]);
    							b = s.getBytes();
    			                file.write(b);
    			                s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    						} else {
    							s = ("x"+(i+1)+" = "+df.format(solution[i][this.NKolEff-brsNotZero+2]));
    							b = s.getBytes();
    			                file.write(b);
    			                s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    							
    						}
    		            }
    		        }
    		        
    			}
    			
    			//Tulis hasil ke layar
    			System.out.println("SPL ini memiliki solusi banyak, yang mengikuti:");
    			for (int i = this.IdxBrsMin; i <= this.getLastIdxKol()-1; i++) {
    				if(solution[i][this.NKolEff-brsNotZero-1]==0 && solution[i][this.NKolEff-brsNotZero]==0) {
    					System.out.print("x"+(i+1)+" = ");
    					if (solution[i][this.NKolEff-brsNotZero+2]!=0) {
    						System.out.print(df.format(solution[i][this.NKolEff-brsNotZero+2]));
    					}
						for (int j=0;j<=cpar-1;j++) {
							int cOutput = 0;
							if (solution[i][j]!=0) {
								if (-(solution[i][j])>0 && (cOutput!=0 || solution[i][this.NKolEff-brsNotZero+2]!=0)){
									System.out.print("+");
								}
								if (-(solution[i][j])==-1){
									System.out.print("-");
								}
								if (Math.abs(solution[i][j])!=1) {
									System.out.print(df.format(-(solution[i][j])));
								}
								System.out.print(variables[j]);
								cOutput++;
							}
						}
						System.out.println();
					}
					else if (solution[i][this.NKolEff-brsNotZero-1]==1 && solution[i][this.NKolEff-brsNotZero]==0) {
						System.out.print("x"+(i+1)+" = "+variables[(int)solution[i][this.NKolEff-brsNotZero+1]]);
						System.out.println();
					}
					else {
						System.out.print("x"+(i+1)+" = "+df.format(solution[i][this.NKolEff-brsNotZero+2]));
						System.out.println();
						
					}
    	        }
    			    			
    			
    		} 
    		//Kasus 3: Solusi unik
    		else {
    			String printMode = this.TulisSPLGauss();    			
    			
    			//Menyimpan solusi masing-masing variabel pada sebuah array
    	        double[] solution = new double[this.NKolEff];
    	        for (int i = this.getLastIdxKol()-1; i >= 0;i--) {
    	            double sum = 0.0;
    	            for (int j = i + 1; j < this.getLastIdxKol(); j++) 
    	                sum += this.Elmt[i][j] * solution[j];
    	            solution[i] = (this.Elmt[i][this.getLastIdxKol()] - sum);
    	        }     
    	        
    	        //Tulis hasil ke file
    	        if (printMode.equals("2")) {
    				Scanner scan = new Scanner(System.in);
    		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
    		        System.out.println("Contoh: solutions/SolusiSPL.txt");
					String namafile = "../"+scan.nextLine();
    				
    				try (FileOutputStream file = new FileOutputStream(namafile)) {
    		            byte[] b;
    		            String s =("SPL ini memiliki solusi unik, yaitu:\n");
    		            b = s.getBytes();
		                file.write(b);
    		            for (int i=0; i<=this.getLastIdxKol()-1;i++) {
    		                s = ("x"+(i+1)+" = "+ df.format(solution[i])+"\n");
    		                b = s.getBytes();
    		                file.write(b);
    		            }
    		        }
    			}
    	        
    	        //Tulis hasil ke layar
    	        System.out.println("SPL ini memiliki solusi unik, yaitu:");
    	        for (int i=0; i<=this.getLastIdxKol()-1;i++) {
    				System.out.print("x"+(i+1)+" = "+ df.format(solution[i])+"\n");
    			}
    		}
    	}
    }
    
    //SPL Gauss Jordan
    public void SPLGaussJordan() throws IOException {
    	DecimalFormat df = new DecimalFormat("#.##");
    	int brsNotZero = 0;
    	int cr = this.getLastIdxBrs();
    	boolean hasSolution = true; //Variabel penanda apakah solusi SPL ada
    	
    	
    	EliminasiGaussJordan(); //Mengubah matriks ke bentuk matriks eselon tereduksi
    	while (hasSolution && cr>=0) {
    		int cc = 0;
    		boolean rowAllZero = true;
    	    
    		//Proses pengecekan apakah elemen sebuah baris pada matriks A bernilai nol semua
    		for (int j=0; j<=this.getLastIdxKol()-1;j++) {
    			if (this.Elmt[cr][j]!=0) {
    				rowAllZero = false;
    			}
    		}
    		
    		//Mengecek elemen pada matriks b yang bersangkutan apabila ditemukan sebuah baris nol
    		if (rowAllZero) {
    			if (this.Elmt[cr][this.getLastIdxKol()]!=0) {
    				hasSolution = false; //Deklarasi bahwa SPL tidak memilki solusi
    			} 
    		} else {
				brsNotZero++;
			}
    		
    		cr--;
    	}
    	
    	//Kasus 1: Tidak mempunyai solusi
    	if (!hasSolution) {
    		String printMode = this.TulisSPLGauss();   
    		
    		//Tulis ke sebuah file
    		if (printMode.equals("2")) {
				Scanner scan = new Scanner(System.in);
		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
		        System.out.println("Contoh: solutions/SolusiSPL.txt");
				String namafile = "../"+scan.nextLine();

				try (FileOutputStream file = new FileOutputStream(namafile)) {
		            byte[] b;
		            String s =("SPL ini tidak memiliki solusi.\n");
		            b = s.getBytes();
	                file.write(b);
		        }
			}
    		
    		//Tulis ke layar
    		System.out.println("SPL ini tidak memiliki solusi.");
    		
    	} else {
    		//Kasus 2: Solusi banyak
    		if (brsNotZero<this.NKolEff-1) {
    			
    			//Deklarasi array tambahan, dengan spesifikasi:
    			//Array 2D dengan ukuran n x m (n: Jumlah variabel, jumlah persamaan efektif setelah eliminasi + 4)
    			//4 kolom di akhir array solusi dibuat untuk menyimpan data dengan ketentuan:
    				//Kolom 1: penanda apakah sebuah variabel dijadikan variabel parametrik atau tidak (1 jika ya, 0 jika tidak)
    				//Kolom 2: penanda apakah sebuah variabel memiliki solusi tunggal (1 jika ya, 0 jika tidak)
    				//Kolom 3: index variabel parametrik (variabel parametrik ke berapa), jika kolom 1 bernilai 1
    				//Kolom 4: nilai solusi tunggal suatu variabel apabila kolom 2 bernilai 1
    			double[][] solution = new double[this.NKolEff-1][this.NKolEff-brsNotZero+3];
    			//Array 1D berukuran jumlah variabel matriks, menjadi penanda apakah solusinya sudah dideklarasikan atau belum
    			boolean[] solutionDeclared = new boolean[this.NKolEff-1];
    			for (int i=0;i<=this.getLastIdxKol()-1;i++) {
    				solutionDeclared[i] = false; //Inisiasi seluruh nilai solution declared
    			}
    			    			
    			//Menentukan variabel mana saja yang akan dijadikan variabel parametrik
    			int cpar = 0; //Variabel yang menyimpan jumlah variabel yang harus dijadikan variabel parametrik
    			int cr2 = brsNotZero-1;
    			int cc2 = this.getLastIdxKol()-1;
    			
    			//Mecari kolom nol untuk kemudian variabel terkait langsung dijadikan variabel parametrik
    			for (int j=0;j<this.getLastIdxKol()-1;j++) {
    				if (this.IsKolAllZero(j)) {
    					solutionDeclared[j] = true;
    					solution[j][this.NKolEff-brsNotZero-1] = 1;
						solution[j][this.NKolEff-brsNotZero+1] = cpar;
						cpar++;
    				}
    			}
    			
    			//Menentukan variabel lain yang akan dijadikan variabel parametrik
    			while(cpar < this.NKolEff-1-brsNotZero) {
    				cc2 = this.getLastIdxKol()-1;
    				while(cc2>=0 && cpar < this.NKolEff-1-brsNotZero) {
    					if (solutionDeclared[cc2]==false && this.Elmt[cr2][cc2]!=0) {
    						//variabel tidak akan dijadikan variabel parametrik apabila ia memilki solusi unik atau bisa
    						//dideklarasikan dengan variabel parametrik lainnya
        					if(ItemsInRow(cr2,this.getLastIdxKol()-1)==1){
        						
        						solutionDeclared[cc2]=true;
            					solution[cc2][this.NKolEff-brsNotZero] = 1;
            					solution[cc2][this.NKolEff-brsNotZero+2] = this.Elmt[cr2][this.getLastIdxKol()]/this.Elmt[cr2][cc2];
            					
            				}
        					//sebuah variabel dideklarasikan sebagai variabel parametrik
        					else if(cc2!=Kolom1Utama(cr2) && IsKolZeroFromCr(cr2+1,cc2)) {
        						solutionDeclared[cc2]=true;
        						solution[cc2][this.NKolEff-brsNotZero-1] = 1;
        						solution[cc2][this.NKolEff-brsNotZero+1] = cpar;
        						cpar++;
        					}
        					
        				}
    					cc2--;
    				}
    				
    				cr2--;
    				
    			}
    			    			
    			//Mengisi array solusi sesuai dengan jenis solusi setiap variabelnya
    			cr2 = brsNotZero-1;
    			cc2 = 0;
    			for (int i=cr2;i>=0;i--) {
    				cc2 = Kolom1Utama(i);
    				if(solutionDeclared[cc2]==false) {
    					solutionDeclared[cc2]=true;
    					solution[cc2][this.NKolEff-brsNotZero+2] = this.Elmt[i][this.getLastIdxKol()];
    					for (int k=this.getLastIdxKol()-1;k>=cc2+1;k--) {
    						//Jenis solusi 1: bukan merupakan variabel parametrik ataupun solusi tunggal
    						if(solution[k][this.NKolEff-brsNotZero-1]==0 && solution[k][this.NKolEff-brsNotZero]==0) {
    							for (int j=0;j<=cpar-1;j++) {
    								solution[cc2][j] += solution[k][j]*-(this.Elmt[i][k]);
    							}
    							solution[cc2][this.NKolEff-brsNotZero+2] += solution[k][this.NKolEff-brsNotZero+2]*-(this.Elmt[i][k]);
        					}
    						//Jenis solusi 2: Merupakan variabel parametrik
    						else if (solution[k][this.NKolEff-brsNotZero-1]==1 && solution[k][this.NKolEff-brsNotZero]==0) {
    							solution[cc2][(int)solution[k][this.NKolEff-brsNotZero+1]] += this.Elmt[i][k];
    						}
    						//Jenis solusi 3: Memiliki solusi tunggal
    						else {
    							solution[cc2][this.NKolEff-brsNotZero+2] -= solution[k][this.NKolEff-brsNotZero+2]*this.Elmt[i][k];	
    							
    						}
    						
						}
    					
    				}
    				
    			}
    			
    			//Menuliskan hasil
    			String[] variables = {"r","s","t","u","v","w","x","y","z"}; 
    			String printMode = this.TulisSPLGauss();   
    			
    			//Tulis hasil ke file
    			if (printMode.equals("2")) {
    				Scanner scan = new Scanner(System.in);
    		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
    		        System.out.println("Contoh: solutions/SolusiSPL.txt");
					String namafile = "../"+scan.nextLine();
    				
    		        try (FileOutputStream file = new FileOutputStream(namafile)) {
    		            byte[] b;
    		            String s =("SPL ini memiliki solusi banyak, yang mengikuti:\n");
    		            b = s.getBytes();
		                file.write(b);
		                for (int i = this.IdxBrsMin; i <= this.getLastIdxKol()-1; i++) {
    		            	if(solution[i][this.NKolEff-brsNotZero-1]==0 && solution[i][this.NKolEff-brsNotZero]==0) {
    	    					s = ("x"+(i+1)+" = ");
    	    					b = s.getBytes();
    			                file.write(b);
    	    					if (solution[i][this.NKolEff-brsNotZero+2]!=0) {
    	    						s = (df.format(solution[i][this.NKolEff-brsNotZero+2]));
    	    						b = s.getBytes();
    	    		                file.write(b);
    	    					}
    							for (int j=0;j<=cpar-1;j++) {
    								int cOutput = 0;
    								if (solution[i][j]!=0) {
    									if (-(solution[i][j])>0 && (cOutput!=0 || solution[i][this.NKolEff-brsNotZero+2]!=0)){
    										s = ("+");
    										b = s.getBytes();
    						                file.write(b);
    									}
    									if (-(solution[i][j])==-1){
    										s = ("-");
    										b = s.getBytes();
    						                file.write(b);
    									}
    									if (Math.abs(solution[i][j])!=1) {
    										s = (df.format(-(solution[i][j])));
    										b = s.getBytes();
    						                file.write(b);
    									}
    									s = (variables[j]);
    									b = s.getBytes();
    					                file.write(b);
    									cOutput++;
    								}
    							}
    							s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    						} else if (solution[i][this.NKolEff-brsNotZero-1]==1 && solution[i][this.NKolEff-brsNotZero]==0) {
    							s = ("x"+(i+1)+" = "+variables[(int)solution[i][this.NKolEff-brsNotZero+1]]);
    							b = s.getBytes();
    			                file.write(b);
    			                s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    						} else {
    							s = ("x"+(i+1)+" = "+df.format(solution[i][this.NKolEff-brsNotZero+2]));
    							b = s.getBytes();
    			                file.write(b);
    			                s = ("\n");
    							b = s.getBytes();
    			                file.write(b);
    							
    						}
    		            }
    		        }
    		        
    			}
    			
    			//Tulis hasil ke layar
    			System.out.println("SPL ini memiliki solusi banyak, yang mengikuti:");
    			for (int i = this.IdxBrsMin; i <= this.getLastIdxKol()-1; i++) {
    				if(solution[i][this.NKolEff-brsNotZero-1]==0 && solution[i][this.NKolEff-brsNotZero]==0) {
    					System.out.print("x"+(i+1)+" = ");
    					if (solution[i][this.NKolEff-brsNotZero+2]!=0) {
    						System.out.print(df.format(solution[i][this.NKolEff-brsNotZero+2]));
    					}
						for (int j=0;j<=cpar-1;j++) {
							int cOutput = 0;
							if (solution[i][j]!=0) {
								if (-(solution[i][j])>0 && (cOutput!=0 || solution[i][this.NKolEff-brsNotZero+2]!=0)){
									System.out.print("+");
								}
								if (-(solution[i][j])==-1){
									System.out.print("-");
								}
								if (Math.abs(solution[i][j])!=1) {
									System.out.print(df.format(-(solution[i][j])));
								}
								System.out.print(variables[j]);
								cOutput++;
							}
						}
						System.out.println();
					}
					else if (solution[i][this.NKolEff-brsNotZero-1]==1 && solution[i][this.NKolEff-brsNotZero]==0) {
						System.out.print("x"+(i+1)+" = "+variables[(int)solution[i][this.NKolEff-brsNotZero+1]]);
						System.out.println();
					}
					else {
						System.out.print("x"+(i+1)+" = "+df.format(solution[i][this.NKolEff-brsNotZero+2]));
						System.out.println();
						
					}
    	        }
    			    			
    			
    		} 
    		//Kasus 3: Solusi unik
    		else {
    			String printMode = this.TulisSPLGauss();    			
    			
    			//Menyimpan solusi masing-masing variabel pada sebuah array
    	        double[] solution = new double[this.NKolEff];
    	        for (int i = this.getLastIdxKol()-1; i >= 0;i--) {
    	            double sum = 0.0;
    	            for (int j = i + 1; j < this.getLastIdxKol(); j++) 
    	                sum += this.Elmt[i][j] * solution[j];
    	            solution[i] = (this.Elmt[i][this.getLastIdxKol()] - sum);
    	        }     
    	        
    	        //Tulis hasil ke file
    	        if (printMode.equals("2")) {
    				Scanner scan = new Scanner(System.in);
    		        System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
    		        System.out.println("Contoh: solutions/SolusiSPL.txt");
					String namafile = "../"+scan.nextLine();
    				
    				try (FileOutputStream file = new FileOutputStream(namafile)) {
    		            byte[] b;
    		            String s =("SPL ini memiliki solusi unik, yaitu:\n");
    		            b = s.getBytes();
		                file.write(b);
    		            for (int i=0; i<=this.getLastIdxKol()-1;i++) {
    		                s = ("x"+(i+1)+" = "+ df.format(solution[i])+"\n");
    		                b = s.getBytes();
    		                file.write(b);
    		            }
    		        }
    			}
    	        
    	        //Tulis hasil ke layar
    	        System.out.println("SPL ini memiliki solusi unik, yaitu:");
    	        for (int i=0; i<=this.getLastIdxKol()-1;i++) {
    				System.out.print("x"+(i+1)+" = "+ df.format(solution[i])+"\n");
    			}
    		}
    	}
    	
    	
    }

	//SPL CRAMER
	//SPL CRAMER Dapat mengembalikan array dari detMinor/detUtama untuk digunakan dalam Multiple Linear Regression
	public double[] SPLCramer(boolean isMLR) {
		// Diasumsikan SPL yang diterima hanya SPL dengan n peubah dan n persamaan
		DecimalFormat df = new DecimalFormat("#.##");
		int n = this.NBrsEff;

		// Menmbuat Matriks Utama yang berisi koefisien peubah pada n persamaan
		Matriks utama = new Matriks(n, n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				utama.Elmt[i][j] = this.Elmt[i][j];
			}
		}
		//Menghitung determinan matriks utama
		double detUtama = utama.DeterminanDenganKofaktor();
		
		//Menghitung determinan tiap matriks minor (yang kolomnya sudah ditukar)
		double[] detMinor = new double[n];
		for(int i=0; i<n; i++){
			// Menukar Kolom i dengan Kolom terakhir pada matriks utama
			this.TukarKolCramer(i, this.getLastIdxKol(), utama);
			detMinor[i] = utama.DeterminanDenganKofaktor();
			// Mengembalikan kolom i ke kolom terakhir pada matriks utama
			this.TukarKolCramer(i, this.getLastIdxKol(), utama);
		}

		if(!isMLR){
			// Untuk SPL Biasa
			try {
				// Menulis Hasil SPL dengan Kaidah Cramer
				TulisSPLCramer(detUtama, detMinor);
			}catch (IOException e) {
				// Do Nothing
			}
			
		}

		//Khusus Untuk Multiple Linear Regression
		double[] solusi = new double[n];
		for(int i=0; i<n; i++){
			solusi[i] = detMinor[i]/detUtama;
		}

		return solusi;
	}
	
	public static void TulisSPLCramer(double detUtama, double[] detMinor) throws IOException{
		DecimalFormat df= new DecimalFormat("#.##");
		int n = detMinor.length;
		Scanner scan = new Scanner(System.in);
		System.out.println ("#=============================================================================================#");
		System.out.println ("# PENULISAN SOLUSI SPL DENGAN KAIDAH CRAMER                                                   #");
		System.out.println ("#---------------------------------------------------------------------------------------------#");
		System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
		System.out.println ("#=============================================================================================#");
		System.out.println ("# 1. Tampilkan pada layar                                                                     #");
		System.out.println ("# 2. Simpan dalam file      	                                                               #");
		System.out.println ("#=============================================================================================#");
		System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

		// Menerima pilihan user untuk penulisan solusi
		int pilihan = scan.nextInt();
		while (pilihan<1 || pilihan>2){
			System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
			pilihan = scan.nextInt();
		}

		// Menampilkan hasil SPL ke layar
		if (pilihan==1) {
			if(detUtama==0){
				// Jika determinan utama = 0, maka solusi SPL antara tidak memiliki solusi atau memiliki banyak solusi
				boolean isAllDetZero = true;
				int i=0;
				// Pengecekan determinan matriks minor untuk tiap peubah
				while(i<n && isAllDetZero){
					if(detMinor[i]!=0){
						isAllDetZero = false;
					}else{
						i+=1;
					}
				}

				if(isAllDetZero){
					// Jika semua determinan matriks minor = 0
					System.out.println("Solusi SPL tidak dapat dicari dengan metode cramer, karena SPL ini memiliki banyak solusi.");
				} else{
					// Jika ada determinan matriks minor != 0
					System.out.println("SPL ini tidak memiliki solusi.");
				}
			} else{
				// Jika determinan utama != 0
				System.out.println("SPL ini memiliki solusi unik, yaitu:");
				for (int i=0; i<n;i++) {
					//Menampilkan hasil bagi antara determinan minor dengan determinan utama sebagai solusi SPl untuk tiap variabel peubah
					System.out.print("x"+(i+1)+" = "+ df.format(detMinor[i]/detUtama)+"\n");
				}
			}
		} else {
			// Simpan dalam file
			Scanner sc = new Scanner(System.in);
			System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
			System.out.println("Contoh: solutions/SolusiSPL.txt");
			String namafile = "../"+sc.nextLine();

			try (FileOutputStream file = new FileOutputStream(namafile)) {
				byte[] b;
				if(detUtama==0){
					// Jika determinan utama = 0, maka solusi SPL antara tidak memiliki solusi atau memiliki banyak solusi
					boolean isAllDetZero = true;
					int i=0;
					// Pengecekan determinan matriks minor untuk tiap peubah				
					while(i<n && isAllDetZero){
						if(detMinor[i]!=0){
							isAllDetZero = false;
						}else{
							i+=1;
						}
					}
					if(isAllDetZero){
						// Jika semua determinan matriks minor = 0
						String s = "Solusi SPL tidak dapat dicari dengan metode cramer, karena SPL ini memiliki banyak solusi.\n";
						System.out.print(s);
						b = s.getBytes();
						file.write(b);
						System.out.println("Hasil perhitungan SPL dengan kaidah cramer berhasil disimpan kedalam file");
					} else{
						// Jika ada determinan matriks minor != 0
						String s = "SPL ini tidak memiliki solusi.\n";
						System.out.print(s);
						b = s.getBytes();
						file.write(b);
						System.out.println("Hasil perhitungan SPL dengan kaidah cramer berhasil disimpan kedalam file");
					}
				} else{
					// Jika determinan utama != 0
					System.out.println("SPL ini memiliki solusi unik, yaitu:");
					for (int i = 0; i <n; i++) {
						//Menampilkan hasil bagi antara determinan minor dengan determinan utama sebagai solusi SPl untuk tiap variabel peubah
						// Menyimpan solusi SPL kedalam file
						String s = ("x" + (i + 1) + " = " + df.format(detMinor[i]/detUtama) + "\n");
						System.out.print(s);
						b = s.getBytes();
						file.write(b);
					}
					System.out.println("Hasil perhitungan SPL dengan kaidah berhasil disimpan kedalam file");
				}
			}
		}
	
	}

    //DETERMINAN OBE
    public double DeterminanOBE() {
		int tukarBaris = 0; //Variabel untuk menghitung jumlah pertukaran baris yang terjadi
		double hasil = 1;
		
		for (int k = 0; k < this.NBrsEff; k++) {
			//Mencari baris dengan this.Elmn[i][k] paling besar untuk dipindahkan ke paling atas
			int currentP = k;
			for (int i = k + 1;i < this.NBrsEff; i++) {
				if (Math.abs(this.Elmt[i][k]) > Math.abs(this.Elmt[currentP][k])) {
					currentP = i;
				}
			}
			
			//Menukar baris sehingga baris dengan this.Elmt[i][k] terbesar dari i akan menjadi berada pada posisi i
			if (currentP != k) {
				tukarBaris++; //Increment tukarBaris
				TukarBrs(k, currentP);
			}
			
			cleanMatriks(this, 1e-9); //Membersihkan matriks
			
			//Menjadikan 0 semua elemen di bawah 1 utama currentP
			for (int i = k + 1; i < this.NBrsEff; i++) {
				double x = -(this.Elmt[i][k]/this.Elmt[k][k]); //Faktor pengali
				for (int j = k; j < this.NKolEff; j++) {
					this.Elmt[i][j] += this.Elmt[k][j] * x;
				}
			}
			
		}
		
		//Perhitungan determinan
		for (int i=0; i<this.NBrsEff; i++) {
			hasil *= GetElmtDiagonal(i);
		}
		hasil *= Math.pow(-1, tukarBaris); //Mengalikan hasil dengan -1 dipangkatkan jumlah pertukaran baris
		return hasil;
		
	}

    //DETERMINAN KOFAKTOR
    public double DeterminanDenganKofaktor() {
		// Diasumsikan matriks persegi
		int n = this.NBrsEff;

		if (n<=0) {
			// Basis
			// Jika ukuran matriks tidak terdefinisi
			return 0;
		} else if (n==1){
			// Basis
			// Jika matriks memiliki ukuran 1x1
			return this.Elmt[0][0];
		} else if (n==2){
			// Basis
			// Jika matriks memiliki ukuran 2x2
			return (this.Elmt[0][0] * this.Elmt[1][1]) - (this.Elmt[1][0]*this.Elmt[0][1]);
		} else{
			// Rekurens
			// Jika matriks memiliki ukuran >= 3x3
			Matriks minor = new Matriks((n-1),(n-1));	//inisialisasi matriks minor
			int i, aj, bj, k;	//indeks yang akan digunakan
			int sign = 1;
			double det = 0;

			//Lakukan perhitungan matriks kofaktor
			for(k=0; k<n ; k++){
				for (i=1 ; i<n ; i++){
					bj = 0;
					for (aj=0; aj<n ; aj++){
						if (aj!=k){
							minor.Elmt[i-1][bj] = this.Elmt[i][aj];
							++bj;
						}
					}
				}
				det = det + (sign*this.Elmt[0][k]*minor.DeterminanDenganKofaktor());
				// Merubah Nilai sign menjadi -sign
				sign = -1*sign;
			}
			return det;
			//Referensi : https://stackoverflow.com/questions/42802208/code-for-determinant-of-n-x-n-matrix
		}
	}

	// Multiple Linear Regression
	public void MultipleLinearRegression() throws IOException{
		//Preparation: Membuat SPL dari Tabel Data dengan Normal Estimation Equation for Multiple Linear Regression
		int x = this.NKolEff+1;
		int y = this.NKolEff;
		Matriks spl = new Matriks(y, x);
		for(int i=0; i<y; i++){
			for(int j=0; j<x; j++){
				spl.Elmt[i][j] = this.SumMul2BrsMLR(i, j);
			}
		}
		
		//Perhitungan bo, b1, b2, ..., bk
		double[] koef = new double[y];
		koef = spl.SPLCramer(true);
		int n = koef.length;

		// Menerima masukan user untuk nilai x1..xn 
		System.out.println("\nUntuk mengetahui estimasi nilai y, masukkan nilai variabel peubah");
		Scanner scan= new Scanner(System.in);  
		double[] variabel = new double[n];
		variabel[0] = 1;
		for(int i=1;i<n;i++){
			System.out.print("Nilai x"+i+": ");
			variabel[i] = scan.nextDouble();
		}

        System.out.println ("#=============================================================================================#");
        System.out.println ("# PENULISAN PERSAMAAN REGRESI DAN TAKSIRAN NILAI FUNGSI TERHADAP X YANG DIBERIKAN             #");
        System.out.println ("#---------------------------------------------------------------------------------------------#");
        System.out.println ("# Silakan pilih salah satu dari pilihan berikut!                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# 1. Tampilkan pada layar                                                                     #");
        System.out.println ("# 2. Simpan dalam file      	                                                              #");
        System.out.println ("#=============================================================================================#");
        System.out.println ("# Ketik '1' atau '2' pada keyboard:                                                           #");

		// Menerima masukkan user untuk pilihan penulisan solusi regresi linier berganda
        int pilihan = scan.nextInt();
        while (pilihan<1 || pilihan>2){
            System.out.println("Masukan Anda salah. Silakan ulangi kembali.");
            pilihan = scan.nextInt();
		}

		if(pilihan ==1 ){
			//Print persamaan
			DecimalFormat eq = new DecimalFormat("#.#####");
			System.out.println("\nBerikut adalah persamaan yang didapatkan dengan Regresi Linear Berganda:");
			String equation = "y = ";
		
			for(int i=0; i<n; i++){
				if(i==0){
					equation += eq.format(koef[i]);
				} else{
					if(koef[i]>0){
						equation += " + "+eq.format(koef[i])+" x"+i;
					} else if (koef[i]<0){
						equation += " - "+eq.format(Math.abs(koef[i]))+" x"+i;
					} else{
						continue;
					}
				}
			}
			System.out.println(equation);

			//Perhitungan nilai y
			DecimalFormat dfY = new DecimalFormat("#.##");
			double y1=0;
			for(int i=0; i<n; i++){
				y1 += koef[i]*variabel[i];
			}
			System.out.println("Berdasarkan nilai variabel peubah, didapatkan nilai y sebesar " + dfY.format(y1));  
		
		}else {
			
			//Print persamaan
			DecimalFormat eq = new DecimalFormat("#.#####");
			String final1 = "Berikut adalah persamaan yang didapatkan dengan Multiple Linear Regression:\n";
			String equation = "y = ";
			
			for(int i=0; i<n; i++){
				if(i==0){
					equation += eq.format(koef[i]);
				} else{
					if(koef[i]>0){
						equation += " + "+eq.format(koef[i])+" x"+i;
					} else if (koef[i]<0){
						equation += " - "+eq.format(Math.abs(koef[i]))+" x"+i;
					} else{
						continue;
					}
				}
			}
			equation += "\n";	
			
			//Perhitungan nilai y
			DecimalFormat dfY = new DecimalFormat("#.##");
			double y1=0;
			for(int i=0; i<n; i++){
				y1 += koef[i]*variabel[i];
			}
			String final2 = "Berdasarkan nilai variabel peubah, didapatkan nilai y sebesar " + dfY.format(y1)+"\n";  
			
			// Simpan dalam file
            Scanner sc = new Scanner(System.in);
            System.out.println("Masukkan nama File solusi beserta direktori dengan format nama_folder/nama_file.txt: ");
            System.out.println("Contoh: solutions/SolusiSPL.txt");
            String namafile = "../"+sc.nextLine();

            try (FileOutputStream file = new FileOutputStream(namafile)) {
				byte[] b;
				String final3 =final1+equation+final2;
                System.out.print(final3);
                b = final3.getBytes();
				file.write(b);				
                System.out.println("Persamaan Regresi dan Taksiran Nilai Fungsi berhasil disimpan kedalam file");
            }
		}
	}  

}