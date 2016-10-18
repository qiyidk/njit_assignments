package njit.cs610.qiyi.assignment1.sort;

/**
 * <p>
 * CountableSort
 * Abstract SuperClass for all sorts whose comparisons need to be counted 
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public abstract class CountableSort{
    private int count = 0; //the number of comparisons
    protected String sortName; // the name of sorting algorithm
    
    public CountableSort(String sortName){
        this.sortName = sortName;
    }
   
    /**
     * sort array a in ascending order
     * the array should be indexed from 0
     * @param a the array to be sorted
     * @return the times of key comparisons
     */
    public int sort(int[] a){
        count = 0; // reset count number
        sortArray(a); // sort array
        return count;
    }
    
    /**
     * sort array a in ascending order
     * the array should be indexed from 0, key comparison can be counted only by calling "compare" method
     * @param a the array to be sorted
     */
    protected abstract void sortArray(int[] a);
    
    /**
     * printSortedResult
     * @param a sorted array
     */
    public void printSortedResult(int[] a){
        int n = a.length;
        for (int i = 0; i < n; i++){
            System.out.print(a[i]+" ");
        }
    }
    
    /**
     * Compare two integers i,j
     * All key comparisons should be made by calling this method in order to be counted
     * @return the result of key comparison
     * if i =j return 0; if i > j return 1; if i < j return -1 
     */
    protected int compare(int i, int j){
        count++; //count key comparisons
        if (i == j) return 0;
        else if (i > j) return 1;
        else return -1;
    }
    
    
    public String getName(){
        return sortName;
    }
}
