package njit.cs634.qiyi.assignments.utils;

/**
 * <p>
 * Utils
 * </p>
 *
 * @author qiyi
 * @version 2016Äê9ÔÂ14ÈÕ
 */
public class Utils {

    public static void outputMean(String v){
        String[] s = v.split(", ");
        int m = 0;
        for (String str : s) m += Integer.parseInt(str);
        System.out.println(m);
        System.out.println(1.0 * m / s.length);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String v = "13, 15, 16, 16, 19, 20, 20, 21, 22, 22, 25, 25, 25, 25, 30, 33, 33, 35, 35, 35, 35, 36, 40, 45, 46, 52, 70";
        Utils.outputMean(v);
    }

}
