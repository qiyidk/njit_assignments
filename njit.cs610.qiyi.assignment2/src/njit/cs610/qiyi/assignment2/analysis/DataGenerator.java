package njit.cs610.qiyi.assignment2.analysis;

/**
 * <p>
 * DataGenerator
 * </p>
 *
 * @author qiyi
 * @version 2015-10-27
 */
public class DataGenerator {
    
    /**
     * generated randomized integer data between 1 to 1000000
     * @param size data size
     * @return integer array data indexed from 0
     */
    public static int[] getRandomizedData(int size){
        int[] data = new int[size];
        for (int i = 0; i < size; i++){
            data[i] = (int)(Math.random() * 1000000) + 1;
        }
        return data;
    } 
}
