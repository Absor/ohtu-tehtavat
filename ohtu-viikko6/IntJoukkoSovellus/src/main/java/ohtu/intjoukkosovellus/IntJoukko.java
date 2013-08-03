package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        ljono = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti <= 0) {
            throw new IllegalArgumentException("Kapasiteetin oltava positiivinen.");
        }
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti <= 0) {
            throw new IllegalArgumentException("Kapasiteetin oltava positiivinen.");
        }
        if (kasvatuskoko <= 0) {
            throw new IllegalArgumentException("Kasvatuskoon oltava positiivinen.");
        }
        ljono = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            laajennaTaulukkoaJosTaynna();
            return true;
        }
        return false;
    }

    private void laajennaTaulukkoaJosTaynna() {
        if (alkioidenLkm == ljono.length) {
            int[] taulukkoOld = ljono;
            ljono = new int[alkioidenLkm + kasvatuskoko];
            System.arraycopy(taulukkoOld, 0, ljono, 0, alkioidenLkm);
        }
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                alkioidenLkm--;
                siirraLukujaTaaksepainTaulukossa(i);
                return true;
            }
        }
        return false;
    }

    private void siirraLukujaTaaksepainTaulukossa(int alkuIndeksi) {
        for (int j = alkuIndeksi; j < alkioidenLkm; j++) {
            ljono[j] = ljono[j + 1];
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < alkioidenLkm; i++) {
            builder.append(ljono[i]);
            if (i != alkioidenLkm - 1) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(ljono, 0, taulu, 0, taulu.length);
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        for (int aJasen : a.toIntArray()) {
            yhdiste.lisaa(aJasen);
        }
        for (int bJasen : b.toIntArray()) {
            yhdiste.lisaa(bJasen);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int aJasen : a.toIntArray()) {
            for (int bJasen : b.toIntArray()) {
                if (aJasen == bJasen) {
                    leikkaus.lisaa(aJasen);
                }
            }
        }
        return leikkaus;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();
        for (int aJasen : a.toIntArray()) {
            erotus.lisaa(aJasen);
        }
        for (int bJasen : b.toIntArray()) {
            erotus.poista(bJasen);
        }
        return erotus;
    }
}