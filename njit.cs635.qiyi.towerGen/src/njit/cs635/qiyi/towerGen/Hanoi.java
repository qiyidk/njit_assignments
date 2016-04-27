package njit.cs635.qiyi.towerGen;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Hanoi
 * </p>
 *
 * @author qiyi
 * @version 2015-12-7
 */
public class Hanoi {
    //the disks should be provided in descending size order and use A,B,C to represent the three towers
    private String initiateDisks; 
    private String targetDisks;
    public Hanoi(String initiateDisks, String targetDisks){
        checkArguments(initiateDisks, targetDisks);
        this.initiateDisks = initiateDisks;
        this.targetDisks = targetDisks;
    }
    public List<String> getPath(){
        List<String> paths = new ArrayList<String>();
        if (initiateDisks.length() == 0) return paths;
        getPath(initiateDisks, targetDisks, paths);
        return paths;
    }
    private void getPath(String initiateDisks, String targetDisks, List<String> paths){
        if (initiateDisks.length() == 0) return;
        if (initiateDisks.charAt(0) == targetDisks.charAt(0)){
            getPath(initiateDisks.substring(1), targetDisks.substring(1), paths);
            return;
        }
        else{
            //step 1: empty the tower targetDisks.charAt(0)
            //get intermediate tower
            char c = (char)(initiateDisks.charAt(0) ^ targetDisks.charAt(0) ^ 'A' ^ 'B' ^ 'C');
            char[] newChars = new char[initiateDisks.length() - 1];
            for (int i = 0; i < newChars.length; i++) newChars[i] = c;
            String intermediateTarget = new String(newChars);
            getPath(initiateDisks.substring(1), intermediateTarget, paths);
            //step 2: move the biggest disk to the tower targetDisks.charAt(0) 
            String path = initiateDisks.charAt(0) + "->" + targetDisks.charAt(0);
            paths.add(path);
            //step 3: move the remain disks to the final target
            getPath(intermediateTarget, targetDisks.substring(1), paths);
        }  
    }
    private void checkArguments(String initiateDisks, String targetDisks){
        if ((initiateDisks == null) || (targetDisks == null)) throw new IllegalArgumentException("The arguments cannot be null");
        if (initiateDisks.length() != targetDisks.length()) throw new IllegalArgumentException("The lengths of initiateDisks and targetDisks are mismatched");
        int n = initiateDisks.length();
        for (int i = 0; i < n; i++){
            if ((initiateDisks.charAt(i)) > 'C' || 
                    (initiateDisks.charAt(i) < 'A')) 
                throw new IllegalArgumentException("The initiateDisks can only contains chars from a to c");
        }
        for (int i = 0; i < n; i++){
            if ((targetDisks.charAt(i)) > 'C' || 
                    (targetDisks.charAt(i) < 'A')) 
                throw new IllegalArgumentException("The targetDisks can only contains chars from a to c");
        }
    }
}
