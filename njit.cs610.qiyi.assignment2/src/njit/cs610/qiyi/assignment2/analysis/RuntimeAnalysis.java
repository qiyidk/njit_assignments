package njit.cs610.qiyi.assignment2.analysis;

import njit.cs610.qiyi.assignment2.select.CountableSelect;
import njit.cs610.qiyi.assignment2.select.LinearQuickSelect;
import njit.cs610.qiyi.assignment2.select.QuickSelect;
import njit.cs610.qiyi.assignment2.select.SortSelect;

/**
 * <p>
 * RuntimeAnalysis
 * </p>
 *
 * @author qiyi
 * @version 2015-10-27
 */
public class RuntimeAnalysis {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        RuntimeAnalysis analysis = new RuntimeAnalysis();
        SortSelect sortSelect = new SortSelect("Sort&Select");
        QuickSelect quickSelect = new QuickSelect("QuickSelect");
        LinearQuickSelect linearQuickSelect = new LinearQuickSelect("LinearQuickSelect");
        
        boolean debug = false;
        if (debug) analysis.test(sortSelect, quickSelect, linearQuickSelect); // test accuracy
        // calculate time complexity
        int n;
        n = 10000;
        int[] data = DataGenerator.getRandomizedData(n);
        System.out.printf("%-20s", "Algorithm");
        System.out.printf("%-10s", "Size");
        System.out.printf("%-10s", "K");
        System.out.printf("%-12s", "Kth Value");
        System.out.printf("%-15s", "Key Comparison");
        System.out.println();
        System.out.println("------------------------------------------------------------------");
        analysis.analyzeTimeComplexity(analysis.getArray(data), sortSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), quickSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), linearQuickSelect);
        System.out.println("------------------------------------------------------------------");
        
        n = 100000;
        data = DataGenerator.getRandomizedData(n);
        analysis.analyzeTimeComplexity(analysis.getArray(data), sortSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), quickSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), linearQuickSelect);
        System.out.println("------------------------------------------------------------------");
        
        n = 1000000;
        data = DataGenerator.getRandomizedData(n);
        analysis.analyzeTimeComplexity(analysis.getArray(data), sortSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), quickSelect);
        analysis.analyzeTimeComplexity(analysis.getArray(data), linearQuickSelect);
        System.out.println("------------------------------------------------------------------");
        
    }
    
    private void test(SortSelect sortSelect, QuickSelect quickSelect,
            LinearQuickSelect linearQuickSelect) {
        // TODO Auto-generated method stub
        // test accuracy of each select algorithm
        // generate data
        int[] data8_randomized = DataGenerator.getRandomizedData(8);
        int[] data32_randomized = DataGenerator.getRandomizedData(32);
        int[] data128_randomized = DataGenerator.getRandomizedData(128);

        printArray(data8_randomized);
        System.out.println(sortSelect.getName() + ":  " + sortSelect.select(getArray(data8_randomized), 4));
        System.out.println(quickSelect.getName() + ":  " + quickSelect.select(getArray(data8_randomized), 4));
        System.out.println(linearQuickSelect.getName() + ":  " + linearQuickSelect.select(getArray(data8_randomized), 4));
        System.out.println("---------------------------------------------------------------------");
        printArray(data32_randomized);
        System.out.println(sortSelect.getName() + ":  " + sortSelect.select(getArray(data32_randomized), 16));
        System.out.println(quickSelect.getName() + ":  " + quickSelect.select(getArray(data32_randomized), 16));
        System.out.println(linearQuickSelect.getName() + ":  " + linearQuickSelect.select(getArray(data32_randomized), 16));
        System.out.println("---------------------------------------------------------------------");
        printArray(data128_randomized);
        System.out.println(sortSelect.getName() + ":  " + sortSelect.select(getArray(data128_randomized), 64));
        System.out.println(quickSelect.getName() + ":  " + quickSelect.select(getArray(data128_randomized), 64));
        System.out.println(linearQuickSelect.getName() + ":  " + linearQuickSelect.select(getArray(data128_randomized), 64));
        System.out.println("---------------------------------------------------------------------");
    }

    /**
     * analyze TimeComplexity
     * @param data original input data, this data won't be changed during analysis
     * @param select select algorithm
     */
    private void analyzeTimeComplexity(int[] data, CountableSelect select){
        int n = data.length;
        int k = n / 2;
        System.out.printf("%-20s", select.getName()+":");
        System.out.printf("%-10s", n);
        System.out.printf("%-10s", k);
        System.out.printf("%-12s", select.select(data, k));
        System.out.printf("%-15s", select.getCount());
        System.out.println();
    }
    
    /**
     * print array
     * @param a array
     */
    private void printArray(int[] a){
        int n = a.length;
        for (int i = 0; i < n; i++){
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    
    /**
     * get a new array from original Array
     */
    private int[] getArray(int[] a){
        int n = a.length;
        int[]b = new int[n];
        for (int i = 0; i < n; i++){
            b[i] = a[i];
        }
        return b;
    }
}
