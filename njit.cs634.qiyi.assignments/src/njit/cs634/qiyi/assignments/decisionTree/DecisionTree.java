package njit.cs634.qiyi.assignments.decisionTree;

/**
 * <p>
 * DecisionTree
 * </p>
 *
 * @author qiyi
 * @version 2016Äê10ÔÂ26ÈÕ
 */
public class DecisionTree {
    
    
    public static double info(int x, int y){
        double r1 = 1.0 * x / (x + y);
        double r2 = 1.0 * y / (x + y);
        return -r1 * Math.log10(r1) * 3.322 - r2 * Math.log10(r2) * 3.322;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int x = 1;
        int y = 4;
        double ratio = 0.5;
        System.out.println(DecisionTree.info(x, y) * ratio);

    }

}
