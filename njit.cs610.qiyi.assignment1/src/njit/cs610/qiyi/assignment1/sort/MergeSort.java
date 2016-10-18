package njit.cs610.qiyi.assignment1.sort;

/**
 * <p>
 * MergeSort
 * non-recursive merge-sort with optimization
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public class MergeSort extends CountableSort{
    
    public MergeSort(String sortName) {
        super(sortName);
        // TODO Auto-generated constructor stub
    }
    private int[] aux; //auxiliary to store intermediate sorting result

    @Override
    public void sortArray(int[] a) {
        // TODO Auto-generated method stub
        int n = a.length;
        if (n <= 1) return;
        aux = new int[n];
        int width;// the width of each sub-array to be merged
        width = 8; // for the length of sub-array to be merged <= 8, use insertion sort
        for (int i = 0; i < n; i = i + width * 2){
            int start = i; // the start of the array
            int end = Math.min(i + width * 2 - 1, n - 1); // the end of the array
            
            // insertion sort
            for (int j = start + 1; j <= end; j++){
                int p = j; // current position
                while ((p > start) && (compare(a[p] , a[p - 1]) == -1)){
                    //swap and copy array to auxiliary array
                    int t = a[p];
                    a[p] = a[p - 1];
                    a[p - 1] = t;
                    p--;
                }
            }
        }
        width = width * 2;
        int[] t = null; // a temporary array for switching original array and auxiliary array
        int[] original = a; // a pointer to the original array
        for (;width < n; width = width * 2){
            for (int i = 0; i < n; i = i + width * 2){
                if (n - i <= width) continue; // don't need merge
                int end = Math.min(i + width * 2 - 1, n - 1); // the end of the array
                merge(a, aux, i, i + width, end);
            }
            // switch roles of original array and auxiliary array so that we don't need to copy array each time
            t = a;
            a = aux;
            aux = t;
        }
        if (a != original) 
            copyArray(a, original); // if the final result is not stored in the original array, copy data from array a to the original array
        return;
    }
    
    /**
     * merge two sub-arrays, the result is stored in the auxiliary
     * the first array starts from "start" to "middle" - 1, the second array starts from middle to end
     * @param a the original array
     * @param aux the auxiliary array. Note: the merge result will store in the auxiliary array
     * @param start
     * @param middle
     * @param end
     */
    private void merge(int[] a, int aux[], int start, int middle, int end){
        
        int i0 = start; // the index of current element to be scanned in first sub-array
        int i1 = middle; // the index of current element to be scanned in second sub-array
        
        if (compare(a[middle - 1], a[middle]) == -1) {
            // already in sorted order
            // copy array directly
            for (int i = start; i <= end; i++){
                aux[i] = a[i];
            }
            return;
        }

        for (int j = start; j <= end; j++){
            if ((i0 < middle) &&
                    ((i1 > end) || (compare(a[i0], a[i1]) == -1))){
                // a[i0] is the smallest element currently
                aux[j] = a[i0]; 
                i0++;
            }
            else{
                // otherwise, a[i1] is the smallest element currently
                aux[j] = a[i1];
                i1++;
            }
        }
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
