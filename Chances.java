package Underworlds;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author unstable
 */
public class Chances {

    static final int ITERATIONS = 1000000;
    static final int NUMBER_OF_ATK_DICE = 5;
    static final int NUMBER_OF_DEF_DICE = 3;
    final static DecimalFormat DF = new DecimalFormat("0.0000");

    static class Fighter {
        int crits;
        int successess;

        public Fighter(int numDice, int successess) {
            this.crits = 0;
            this.successess = 0;
            for (int i = 0; i < numDice; i++) {
                Dice d = new Dice();
                if (d.result == 5) {
                    this.crits++;
                } else if (d.result > 4 - successess) {
                    this.successess++;
                }
            }
        }

        class Dice {
            int result;
            public Dice() {
                result = (int) (Math.random() * 6);
            }
        }
    }

    public static void main(String[] args) {

        int[][][][][] r = new int[NUMBER_OF_ATK_DICE + 1][5][NUMBER_OF_DEF_DICE + 1][5][4];
        for (int[][][][] a : r) {
            for (int[][][] b : a) {
                for (int[][] c : b) {
                    for (int[] d : c) {
                        Arrays.fill(d, 0);
                    }
                }
            }
        }

        for (int atk_die = 1; atk_die < NUMBER_OF_ATK_DICE + 1; atk_die++) {
            for (int atk_res = 1; atk_res < 5; atk_res++) {
                for (int i = 0; i < ITERATIONS; i++) {
                    Fighter atk = new Fighter(atk_die, atk_res);
                    for (int def_die = 1; def_die < NUMBER_OF_DEF_DICE + 1; def_die++) {
                        for (int def_res = 0; def_res < 5; def_res++) {
                            Fighter def = new Fighter(def_die, def_res);

                            if (atk.crits > def.crits) {
                                r[atk_die][atk_res][def_die][def_res][0]++;
                            } else if (atk.crits == def.crits && atk.successess > def.successess) {
                                r[atk_die][atk_res][def_die][def_res][1]++;
                            } else if (atk.successess == def.successess) {
                                r[atk_die][atk_res][def_die][def_res][2]++;
                            } else {
                                r[atk_die][atk_res][def_die][def_res][3]++;
                            }
                        }
                    }
                }
            }
        }
        
        StringBuilder sB = new StringBuilder();        
        sB.append("\nAtk\t\tDef\nnumDice\tresOn\tnumDice\trestOn\n");
        for (int a = 1; a < NUMBER_OF_ATK_DICE + 1; a++) {            
            for (int b = 1; b < 5; b++) {
                for (int c = 1; c < NUMBER_OF_DEF_DICE + 1; c++) {
                    for (int d = 0; d < 5; d++) {
                        double i;
                        double j;
                        sB.append(a).append("\t").append(b).append("\t").append(c).append("\t").append(d).append("\t");
                        
                        i = r[a][b][c][d][0];
                        j = i / ITERATIONS;                       
                        sB.append(DF.format(j)).append("\t");
                        i = r[a][b][c][d][1];
                        j = i / ITERATIONS;                       
                        sB.append(DF.format(j)).append("\t");
                        i = r[a][b][c][d][2];
                        j = i / ITERATIONS;                       
                        sB.append(DF.format(j)).append("\t");
                        i = r[a][b][c][d][3];
                        j = i / ITERATIONS;                       
                        sB.append(DF.format(j)).append("\n");                               
                    }
                }
            }
        }        
        System.out.println(sB.toString());
    }
}
