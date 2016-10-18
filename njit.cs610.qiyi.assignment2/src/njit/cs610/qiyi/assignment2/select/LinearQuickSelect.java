package njit.cs610.qiyi.assignment2.select;

/**
 * <p>
 * Select algorithm based on LinearQuickSelect
 * </p>
 *
 * @author qiyi
 * @version 2015-10-25
 */
public class LinearQuickSelect extends QuickSelect {

    public LinearQuickSelect(String selectName) {
        super(selectName);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected int findPivot(int[] a, int start, int end){
        int rownum = 0; // the number of row
        for (int i = start; i <= end; i = i + 5){
            // get a row of 5-elements from a[i] to a[i + 4]
            int lastElement = (end >= i + 4? i + 4: end);
            insertionSort(a, i, lastElement);
            // put each median in the front of the original array
            int median = (lastElement == end ? (lastElement - i + 1) / 2 + 1: 3); // the index of median
            swap(a, start + rownum , i + median - 1);
            rownum++;
        }
        
        // find the median of medians of rows
        return findElement(a, start, start + rownum - 1, rownum / 2);
    }
}
