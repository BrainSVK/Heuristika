public class Vec {
    private int index;
    private int cena;
    private int hmotnost;
    private double vyhodKoef;
    private int vBatohu;

    Vec(int _index, int _cena, int _hmotnost) {
        index = _index;
        cena = _cena;
        hmotnost = _hmotnost;
        vyhodKoef = cena / (double)hmotnost;
        vBatohu = 0;
    }

    public double getVyhodKoef() {
        return vyhodKoef;
    }

    public int getCena() {
        return cena;
    }

    public int getHmotnost() {
        return hmotnost;
    }

    public int getIndex() {
        return index;
    }

    public int getvBatohu() {
        return vBatohu;
    }

    public void setvBatohu(int vBatohu) {
        this.vBatohu = vBatohu;
    }
}
