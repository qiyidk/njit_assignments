

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * Console
 * </p>
 *
 * @author qiyi
 * @version 2015-12-3
 */
public class Console {
    private class TestObject{
        private int val;
        public TestObject(int val){
            this.val = val;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BST<Integer, TestObject> bst = new BST<Integer, TestObject>();
        Console console = new Console();
        List<String> commands = console.readFromFile("f.txt");
        for (String command : commands){
            if (command.startsWith("I")){
                String[] keys = command.split(" ");
                for (int i = 1; i < keys.length; i++){
                    TestObject o = console.new TestObject(Integer.parseInt(keys[i]));
                    bst.insert(o.val, o);
                }
                continue;
            }
            if (command.startsWith("D")){
                String[] keys = command.split(" ");
                for (int i = 1; i < keys.length; i++){
                    bst.delete(Integer.parseInt(keys[i]));
                }
                continue;
            }
            if (command.startsWith("P")) bst.printInorderTraverse();
        }
    }
    private List<String> readFromFile(String fileName) {
        File inputFile = new File(fileName);
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
}
