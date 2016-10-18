import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * StringSort
 * </p>
 *
 * @author qiyi
 * @version 2015-11-18
 */
public class StringSort {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        StringSort sort = new StringSort();
        String inputFileName = "f.txt";
        String outputFileName = "g.txt";
        if (args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        }
        //get input strings
        List<String> list = sort.readFromFile(inputFileName); 
        int size = list.size();
        char[][] chars = new char[size][21];
        for (int i = 0; i < size; i++) {
            String s = list.get(i);
            for (int j = 0; j < 21; j++) {
                if (j > s.length() - 1)
                    chars[i][j] = 0;
                else
                    chars[i][j] = s.charAt(j);
            }
        }
        int[] p1 = new int[size]; //position array for countSort-based radix sort 
        int[] p2 = new int[size]; //position array for bucketSort-based radix sort 
        for (int i = 0; i < size; i++) {
            p1[i] = i;
            p2[i] = i;
        }
        long time1 = System.currentTimeMillis();
        sort.radixSort(chars, p1, true);
        long time2 = System.currentTimeMillis();
        sort.radixSort(chars, p2, false);
        long time3 = System.currentTimeMillis();
        System.out.println(
                "CountSort-based radix sort: " + (time2 - time1) + "ms");
        for (int i = 0; i < size && i < 10; i++) {
            System.out.print(new String(chars[p1[i]]).trim() + "  ");
        }
        System.out.println("......");
        System.out.println(
                "BucketSort-based radix sort: " + (time3 - time2) + "ms");
        for (int i = 0; i < size && i < 10; i++) {
            System.out.print(new String(chars[p2[i]]).trim() + "  ");
        }
        System.out.print("......");
        String[] results = new String[size];
        for (int i = 0; i < size; i++) {
            results[i] = new String(chars[p1[i]]);
        }
        sort.writeIntoFile(results, outputFileName);
    }

    /**
     * radixSort
     * 
     * @param a input array
     * @param p position array
     * @param isCountSort is using count sort, if false, using bucket sort
     */
    private void radixSort(char[][] a, int[] p, boolean isCountSort) {
        // use count sort
        int n = p.length;
        if (isCountSort) {
            int[] aux = new int[n];
            for (int i = 0; i < n; i++) {
                aux[i] = p[i];
            }
            boolean inOriginalArray = true; // is the final result storing in the original array
            for (int i = 20; i >= 0; i--) {
                if (inOriginalArray) {
                    countSort(a, p, i, aux);
                } else {
                    // switch role so that we don't need to copy array from a to aux
                    countSort(a, aux, i, p);
                }
                inOriginalArray = !inOriginalArray;
            }
            if (inOriginalArray) {
                // The result is in the auxiliary array, copy array to the original array
                for (int i = 0; i < n; i++) {
                    p[i] = aux[i];
                }
            }
        }
        // bucket sort
        else {
            for (int i = 20; i >= 0; i--) {
                bucketSort(a, p, i);
            }
        }

    }

    /**
     * countSort: count sort the input String by the "i"th character
     * 
     * @param a input array
     * @param p position array
     * @param i "i"th character
     * @param aux auxiliary array
     */
    private void countSort(char[][] a, int[] p, int i, int[] aux) {
        int radix = 27; // A-Z, ""
        int n = p.length;
        int[] count = new int[radix + 1];
        int offset = 'A' - 1;
        for (int j = 0; j < n; j++) {
            count[charAt(a, aux[j], i) - offset + 1]++;
        }
        for (int j = 1; j < radix; j++) {
            count[j] += count[j - 1];
        }
        for (int j = 0; j < n; j++) {
            int num = charAt(a, aux[j], i) - offset;
            p[count[num]] = aux[j];
            count[num]++;
        }
    }

    /**
     * bucketSort: bucket sort the input String by the "i"th character
     * 
     * @param a input array
     * @param p position array
     * @param i "i"th character
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void bucketSort(char[][] a, int[] p, int i) {
        int radix = 27; // A-Z, ""
        int n = p.length;
        int offset = 'A' - 1;
        List[] buckets = new List[radix];
        for (int j = 0; j < radix; j++) {
            List<Integer> bucket = new ArrayList<Integer>();
            buckets[j] = bucket;
        }
        for (int j = 0; j < n; j++) {
            buckets[charAt(a, p[j], i) - offset].add(p[j]);
        }
        int index = 0;
        for (int j = 0; j < radix; j++) {
            for (int s : (List<Integer>) buckets[j]) {
                p[index] = s;
                index++;
            }
        }
    }

    /**
     * return the char value of the "s"th string's "i"th char
     * 
     * @param a input array
     * @param s "s"th string
     * @param i "i"th char
     * @return
     */
    private char charAt(char[][] a, int s, int i) {
        char c = a[s][i];
        if (c == 0)
            return 'A' - 1;
        else
            return c;
    }

    private List<String> readFromFile(String fileName) {
        File inputFile = new File(fileName);
        // if input file doesn't exist, generate one
        if (!inputFile.exists())
            DataGenerator.generateStrings(1000, fileName);
        BufferedReader in = null;
        FileReader fin = null;
        String str = null;
        List<String> list = new ArrayList<String>();
        try {
            fin = new FileReader(inputFile);
            in = new BufferedReader(fin);
            while (((str = in.readLine()) != null)
                    && (str.trim().length() != 0)) {
                list.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (fin != null)
                    fin.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    private void writeIntoFile(String[] strs, String fileName) {
        File outputFile = new File(fileName);
        if (outputFile.exists()) outputFile.delete();
        BufferedWriter out = null;
        FileWriter fout = null;
        try{
            outputFile.createNewFile();        
            fout = new FileWriter(outputFile);        
            out = new BufferedWriter(fout);
            for (int i = 0; i < strs.length; i++){
                out.write(strs[i] + "\r\n");   
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if (out != null) out.close();
                if (fout != null) fout.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } 
    }
}
