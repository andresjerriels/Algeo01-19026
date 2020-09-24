public class InversMatriks {
    public Matriks matriks;

    public InversMatriks(Matriks M){
        this.matriks = M;
    }

    void cleanMatriks(Matriks M, double tolerance) {

        for (int i = 0; i <= M.getLastIdxBrs(); i++){
            for(int j = 0; j <= M.getLastIdxKol(); j++){
                if (Math.abs(M.Elmt[i][j]) < tolerance){
                    M.Elmt[i][j] = 0;
                }
            }
        }
    }

    public Matriks EselonBarisTereduksi() {
        int currentBrs;
        double faktor;
        double tolerance = 1e-9;
        Matriks eselon = new Matriks(this.matriks.NBrsEff, this.matriks.NKolEff);

        for (int i = 0; i <= this.matriks.getLastIdxBrs(); i++){
            for (int j = 0; j<= this.matriks.getLastIdxKol(); j++){
                eselon.Elmt[i][j] = this.matriks.Elmt[i][j];
            }
        }

        int ABrs = 0;
        int AKol = 0;
        while ((ABrs <= eselon.getLastIdxBrs()) && (AKol <= eselon.getLastIdxKol())) {
            if (Math.abs(eselon.Elmt[ABrs][AKol]) < tolerance) {
                currentBrs = ABrs;
                do {
                    currentBrs += 1;

                    if ((currentBrs <= eselon.getLastIdxBrs()) && (AKol <= eselon.getLastIdxKol())) {
                        currentBrs = ABrs;
                        AKol += 1;
                    }

                    if (currentBrs > eselon.getLastIdxBrs()) {
                        cleanMatriks(eselon, tolerance);
                        return eselon;
                    }
                } while (Math.abs(eselon.Elmt[currentBrs][AKol]) < tolerance);
                eselon.TukarBrs(ABrs, currentBrs);
            }
            faktor = 1.0 / eselon.Elmt[ABrs][AKol];

            for (int j = AKol; j <= eselon.getLastIdxKol(); j++){
                eselon.Elmt[ABrs][j] = eselon.Elmt[ABrs][j] * faktor;
            }

            for (int i = 0; i <= eselon.getLastIdxBrs(); i++){
                if ((i != ABrs) && (Math.abs(eselon.Elmt[i][AKol]) > tolerance)){
                    faktor = eselon.Elmt[i][AKol];

                    for (int j = AKol; j <= eselon.getLastIdxKol(); j++) {
                        eselon.Elmt[i][j] = eselon.Elmt[i][j] - (faktor * eselon.Elmt[ABrs][j]);
                    }
                }
            }
            ABrs += 1;
            AKol += 1;
        }
        cleanMatriks(eselon, tolerance);
        return eselon;
    }

    public void hasilInvers() {
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
        this.matriks = this.EselonBarisTereduksi();

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


}
