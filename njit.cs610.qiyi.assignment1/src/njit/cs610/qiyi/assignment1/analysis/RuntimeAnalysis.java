package njit.cs610.qiyi.assignment1.analysis;

import njit.cs610.qiyi.assignment1.sort.CountableSort;
import njit.cs610.qiyi.assignment1.sort.HeapSort;
import njit.cs610.qiyi.assignment1.sort.MergeSort;
import njit.cs610.qiyi.assignment1.sort.QuickSort;
import njit.cs610.qiyi.assignment1.sort.RadixSort;

/**
 * <p>
 * RuntimeAnalysis
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public class RuntimeAnalysis {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        RuntimeAnalysis analysis = new RuntimeAnalysis();
        QuickSort quicksort = new QuickSort("Quick Sort");
        MergeSort mergeSort = new MergeSort("Merge Sort");
        HeapSort heapSort = new HeapSort("Heap Sort");
        RadixSort radixSort = new RadixSort("Radix Sort");
        
        // test accuracy of each sorting algorithm
        // generate data
        int[] data32_sorted = DataGenerator.getAscendingData(32);
        int[] data32_reverselySorted = DataGenerator.getDescendingData(32);
        int[] data32_randomized = DataGenerator.getRandomizedData(32);
        // sort and analysis
        // sorted data
        System.out.println("Sorted data:");
        analysis.analyzeAndPrintResult(data32_sorted, quicksort);
        analysis.analyzeAndPrintResult(data32_sorted, mergeSort);
        analysis.analyzeAndPrintResult(data32_sorted, heapSort);
        analysis.analyzeAndPrintResult(data32_sorted, radixSort);
        System.out.println("---------------------------------------------------------------------");
        // reverselySorted data
        System.out.println("reverselySorted data:");
        analysis.analyzeAndPrintResult(data32_reverselySorted, quicksort);
        analysis.analyzeAndPrintResult(data32_reverselySorted, mergeSort);
        analysis.analyzeAndPrintResult(data32_reverselySorted, heapSort);
        analysis.analyzeAndPrintResult(data32_reverselySorted, radixSort);
        System.out.println("---------------------------------------------------------------------");
        // randomized data
        System.out.println("randomized data:");
        analysis.analyzeAndPrintResult(data32_randomized, quicksort);
        analysis.analyzeAndPrintResult(data32_randomized, mergeSort);
        analysis.analyzeAndPrintResult(data32_randomized, heapSort);
        analysis.analyzeAndPrintResult(data32_randomized, radixSort);
        System.out.println("---------------------------------------------------------------------");
        
        // test time complexity
        // generate data
        int[] data10 = DataGenerator.getRandomizedData((int)Math.pow(2, 10));
        int[] data15 = DataGenerator.getRandomizedData((int)Math.pow(2, 15));
        int[] data20 = DataGenerator.getRandomizedData((int)Math.pow(2, 20));
        // sort and analysis
        // print header
        System.out.printf("%-15s", "SortName");
        System.out.printf("%-10s", "Size");
        System.out.printf("%-15s", "Comparison");
        System.out.printf("%-10s", "RunTime");
        System.out.printf("%-20s", "TimeComplexity");
        System.out.println();
        // 2^10 data
        analysis.analyzeTimeComplexity(data10, quicksort);
        analysis.analyzeTimeComplexity(data10, mergeSort);
        analysis.analyzeTimeComplexity(data10, heapSort);
        analysis.analyzeTimeComplexity(data10, radixSort);
        System.out.println("---------------------------------------------------------------------");
        // 2^15 data
        analysis.analyzeTimeComplexity(data15, quicksort);
        analysis.analyzeTimeComplexity(data15, mergeSort);
        analysis.analyzeTimeComplexity(data15, heapSort);
        analysis.analyzeTimeComplexity(data15, radixSort);
        System.out.println("---------------------------------------------------------------------");
        // 2^20 data
        analysis.analyzeTimeComplexity(data20, quicksort);
        analysis.analyzeTimeComplexity(data20, mergeSort);
        analysis.analyzeTimeComplexity(data20, heapSort);
        analysis.analyzeTimeComplexity(data20, radixSort);
        System.out.println("---------------------------------------------------------------------");
    }
    
    /**
     * analyze TimeComplexity
     * @param data original input data, this data won't be changed during analysis
     * @param sort sort algorithm
     */
    private void analyzeTimeComplexity(int[] data, CountableSort sort){
        int n = data.length;
        int[] copy = new int[n]; 
        copyArray(data, copy); // copy array 
        long before = System.currentTimeMillis();
        int comparison = sort.sort(copy);
        long after = System.currentTimeMillis();
        int nlgn = (int)(Math.log10(n)/ Math.log10(2)) * n; // compute NlogN
        System.out.printf("%-15s", sort.getName());
        System.out.printf("%-10s", n);
        System.out.printf("%-15s", comparison);
        System.out.printf("%-10s", (after - before) + "ms");
        System.out.printf("%-20s", Math.round(comparison * 1.0 / nlgn * 100)/100.0 + " NlogN");
        System.out.println();
    }
    
    /**
     * analyze sorting algorithm and print sorting result
     * @param data original input data, this data won't be changed during analysis
     * @param sort sort algorithm
     */
    private void analyzeAndPrintResult(int[] data, CountableSort sort){
        int n = data.length;
        int[] copy = new int[n]; 
        copyArray(data, copy); // copy array 
        long before = System.currentTimeMillis();
        int comparison = sort.sort(copy);
        long after = System.currentTimeMillis();
        System.out.println(sort.getName());
        System.out.print("key Comparison: " + comparison + "    ");
        System.out.println("Running time: " + (after - before) + "ms");
        for (int i = 0; i < n; i++){
            System.out.print(copy[i] + " ");
        }
        System.out.println();
    }
    
    /**
     * copy array a to array b
     */
    private void copyArray(int[]a, int[]b){
        int n = a.length;
        for (int i = 0; i < n; i++){
            b[i] = a[i];
        }
    }

}
