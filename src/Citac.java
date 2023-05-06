import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Citac {
    int[] cena;
    int[] hmotnost;
    int n;

    Citac(int _n) {
        cena = new int[_n];
        hmotnost = new int[_n];
        n = _n;
    }

    public void citajCenu()
    {
        try {
            File myObj = new File("H6_c.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextInt() && i < n) {
                cena[i] = myReader.nextInt();
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void citajHmotnost()
    {
        try {
            File myObj = new File("H6_a.txt");
            Scanner myReader = new Scanner(myObj);
            int i = 0;
            while (myReader.hasNextInt() && i < n) {
                hmotnost[i] = myReader.nextInt();
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int[] getCena() {
        return cena;
    }

    public int[] getHmotnost() {
        return hmotnost;
    }
}
