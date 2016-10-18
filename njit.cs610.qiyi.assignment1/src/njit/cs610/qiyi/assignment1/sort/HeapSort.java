package njit.cs610.qiyi.assignment1.sort;

/**
 * <p>
 * Heapsort
 * non-recursive heap-sort
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public class HeapSort extends CountableSort{

    public HeapSort(String sortName) {
        super(sortName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void sortArray(int[] a) {
        // TODO Auto-generated method stub
        int n = a.length;
        buildHeap(a);
        for (int i = n - 1; i >= 0; i--){
            // swap a[i] and a[0], put the largest element(a[0]) to the end of array
            swap(a, 0, i);
            pushDown(a, 0, i);
        }
    }
    
    private void buildHeap(int[] a){
        int n = a.length;
        for(int i = n/2-1; i >= 0; i--){
            pushDown(a, i, n);
        }
    }
    
    /**
     * pushDown Max-heap
     * note: the array's index starts from 0, so the left child of node i is 2 * i + 1
     * @param a array
     * @param r current push-down node r
     * @param n the number of elements in the array
     */
    private void pushDown(int[] a, int r, int n){
        while ((n - 1) >= (2 * r + 1)){
            int max = 0; // the maximum children
            // find the maximum children
            if ((n - 1 == 2 * r + 1) 
                    || (compare(a[2 * r + 1], a[2 * r + 2]) == 1)){
                max = 2 * r + 1;
            }
            else max = 2 * r + 2;
            if (compare(a[r], a[max]) == -1){
                // if a[r] < a[max] swap
                swap(a, r, max);
                r = max; // push down
            }
            else return;
        }
    }
    
    /**
     * swap a[i] and a[j]
     * @param a array
     * @param i array index i
     * @param j array index j
     */
    private void swap(int[] a, int i, int j){
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
