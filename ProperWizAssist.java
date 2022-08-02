package kriegshammare;

/**
 *
 * @author unstable
 */
public class ProperWizAssist {

    private static final int TOCAST = 32;
    String result;

    public ProperWizAssist(int depth, int target) {
        result = getPresentation(depth, target);
    }
    
    private String getPresentation(int depth, int target) {
        int successes = getProbability(depth, target, 0, 0, 0);
        double nPow = Math.pow(6, depth);
        return String.format("%.4f", successes / nPow);        
    }

    private int getProbability(int depth, int target, int sum, int success, int sixes) {
        if (depth == 0) {
            if (sixes >= 2) { success++; } 
            else if (sum >= target && sum > 2) { success++; }
        } else {
            for (int i = 1; i <= 6; i++) {
                if (i == 6) { sixes++; }
                success = getProbability(depth - 1, target, sum + i, success, sixes);
            }
        }
        return success;
    }

    public static void main(String[] args) {
        StringBuilder sB = new StringBuilder();
        
        for (int i = 3; i < TOCAST + 1; i++) {
            sB.append(i);
            for (int j = 1; j < 7; j++) {
                ProperWizAssist c = new ProperWizAssist(j, i);
                sB.append("\t");
                sB.append(c.result);
            }
            sB.append("\n");
        }
        System.out.println(sB.toString());
    }
}
