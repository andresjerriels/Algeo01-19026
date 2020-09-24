import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DariFile {
    public File file;
    private String namaFile;

    public void BacaNamaFile() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Masukkan nama File berisi matriks dengan format .txt: ");
        System.out.println("Nama file: ");
        this.namaFile = scan.nextLine();
        this.file = new File(this.namaFile);
    }

    int JmlElmtMatriks() throws IOException {
        int cnt = 0;
        try (Scanner scan = new Scanner(this.file)) {
            while (scan.hasNextDouble()) {
                cnt += 1;
                scan.nextDouble();
            }
            return cnt;
        }
    }

    int JmlBrsMatriks() throws IOException {
        int cnt = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader (this.namaFile))) {
            while (reader.readLine() != null) {
                cnt += 1;
            }
        }
        return cnt;
    }

    int JmlKolMatriks() throws IOException {
        return JmlElmtMatriks() / JmlBrsMatriks();
    }

    public void BacaFileMatriksSPL(Matriks M) throws IOException {
        M.MakeEmpty(JmlBrsMatriks(), JmlKolMatriks());
        try (Scanner scan = new Scanner(this.file)) {
            for (int i=M.IdxBrsMin;i<=M.getLastIdxBrs();i++) {
                for (int j = M.IdxKolMin; i <= M.getLastIdxKol(); i++) {
                    M.Elmt[i][j] = scan.nextDouble();
                }
            }
        }
    }

    public void BacaFileMatriksInterpolasi(Matriks M) throws IOException {
        M.MakeEmpty(JmlBrsMatriks(), 2);
        try (Scanner scan = new Scanner(this.file)) {
            for (int i=0;i<=M.getLastIdxBrs();i++){
                for (int j=0;j<=M.getLastIdxKol();j++){
                    M.Elmt[i][j] = scan.nextDouble();
                }
            }
        }
        M.MakeMatriksInterpolasi(JmlBrsMatriks());
    }
}
