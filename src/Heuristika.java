import java.io.FileWriter;
import java.io.IOException;

public class Heuristika {
    Vec[] vec;
    int K;
    int r;
    int aktual_r;
    int HUF;
    int hm;
    int n;
    String vypis = "";

    Heuristika(Vec[] _vec, int _K, int _r, int _n) {
        vec = _vec;
        K = _K;
        r = _r;
        n = _n;
        aktual_r = 0;
    }

    public void nastavNaDual() {
        for (int i = 0; i < vec.length; i++) {
            vec[i].setvBatohu(1);
        }
        aktual_r = n;
    }

    public void vypisPrvyRiadok() {
        for (int i = 1; i <= vec.length; i++) {
            System.out.print("x" + i + " ");
            vypis += "x" + i + " ";
        }
        System.out.println("HUF   HM    <= K     akt_r <= r");
        vypis += "HUF   HM    <= K     akt_r <= r\n";
    }

    public void vypisPrvyRiadokPrvyVhodnyKoniec() {
        vypis = "";
        for (int i = 1; i <= vec.length; i++) {
            System.out.print("x" + i + " ");
            vypis += "x" + i + " ";
        }
        System.out.println("VK HUF   P  V");
        vypis += "VK HUF   P  V\n";

    }

    public void vypisPrvyRiadokPrvyVhodny() {
        System.out.print("Z  |N   ");
        for (int i = 1; i <= vec.length; i++) {
            System.out.print("x" + i + " ");
        }
        System.out.println("VK HUF   P  V");
    }

    public int kolkoJeNulovych() {
        int nulovych = 0;
        for (int i = 0; i < n; i++) {
            if (vec[i].getvBatohu() == 0) {
                nulovych++;
            }
        }
        return nulovych;
    }

    public void naplnN(Vec[] _vec) {
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (vec[i].getvBatohu() == 0) {
                _vec[j] = vec[i];
                j++;
            }
        }
    }

    public int vypocitajPrvyVhodnyVymenu() {
        int pocetVymenNaJedenPredmet = kolkoJeNulovych();
        int vhodnaKapacita = K - hm;
        Vec[] nuloveVeci = new Vec[pocetVymenNaJedenPredmet];
        naplnN(nuloveVeci);
        for (int i = 0; i < n; i++) {
            if (vec[i].getvBatohu() == 1) {
                vec[i].setvBatohu(0);
                for (int j = 0; j < pocetVymenNaJedenPredmet; j++) {
                    vec[nuloveVeci[j].getIndex()].setvBatohu(1);
                    if (vypisRiadokPrvyVhodny(vec[i].getIndex(),nuloveVeci[j].getIndex(),vhodnaKapacita) == 2) {
                        HUF = HUF - vec[i].getCena() + nuloveVeci[j].getCena();
                        hm = hm - vec[i].getHmotnost() + nuloveVeci[j].getHmotnost();
                        System.out.println("vymenila sa vec s indexom: " + (vec[i].getIndex()+1) + " za vec s indexom: " + (nuloveVeci[j].getIndex()+1));
                        return 1;
                    }
                    vec[nuloveVeci[j].getIndex()].setvBatohu(0);
                }
                vec[i].setvBatohu(1);
            }
        }
        System.out.println("\nNenasla sa ziadna vhodna vymena");
        vypisPrvyRiadokPrvyVhodnyKoniec();
        vypisRiadokPrvyVhodnyKoniec(vhodnaKapacita);
        System.out.println("Posledny riadok je vysledkom");
        return 0;
    }

    public void vypocitajPrvyVhodny() {
        System.out.println("Zaciatok vymennej heurestiky podla strategie prvy vhodny");
        boolean koniec = false;
        while (!koniec) {
            if (vypocitajPrvyVhodnyVymenu() == 0) {
                koniec = true;
            }
        }
    }

    public void vypocitajDualNajmensimPomerom() {
        System.out.println("Zaciatok Duálnej heuristiky s najmensim výhodnostným koeficientom");
        nastavNaDual();
        vypisPrvyRiadok();
        naplnDual();
        boolean pokracuj = true;
        while (pokracuj) {
            aktual_r--;
            odoberVecSNajmensimPomerom();
            vypisRiadok();
            if (hm <= K && aktual_r <= r) {
                vypisRiadokKoniec();
                try {
                    FileWriter myWriter = new FileWriter("dualHeuristika.txt");
                    myWriter.write(vypis);
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pokracuj = false;
            }
        }
        System.out.println("Posledny riadok je vysledkom \n");
    }

    public void vypisRiadok() {
        for (int i = 0; i < vec.length; i++) {
            if (i < 9) {
                System.out.print(vec[i].getvBatohu() + "  ");
            } else if (i < 99){
                System.out.print(vec[i].getvBatohu() + "   ");
            } else {
                System.out.print(vec[i].getvBatohu() + "    ");
            }
        }
        System.out.println(HUF + " " + hm + " <= " + K + " , " + aktual_r + " <= " + r);
    }

    public void vypisRiadokKoniec() {
        for (int i = 0; i < vec.length; i++) {
            if (i < 9) {
                vypis += vec[i].getvBatohu() + "  ";
            } else if (i < 99){
                vypis += vec[i].getvBatohu() + "   ";
            } else {
                vypis += vec[i].getvBatohu() + "    ";
            }
        }
        vypis += HUF + " " + hm + " <= " + K + " , " + aktual_r + " <= " + r;
    }


    public int vypisRiadokPrvyVhodny(int z,int v, int VK) {
        int koniec = 0;
        char pv = '-';
        char vv = '-';
        String medzera = "";
        if (VK + vec[z].getHmotnost() - vec[v].getHmotnost() >= 0) {
            pv = 'p';
            koniec++;
        }
        if (HUF - vec[z].getCena() + vec[v].getCena() > HUF) {
            vv = 'v';
            koniec++;
        }
        if (koniec == 2) {
            vypisPrvyRiadokPrvyVhodny();
            if (z < 9) {
                System.out.print(z + 1 + "   ");
            } else if (z < 99) {
                System.out.print(z + 1 + "  ");
            } else {
                System.out.print(z + 1 + " ");
            }
            if (v < 9) {
                System.out.print(v + 1 + "   ");
            } else if (v < 99) {
                System.out.print(v + 1 + "  ");
            } else {
                System.out.print(v + 1 + " ");
            }
            for (int i = 0; i < vec.length; i++) {
                if (i < 9) {
                    System.out.print(vec[i].getvBatohu() + "  ");
                } else if (i < 99) {
                    System.out.print(vec[i].getvBatohu() + "   ");
                } else {
                    System.out.print(vec[i].getvBatohu() + "    ");
                }
            }
            if (VK + vec[z].getHmotnost() - vec[v].getHmotnost() < 10) {
                medzera = " ";
            }
            System.out.println((VK + vec[z].getHmotnost() - vec[v].getHmotnost()) + medzera + " " + (HUF - vec[z].getCena() + vec[v].getCena()) + " " + pv + "  " + vv);
        }
        return koniec;
    }

    public void vypisRiadokPrvyVhodnyKoniec(int _vhodnaKapacita) {
        for (int i = 0; i < vec.length; i++) {
            if (i < 9) {
                System.out.print(vec[i].getvBatohu() + "  ");
                vypis += vec[i].getvBatohu() + "  ";
            } else if (i < 99) {
                System.out.print(vec[i].getvBatohu() + "   ");
                vypis += vec[i].getvBatohu() + "   ";
            } else {
                System.out.print(vec[i].getvBatohu() + "    ");
                vypis += vec[i].getvBatohu() + "    ";
            }
        }
        String medzera = "";
        if (_vhodnaKapacita < 10) {
            medzera = " ";
        }
        System.out.println(_vhodnaKapacita + medzera + " " + HUF  + " p " + " v");
        vypis += _vhodnaKapacita + medzera + " " + HUF  + " p " + " v";
        try {
            FileWriter myWriter = new FileWriter("prvyVhodny.txt");
            myWriter.write(vypis);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void naplnDual() {
        HUF = 0;
        hm = 0;
        for (int i = 0; i < vec.length; i++) {
            if (i < 9) {
                System.out.print(vec[i].getvBatohu() + "  ");
            } else if (i < 99){
                System.out.print(vec[i].getvBatohu() + "   ");
            } else {
                System.out.print(vec[i].getvBatohu() + "    ");
            }
            if (vec[i].getvBatohu() == 1) {
                HUF += vec[i].getCena();
                hm += vec[i].getHmotnost();
            }
        }
        System.out.println(HUF + " " + hm + " <= " + K + " , " + aktual_r + " <= " + r);

    }

    public void odoberVecSNajmensimPomerom() {
        int index = -1;
        double najmensi = Integer.MAX_VALUE;
        for (int i = 0; i < vec.length; i++) {
            if (vec[i].getVyhodKoef() < najmensi && vec[i].getvBatohu() == 1) {
                najmensi = vec[i].getVyhodKoef();
                index = vec[i].getIndex();
            }
        }
        vec[index].setvBatohu(0);
        HUF -= vec[index].getCena();
        hm -= vec[index].getHmotnost();
    }


}
