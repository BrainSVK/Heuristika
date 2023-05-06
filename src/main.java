import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        int n = 500;
        int r = 350;
        int K = 11500;
        Citac citac = new Citac(n);
        citac.citajCenu();
        citac.citajHmotnost();
        Vec[] vec = new Vec[n];
        for (int i = 0; i < n; i++)
        {
            vec[i] = new Vec(i,citac.cena[i],citac.hmotnost[i]);
        }
        Heuristika heuristika = new Heuristika(vec,K,r,n);
        heuristika.vypocitajDualNajmensimPomerom();
        heuristika.vypocitajPrvyVhodny();
    }
}
