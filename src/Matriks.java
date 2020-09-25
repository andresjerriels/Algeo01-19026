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
                DecimalFormat df = new DecimalFormat("#.##");

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
    public void MakeMatriksInterpolasi(int NBrs) {
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
    			System.out.println("SPL ini memiliki solusi unik, yaitu: masih bingung nanti dulu");
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
	public void SPLCramer(){
		
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

}