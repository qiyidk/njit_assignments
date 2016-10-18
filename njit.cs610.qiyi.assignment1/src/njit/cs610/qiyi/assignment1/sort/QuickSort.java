package njit.cs610.qiyi.assignment1.sort;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>
 * QuickSort non-recursive three-way quick-sort
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public class QuickSort extends CountableSort {

    public QuickSort(String sortName) {
        super(sortName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void sortArray(int[] a) {
        // TODO Auto-generated method stub
        
        int n = a.length;
        if (n == 1) return;
        
        // use a queue to store the partitions to be done
        // for each partition, we store two integers:start index and end index.
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        queue.add(n - 1);
        while(!queue.isEmpty()){
            int start = queue.remove();
            int end = queue.remove();
            
            // do three-way partition
            // if a[i] < a[pivot] put it on the left section
            // if a[i] = a[pivot] put it on the middle section
            // if a[i] > a[pivot] put it on the right section
            
            if (end - start < 10) {
                // if have less than 10 elements, use insertion sort
                for (int i = start + 1; i <= end; i++){
                    int j = i;// current position
                    while ((j > start) && (compare(a[j] , a[j - 1]) == -1)){
                        //swap and copy array to auxiliary array
                        int t = a[j];
                        a[j] = a[j - 1];
                        a[j - 1] = t;
                        j--;
                    }
                }
                continue;
            }
            // pick a pivot between index "start" and index "end"
            int pivot = (int)((Math.random() * (end - start + 1) + start));
            int v = a[pivot];
            swap(a, start, pivot);
            int l = start; // left bound of middle section
            int r = end; // right bound of middle section
            int i = start + 1; //scan pointer
            while((i <= r)){
                int result = compare(a[i], v); // the result of comparison
                if (result == -1){
                    // if a[i] < v, move current element to the left and move forward the left bound of middle section
                    swap(a, i, l);
                    l++;
                    // after swapping, current element is equal to v,  move forward the pointer
                    i++;
                }
                else if (result == 1){
                    // if a[i] > v, move current element to the right and move backward the right bound of middle section
                    swap(a, i, r);
                    r--;
                }
                else {
                    // a[i] = v, current element belongs to the middle section, just move forward the pointer
                    i++;
                }
            }
            
            if (l - 1 >= start + 1){
                // do a partition on the left side if the length of partition is greater than 1
                queue.add(start);
                queue.add(l - 1);
            }
            if (r + 1 <= end - 1){
                // do a partition on the right side if the length of partition is greater than 1
                queue.add(r + 1);
                queue.add(end);
            }
        }
        return;
    }

    /**
     * swap a[i] and a[j]
     * @param a array
     * @param i array index i
     * @param j array index j
     */
    private void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
