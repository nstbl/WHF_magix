package kriegshammare;
import java.text.DecimalFormat;

/**
 *
 * @author unstable 2022.07.22
 */
public class Wizassist {

    final static int ITERATIONS = 180286;
    final static int TO_CAST = 26;
    final static int NUM_DICES = 6;
    final static DecimalFormat DF = new DecimalFormat("0.0000");
    final static boolean SYS = false; //set to false for whfb 8th ed | true for whfb wap
    
    static double[][] result;
    static double[] miscast;

    public static void main(String[] args) {
        result = new double[TO_CAST - 2][NUM_DICES];
        miscast = new double[NUM_DICES];
        
        for (int iter = 0; iter < ITERATIONS; iter++) {
            for (int i = 3; i < TO_CAST + 1; i++) {
                for (int j = 1; j < NUM_DICES + 1; j++) {
                    Result r = new Result();
                    for (int k = 0; k < j; k++) { 
                        r.getSum(true); //t.c.
                    }  
                    if (r.sum >= i || (!SYS && r.sixes > 1)) { result[i - 3][j - 1]++; }
                    if (i == TO_CAST - 1 && ((SYS && r.ones > 1) || (!SYS && r.sixes > 1))) { miscast[j - 1]++; }
                }
            }
        }
        printSum();
    }

    static void printSum() {
        int i = 3;
        System.out.print("target:\n\t");
        for (int j = 0; j < NUM_DICES; j++) {  System.out.print(j + 1 + "d\t"); }
        for (double[] d : result) { 
            System.out.print("\n" + (i++) + "\t"); 
            for (double dd : d) { System.out.print(DF.format(dd / ITERATIONS) + "\t"); }
        }
        System.out.print("\nmiscast:\n\t");
        for (double d : miscast) {
            System.out.print(DF.format(d/ITERATIONS) + "\t");
        }       
    }

    static class Result {
        int sum, sixes, ones;

        Result() {
            sum = 0; sixes = 0; ones = 0;
        }

        void getSum(boolean nat) {
            int d = (int) (Math.random() * 6) + 1;
            if (SYS && nat && d == 6) { getSum(false); } //exploding sixes
            sixes = d == 6 ? ++sixes : sixes;
            ones = d == 1 ? ++ones : ones;
            sum += d;
        }
    }
}
