import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * <p>
 * DataGenerator
 * </p>
 *
 * @author qiyi
 * @version 2015-11-18
 */
public class DataGenerator {
    
    /**
     * generated randomized String data and write them into a file
     * @param size data size
     * @param name file name
     */
    public static void generateStrings(int size, String name){
        File file = new File(name);
        if (file.exists()) file.delete();
        BufferedWriter out = null;
        FileWriter fout = null;
        try{
            file.createNewFile();        
            fout = new FileWriter(file);        
            out = new BufferedWriter(fout);
            for (int i = 0; i < size; i++){
                int length = (int) (Math.random() * 21 + 1); // the length of current string (1-21)
                char[] chars = new char[length];
                for (int j = 0; j < length; j++){
                    char c = (char)('A' + (int)(Math.random() * 26));
                    chars[j] = c;
                }
                out.write(new String(chars) + "\r\n");   
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
