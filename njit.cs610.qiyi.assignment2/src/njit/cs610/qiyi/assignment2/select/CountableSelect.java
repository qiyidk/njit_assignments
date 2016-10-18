package njit.cs610.qiyi.assignment2.select;

/**
 * <p>
 * CountableSelect
 * The super class for all select algorithms that can be counted
 * </p>
 *
 * @author qiyi
 * @version 2015-10-24
 */
public abstract class CountableSelect {
    private int count = 0; //the number of comparisons
    protected String selectName; // the name of select algorithm
    
    public CountableSelect(String selectName){
        this.selectName = selectName;
    }
    
    /**
     * select the Kth smallest element in an array
     * the array should be indexed from 0
     * @param a the array to be selected
     * @param k the kth smallest element to be selected
     * @return the Kth smallest element in an array
     */
    public int select(int[] a, int k){
        count = 0; // reset count number
        return selectElement(a, k);
    }
    
    /**
     * select the Kth smallest element in an array
     * the array should be indexed from 0, key comparison can be counted only by calling "compare" method
     * @param a the array to be selected
     * @param k the kth smallest element to be selected
     * @return the Kth smallest element in an array
     */
    protected abstract int selectElement(int[] a, int k);
    
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
    
    /**
     * swap a[i] and a[j]
     * @param a array
     * @param i array index i
     * @param j array index j
     */
    protected void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    public String getName(){
        return selectName;
    }
    
    /**
     * get the count of key comparisons during the select operation.
     * Note: this method must be called after calling select
     * @return the count of key comparisons
     */
    public int getCount(){
        return count;
    }
}
