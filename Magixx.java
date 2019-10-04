package Lore;

/**
 * Warhammer Fantasy, Magic Generator
 * @author jonjon
 */
public class Magixx {
    
    public String toCast;
    
    public Magixx () {
        toCast = getToCast();
    }

    protected int roll(int depth, int wizLvl, int sum, int targetVal, int success, int sixes) {
        if (depth == 0) {
            if (sixes >= 2) {   success++;} 
            else if (sum + wizLvl >= targetVal && sum > 3) {    success++;}
        } else {
            for (int i = 1; i <= 6; i++) {
                if (i == 6) {   sixes++;}
                success = roll(depth - 1, wizLvl, sum + i, targetVal, success, sixes);
            }
        }
        return success;
    }

    protected double miscasts(int depth) {
        double a = Math.pow((double) 1/6, 0) * Math.pow((double) 5/6, depth);
        double b = depth * Math.pow((double) 1/6, 1) * Math.pow((double) 5/6, depth - 1);
        return 1.00001 - a - b;
    }

    private String getToCast() {
        StringBuilder sB = new StringBuilder();
        for (int wizardLevel = 1; wizardLevel <= 7; wizardLevel++) {
            for (int dice = 1; dice <= 6; dice++) {
                sB
                        .append("\n")
                        .append(wizardLevel)
                        .append("\t")
                        .append(dice)
                        .append("\t");
                for (int castOn = 5; castOn < 25; castOn++) {
                    int chance = roll(dice, wizardLevel, 0, castOn, 0, 0);
                    double nPow = Math.pow(6, dice);
                    sB
                            .append(String.format("%.4f", chance / nPow))
                            .append("\t");
                }
                sB
                        .append(String.format("%.4f", miscasts(dice)))
                        .append("\t");
            }
        }
        return sB.toString();
    }

    public static void main(String[] args) {
        Magixx m = new Magixx();
        System.out.println(m.toCast.substring(1));
    }
}