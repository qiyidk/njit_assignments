package njit.cs610.qiyi.assignment2.select;

/**
 * <p>
 * Select algorithm based on quick sort
 * </p>
 *
 * @author qiyi
 * @version 2015-10-25
 */
public class SortSelect extends CountableSelect{

    public SortSelect(String selectName) {
        super(selectName);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected int selectElement(int[] a, int k) {
        // TODO Auto-generated method stub
        quickSort(a, 0, a.length - 1);
        return a[k - 1];
    }
    
    /**
     * quickSort
     * @param a array
     * @param start the start index of sub array
     * @param end the end index of sub array
     */
    private void quickSort(int[]a, int start, int end){
        if (end <= start) return;
        int l = start + 1; // left pointer
        int r = end; // right pointer
        int pivot = (int)(Math.random() * (end - start + 1) + start);
        int v = a[pivot];
        swap(a, pivot, start);
        while (l <= r){
            while ((l <= r) && (compare(a[l], v) <= 0)) l++;
            while ((l <= r) && (compare(a[r], v) >= 0)) r--;
            if (l < r){
                swap(a, l, r);
                l++;
                r--;
            }
        }
        swap(a, start, r); // put pivot to the right position
        quickSort(a, start, r - 1);
        quickSort(a, r + 1, end);
    }
}
