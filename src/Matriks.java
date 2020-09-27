import java.text.DecimalFormat;
import java.util.Scanner;

public class Matriks {
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
    //GetLaskIdxBrs
    public int getLastIdxBrs() {
        return this.NBrsEff - 1;
    }

    //GetLaskIdxKol
    public int getLastIdxKol() {
        return this.NKolEff - 1;
    }

    //Baca Matriks
    public void BacaMatriks() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Masukkan jumlah baris matriks: ");
        int NB = scan.nextInt();
        System.out.println("Masukkan jumlah kolom matriks: ");
        int NK = scan.nextInt();
        MakeEmpty(NB, NK);
        for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs(); i++){
            for (int j = this.IdxKolMin; j <= this.getLastIdxKol(); j++){
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
		double tmp;
		for (int i = this.IdxBrsMin; i <= this.getLastIdxBrs();i++){
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
			for (int j=0;j<=this.NKolEff-1;j++) {
				if (this.Elmt[i][j]==1) {
					//0in nilai di atas 1 utama
					for (int a = i-1; a>=0; a--) {
						double x = -(this.Elmt[a][j]/this.Elmt[i][j]);
						for (int b = 0; b < this.NKolEff; b++) {
							this.Elmt[a][b] += this.Elmt[i][b] * x;
						}
					}
				}
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

    public void BacaMatriksInterpolasi() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Masukkan jumlah baris: ");
        int NB = scan.nextInt();
        this.MakeEmpty(NB, 2);
        for (int i=this.IdxBrsMin; i <= this.getLastIdxBrs(); i++){
            for (int j=this.IdxKolMin; j <= this.getLastIdxKol(); j++){
                this.Elmt[i][j] = scan.nextDouble();
            }
        }
        this.MakeMatriksInterpolasi(NB);
    }

	public void SolusiInterpolasi() {
		DecimalFormat df = new DecimalFormat("#.######");
		int brsNotZero = 0;
		int cr = this.getLastIdxBrs();
		boolean hasSolution = true;

		//PREPARATION
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

		//KASUS 1: HAS NO SOLUTION
		if (!hasSolution) {
			System.out.println("Polinom Interpolasi tidak dapat dihitung");
		} else {
			//KASUS 2: INFINITELY MANY SOLUTIONS
			if (brsNotZero < this.NKolEff - 1) {
				System.out.println("Polinom Interpolasi tidak dapat dihitung");
			}
			//KASUS 3: UNIQUE SOLUTION
			else {
				System.out.println("Hasil polinom interpolasinya adalah");
				System.out.print("P(x) = ");
				for (int i = 0; i <= this.getLastIdxBrs(); i++) {
					if (i == 0) {
						if (this.Elmt[i][this.getLastIdxKol()] > 0) {
							System.out.print(df.format(this.Elmt[i][this.getLastIdxKol()]));
						} else if (this.Elmt[i][this.getLastIdxKol()] < 0) {
							System.out.print(df.format(this.Elmt[i][this.getLastIdxKol()]));
						}
					} else if (i == 1) {
						if (this.Elmt[i][this.getLastIdxKol()] > 0) {
							System.out.print(" + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x");
						} else if (this.Elmt[i][this.getLastIdxKol()] < 0) {
							System.out.print(" - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x");
						}
					} else {
						if (this.Elmt[i][this.getLastIdxKol()] > 0) {
							System.out.print(" + " + df.format(this.Elmt[i][this.getLastIdxKol()]) + "x^" + i);
						} else if (this.Elmt[i][this.getLastIdxKol()] < 0) {
							System.out.print(" - " + df.format(Math.abs(this.Elmt[i][this.getLastIdxKol()])) + "x^" + i);
						}
					}
				}
				System.out.println();
			}
		}
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

	public double Taksiran(double x){
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
	
	//Untuk Multiple Linear Regression
	//Jumlah Perkalian 2 Kolom
	public double SumMul2BrsMLR(int IdxKol1, int IdxKol2){
		double sum = 0;
		int n = this.NBrsEff;
		int k = this.NKolEff;
		if(IdxKol1==0 && IdxKol2==0){
			sum = n;
		} else if(IdxKol2 == k){
			if(IdxKol1==0){
				for(int i=0; i<n ;i++){
					sum += this.Elmt[i][0];
				}
			} else{
				for(int i=0; i<n ;i++){
					sum += this.Elmt[i][IdxKol1]*this.Elmt[i][0];
				}
			}
		} else if(IdxKol1==0 || IdxKol2==0){
			if(IdxKol1==0){
				for(int i=0; i<n; i++){
					sum += this.Elmt[i][IdxKol2];
				}
			} else{
				//IdxKol2==0
				for(int i=0; i<n; i++){
					sum+= this.Elmt[i][IdxKol1];
				}
			}
		} else{
			for(int i=0; i<n; i++){
				sum+= this.Elmt[i][IdxKol1] * this.Elmt[i][IdxKol2];
			}
		}

		return sum;
	}
    
    //=====================MAIN OPERATIONS=======================
    
    //SPL Gauss
    public void SPLGauss() {
    	DecimalFormat df = new DecimalFormat("#.##");
    	int brsNotZero = 0;
    	int cr = this.getLastIdxBrs();
    	boolean hasSolution = true;
    	
    	//PREPARATION
    	EliminasiGauss();
    	while (hasSolution && cr>=0) {
    		int cc = 0;
    		boolean rowAllZero = true;
    	    		
    		for (int j=0; j<=this.getLastIdxKol()-1;j++) {
    			if (this.Elmt[cr][j]!=0) {
    				rowAllZero = false;
    			}
    		}
    		
    		if (rowAllZero) {
    			if (this.Elmt[cr][this.getLastIdxKol()]!=0) {
    				hasSolution = false;
    			} 
    		} else {
				brsNotZero++;
			}
    		
    		cr--;
    	}
    	
    	//KASUS 1: HAS NO SOLUTION
    	if (!hasSolution) {
    		System.out.println("SPL ini tidak memiliki solusi.");
    	} else {
    		//KASUS 2: INFINITELY MANY SOLUTIONS
    		if (brsNotZero<this.NKolEff-1) {
    			System.out.println("Oke ini nanti dulu masih bingung, tapi punya solusi banyak");
    		} 
    		//KASUS 3: UNIQUE SOLUTION
    		else {
    			System.out.println("SPL ini memiliki solusi unik, yaitu:");
    	        double[] solution = new double[this.NBrsEff];
    	        for (int i = this.getLastIdxBrs(); i >= 0;i--) {
    	            double sum = 0.0;
    	            for (int j = i + 1; j < this.getLastIdxKol(); j++) 
    	                sum += this.Elmt[i][j] * solution[j];
    	            solution[i] = (this.Elmt[i][this.getLastIdxKol()] - sum);
    	        }     
    	        for (int i=0; i<=this.getLastIdxBrs();i++) {
    				System.out.print("x"+(i+1)+" = "+ df.format(solution[i])+"\n");
    			}
    	        
    		}
    	}
    }
    
    //SPL Gauss Jordan
    public void SPLGaussJordan() {
    	DecimalFormat df = new DecimalFormat("#.##");
    	int brsNotZero = 0;
    	int cr = this.getLastIdxBrs();
    	boolean hasSolution = true;
    	
    	//PREPARATION
    	EliminasiGaussJordan();
    	while (hasSolution && cr>=0) {
    		int cc = 0;
    		boolean rowAllZero = true;
    	    		
    		for (int j=0; j<=this.getLastIdxKol()-1;j++) {
    			if (this.Elmt[cr][j]!=0) {
    				rowAllZero = false;
    			}
    		}
    		
    		if (rowAllZero) {
    			if (this.Elmt[cr][this.getLastIdxKol()]!=0) {
    				hasSolution = false;
    			} 
    		} else {
				brsNotZero++;
			}
    		
    		cr--;
    	}
    	
    	//KASUS 1: HAS NO SOLUTION
    	if (!hasSolution) {
    		System.out.println("SPL ini tidak memiliki solusi.");
    	} else {
    		//KASUS 2: INFINITELY MANY SOLUTIONS
    		if (brsNotZero<this.NKolEff-1) {
    			System.out.println("Oke ini nanti dulu masih bingung, tapi punya solusi banyak");
    		} 
    		//KASUS 3: UNIQUE SOLUTION
    		else {
    			System.out.println("SPL ini memiliki solusi unik, yaitu:");
    			for (int i=0; i<=this.getLastIdxBrs();i++) {
    				System.out.print("x"+(i+1)+" = "+ df.format(this.Elmt[i][this.getLastIdxKol()])+"\n");
    			}
    		}
    	}
    	
    	
    }

	//SPL CRAMER
	//SPL CRAMER Dapat mengembalikan array dari detMinor/detUtama untuk digunakan dalam Multiple Linear Regression
	public double[] SPLCramer(boolean isMLR){
		DecimalFormat df = new DecimalFormat("#.##");
		int n = this.NBrsEff;
		Matriks utama = new Matriks(n, n);
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				utama.Elmt[i][j] = this.Elmt[i][j];
			}
		}
		double detUtama = utama.DeterminanDenganKofaktor();
		double[] detMinor = new double[n];
		for(int i=0; i<n; i++){
			this.TukarKolCramer(i, this.getLastIdxKol(), utama);
			detMinor[i] = utama.DeterminanDenganKofaktor();
			this.TukarKolCramer(i, this.getLastIdxKol(), utama);
		}
		if(!isMLR){
			// Untuk SPL Biasa
			if(detUtama==0){
				boolean isAllDetZero = true;
				int i=0;
				while(i<n && isAllDetZero){
					if(detMinor[i]!=0){
						isAllDetZero = false;
					}else{
						i+=1;
					}
				}
				if(isAllDetZero){
					System.out.println("Solusi SPL tidak dapat dicari dengan metode cramer, karena SPL ini memiliki banyak solusi.");
				} else{
					System.out.println("SPL ini tidak memiliki solusi.");
				}
			} else{
				System.out.println("SPL ini memiliki solusi unik, yaitu:");
				for (int i=0; i<n;i++) {
					System.out.print("x"+(i+1)+" = "+ df.format(detMinor[i]/detUtama)+"\n");
				}
			}
		}

		//Khusus Untuk Multiple Linear Regression
		double[] solusi = new double[n];
		for(int i=0; i<n; i++){
			solusi[i] = detMinor[i]/detUtama;
		}
		return solusi;
	}

    //Determinan OBE
    public float DeterminanOBE() {
		int tukarBaris = 0;
		int hasil = 1;
		
		for (int k = 0; k < this.NBrsEff; k++) {
			//cari baris dengan this.matrix[i][k] paling besar
			int currentP = k;
			for (int i = k + 1;i < this.NBrsEff; i++) {
				if (Math.abs(this.Elmt[i][k]) > Math.abs(this.Elmt[currentP][k])) {
					currentP = i;
				}
			}
			
			if (currentP != k) {
				tukarBaris++;
				//tukar baris  --> nanti ganti sama fungsi swap aja
				TukarBrs(k, currentP);
			}
			
			
			//0in nilai di bawah 1 utama currentP
			for (int i = k + 1; i < this.NBrsEff; i++) {
				double x = -(this.Elmt[i][k]/this.Elmt[k][k]);
				for (int j = k; j < this.NKolEff; j++) {
					this.Elmt[i][j] += this.Elmt[k][j] * x;
				}
			}
			
		}
		
		//Perhitungan determinan
		for (int i=0; i<this.NBrsEff; i++) {
			hasil *= GetElmtDiagonal(i);
		}
		hasil *= Math.pow(-1, tukarBaris);
		return hasil;
		
	}

    //Determinan Kofaktor
    public double DeterminanDenganKofaktor() {
		int n = this.NBrsEff;
		if (n<=0) {
			return 0;
		} else if (n==1){
			return this.Elmt[0][0];
		} else if (n==2){
			return (this.Elmt[0][0] * this.Elmt[1][1]) - (this.Elmt[1][0]*this.Elmt[0][1]);
		} else{
			Matriks minor = new Matriks((n-1),(n-1));	//inisialisasi matriks minor
			int i, aj, bj, k;	//indeks yang akan digunakan
			int sign = 1;
			double det = 0;
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
				sign = -1*sign;
			}
			return det;
		}
	}

	// Multiple Linear Regression
	public void MultipleLinearRegression(){
		//Preparation: Membuat SPL dari Tabel Data dengan Normal Estimation Equation for Multiple Linear Regression
		int x = this.NKolEff+1;
		int y = this.NKolEff;
		Matriks spl = new Matriks(y, x);
		for(int i=0; i<y; i++){
			for(int j=0; j<x; j++){
				spl.Elmt[i][j] = this.SumMul2BrsMLR(i, j);
			}
		}
		System.out.println("\nSetelah diterapkan Normal Estimation Equation for Multiple Linear Regression, maka diperoleh sistem persamaan linear yang direpresentasikan dalam matriks augmented sebagai berikut:");
		spl.TulisMatriks();
		
		//Perhitungan bo, b1, b2, ..., bk
		double[] koef = new double[y];
		koef = spl.SPLCramer(true);
		int n = koef.length;

		//Print persamaan
		DecimalFormat df = new DecimalFormat("#.##");
		System.out.println("\nBerikut adalah persamaan untuk mendapatkan nilai Nitrous Oxide (y):");
		System.out.print("y = ");
		for(int i=0; i<n; i++){
			if(i==0){
				System.out.print(df.format(koef[i]));
			} else{
				if(koef[i]>0){
					System.out.print(" + "+df.format(koef[i])+" x"+i);
				} else if (koef[i]<0){
					System.out.print(" - "+df.format(Math.abs(koef[i]))+" x"+i);
				} else{
					continue;
				}
			}
		}
		System.out.print("\n");

		// Menerima masukan user untuk nilai Humidity, Temperatur, dan Tekanan Udara 
		System.out.println("\nUntuk mengetahui estimasi nilai Nitrous Oxide, masukkan beberapa data yang diperlukan.");
		Scanner sc= new Scanner(System.in);  
		System.out.print("Masukkan Nilai Humidity: ");  
		double h= sc.nextDouble();  
		System.out.print("Masukkan Nilai Temperatur: ");  
		double t= sc.nextDouble();  
		System.out.print("Masukkan Nilai Tekanan Udara: ");  
		double u= sc.nextDouble();
		
		//Perhitungan nilai Nitrous Oxide
		double nitrous = koef[0] + h*koef[1] + t*koef[2] + u*koef[3];
		System.out.println("Berdasarkan data tersebut, didapatkan nilai Nitrous Oxide sebesar " + df.format(nitrous));  
	
	}  

}