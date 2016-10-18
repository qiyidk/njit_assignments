package njit.cs610.qiyi.assignment1.sort;

/**
 * <p>
 * RadixSort
 * </p>
 *
 * @author qiyi
 * @version 2015-11-9
 */
public class RadixSort extends CountableSort {

    public RadixSort(String sortName) {
        super(sortName);
        // TODO Auto-generated constructor stub
    }
    private void radixSort(int[] a, int start, int end, int bytes, int[] aux){
        int radix = 256;
        int[] count = new int[radix + 1];
        int offset = (4 - bytes) * 8;
        int mask = 0xff << offset;
        for (int i = start; i <= end; i++){
            count[((aux[i] & mask) >>> offset) + 1]++;
        }
        for (int i = 1; i < radix; i++){
            count[i] += count[i - 1];
        }
        for (int i = start; i <= end; i++){
            int num = (aux[i] & mask) >>> offset;
            a[start + count[num]] = aux[i];
            count[num]++;
        }
    }

    @Override
    protected void sortArray(int[] a) {
        // TODO Auto-generated method stub
        int[] aux = new int[a.length];
        int p = a.length - 1; // pointer for positive number
        int n = 0; // pointer for negative number
        for (int i = 0; i < a.length; i++){
            if (a[i] >= 0){
                aux[p] = a[i];
                p--;
            }
            else{
                aux[n] = a[i];
                n++;
            }
        }
        
        boolean inOriginalArray = true; // is the final result storing in the original array
        for (int i = 4; i >= 1; i--){
            if (inOriginalArray){
                radixSort(a, 0, n - 1, i, aux); //sort negative numbers
                radixSort(a, n, a.length - 1, i, aux); // sort positive numbers
            }
            else{
                // switch role so that we don't need to copy array from a to aux
                radixSort(aux, 0, n - 1, i, a); //sort negative numbers
                radixSort(aux, n, a.length - 1, i, a); // sort positive numbers
            }
            inOriginalArray = !inOriginalArray;
        }
        if (inOriginalArray){
            // The result is in the auxiliary array, copy array to the original array
            for (int i = 0; i < a.length; i++){
                a[i] = aux[i];
            }
        }
    }
}
