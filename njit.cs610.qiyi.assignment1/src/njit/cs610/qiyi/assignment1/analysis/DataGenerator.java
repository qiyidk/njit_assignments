package njit.cs610.qiyi.assignment1.analysis;

/**
 * <p>
 * DataGenerator
 * </p>
 *
 * @author qiyi
 * @version 2015-10-11
 */
public class DataGenerator {
    
    /**
     * generated randomized integer data between -100000 to 100000
     * @param size data size
     * @return integer array data indexed from 0
     */
    public static int[] getRandomizedData(int size){
        int[] data = new int[size];
        for (int i = 0; i < size; i++){
            data[i] = 100000 - (int)(Math.random() * 200001);
        }
        return data;
    } 
    
    /**
     * generated ascending ordered integer data with differences from 0 to 99
     * @param size data size
     * @return integer array data indexed from 0
     */
    public static int[] getAscendingData(int size){
        int[] data = new int[size];
        int base = (int)(Math.random() * 1000);
        for (int i = 0; i < size; i++){
            int difference = (int)(Math.random() * 100);
            data[i] = base + difference;
            base = data[i];
        }
        return data;
    }
    
    /**
     * generated descending ordered integer data with differences from 0 to 99
     * @param size data size
     * @return integer array data indexed from 0
     */
    public static int[] getDescendingData(int size){
        int[] data = new int[size];
        int base = (int)(Math.random() * 1000 + 10000);
        for (int i = 0; i < size; i++){
            int difference = (int)(Math.random() * 100);
            data[i] = base - difference;
            base = data[i];
        }
        return data;
    }
}
