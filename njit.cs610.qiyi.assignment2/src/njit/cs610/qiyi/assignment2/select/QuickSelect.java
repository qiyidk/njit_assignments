package njit.cs610.qiyi.assignment2.select;

/**
 * <p>
 * Select algorithm based on QuickSelect
 * </p>
 *
 * @author qiyi
 * @version 2015-10-24
 */
public class QuickSelect extends CountableSelect {

    public QuickSelect(String selectName) {
        super(selectName);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected int selectElement(int[] a, int k) {
        // TODO Auto-generated method stub
        return a[findElement(a, 0, a.length - 1, k)];
    }
    
    /**
     * find the Kth smallest element in sub array
     * @param a array
     * @param start the start index of sub array
     * @param end the end index of sub array
     * @param k the kth smallest element in the sub array
     * @return the index of the found element
     */
    protected int findElement(int[] a, int start, int end, int k){
        
        if (end - start + 1 < 25){
            // if the number of elements less than 25, use insertion sort instead
            insertionSort(a, start, end);
            return start + k - 1;
        }
        // three-way partition
        int pivot = findPivot(a, start, end);
        int v = a[pivot];
        swap(a, start, pivot);
        int i = start + 1; // scan pointer
        int l = 0; // number of element in the left side
        int r = 0; // number of element in the right side
        int m = 1; // number of element in the middle side
        while (i <= end - r){
            int result = compare(a[i], v); 
            if (result == -1){
                // if current element is less than v, swap the current element with the previous one and increment count of left side
                swap(a, i, i - m);
                l++;
                // move forward the scanner
                i++;
            }
            else if (result == 1){
                // if current element is greater than v, move the element to the "greater" section and increment count of right side
                swap(a, i, end - r);
                r++;
            }
            else{
                // if equal increment count of middle side
                m++;
                // move forward the scanner
                i++;
            }
        }
        if (k <= l){
            // go left side
            return findElement(a, start, start + l - 1, k);
        }
        else if (k <= l + m){
            return start + k - 1;
        }
        else{
            // go right side
            return findElement(a, end - r + 1, end, k - l - m);
        }
    }
    
    /**
     * find pivot in the sub array 
     * @param a array 
     * @param start the start index of sub array
     * @param end the end index of sub array
     * @return the index of the pivot
     */
    protected int findPivot(int[] a, int start, int end){
        return (int) (Math.random() * (end - start + 1)) + start;
    }
    
    /**
     * insertion sort
     * @param a array to be sorted
     * @param start the start index of sub array
     * @param end the end index of sub array
     */
    protected void insertionSort(int[] a, int start, int end){
        for (int i = start + 1; i <= end; i++){
            for (int j = i; (j > start) && (compare(a[j], a[j - 1]) == -1); j--){
                swap(a, j, j - 1);
            }
        }
    }
}
