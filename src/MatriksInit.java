import java.io.File;
import java.util.Scanner;

public class MatriksInit {
	Scanner scan;
	
	//Attributes
	int NBrsEff = 0;
	int NKolEff = 0;
	double[][] matrix;
	String fileName;
	
	//Constructor
	MatriksInit(){
		
		scan = new Scanner(System.in);
		System.out.println ("Pilih salah satu cara input di bawah ini: ");
        System.out.println ("1. Baca dari keyboard");
        System.out.println ("2. Baca dari file yang sudah ada");
        boolean inputSuccess = false;
        
        while (!inputSuccess) {
        	int caraInput = scan.nextInt();
        	if (caraInput == 1) {
            	matrixInitKeyboard();
            	enterMatrix(scan);
            	inputSuccess = true;
            } else if (caraInput == 2){
            	System.out.println("Masukkan nama file: ");
            	Scanner fileNameScanner = new Scanner(System.in);
            	fileName = fileNameScanner.next();
            	fileNameScanner.close();
            	openFile(fileName);
        		matrixInitFile();
        		enterMatrix(scan);
        		closeFile();
        		inputSuccess = true;
            } else {
            	System.out.println("Pilihan tidak valid, silakan coba lagi");
            }
        }
        
		
		
	}
	
	//Open File (entry from file)
	public void openFile(String namaFile) {
		try {
			scan = new Scanner(new File(namaFile));
		} catch(Exception e){
			System.out.println("File not found");
		}
	}
	
	//Close File (entry from file)
	public void closeFile() {
		scan.close();
	}
	
	//Matrix initialization (entry from file)
	public void matrixInitFile() {
		while(scan.hasNextLine()) {
			this.NBrsEff += 1;
			Scanner bacaKolom = new Scanner(scan.nextLine());
			while(bacaKolom.hasNextDouble()) {
				this.NKolEff += 1;
				bacaKolom.nextDouble();
			}
		}
		
		this.NKolEff /= this.NBrsEff;
		this.matrix = new double[NBrsEff][NKolEff];
		
		closeFile();
		openFile(fileName);
	}
	
	
	//Matrix initialization (entry from keyboard)
	public void matrixInitKeyboard() {
		scan = new Scanner(System.in);
		System.out.println("Masukkan jumlah baris matriks: ");
	    this.NBrsEff = scan.nextInt();
	    System.out.println("Masukkan jumlah kolom matriks: ");
	    this.NKolEff = scan.nextInt();
	    
	    this.matrix = new double[NBrsEff][NKolEff];
	}
	
	//Enter Matrix Data Value
	public void enterMatrix (Scanner scan) {			
		for (int i = 0; i < this.NBrsEff; i++) {
			for (int j = 0; j < this.NKolEff; j++) {
				this.matrix[i][j] = scan.nextDouble();
			}
		}
	}
	
	//MatriksInit to Matriks
	void toMatriks(Matriks M) {
		for (int i = 0; i < this.NBrsEff; i++) {
			for (int j = 0; j < this.NKolEff; j++) {
				M.Elmt[i][j] = this.matrix[i][j];
			}
		}
	}
	
}
