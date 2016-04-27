package njit.cs635.qiyi.towerGen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * <p>
 * Simulator
 * </p>
 *
 * @author qiyi
 * @version 2015-12-7
 */
public class Simulator extends JFrame{
    
    private static final long serialVersionUID = 1L;
    private Stack<Integer> a = new Stack<Integer>(); // store disks in tower a 
    private Stack<Integer> b = new Stack<Integer>(); // store disks in tower b 
    private Stack<Integer> c = new Stack<Integer>(); // store disks in tower c 
    private Map<Integer, JLabel> labelMap = new HashMap<Integer, JLabel>();
    private String init;
    private String target;
    private int interval; // the interval of each move in millisecond 

    public Simulator(String init, String target, int interval){
        super();
        this.setSize(600, 400);
        this.getContentPane().setLayout(null);
        this.setTitle("Hanoi");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);;
        this.setVisible(true);
        this.init = init;
        this.target = target;
        this.interval = interval;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String initiateDisks = "ABCAB";
        String targetDisks =   "BCAAB";
        Simulator s = new Simulator(initiateDisks, targetDisks, 1000);
        s.simulate();
    }
    
    public void simulate(){
        Hanoi hanoi = new Hanoi(init, target);
        List<String> paths = hanoi.getPath();
        int n = init.length();
        for(int i = 0; i < n; i++){
            if (init.charAt(i) == 'A') a.push(n - 1 - i);
            else if (init.charAt(i) == 'B') b.push(n - 1 - i);
            else c.push(n - 1 - i);
        }
        init();// initiate the original disks
        try {
            repaint();
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        for (String path : paths){
            System.out.println(path);
            int move = 0;
            switch(path.charAt(0)){
            case 'A':
                move = a.pop(); break;
            case 'B':
                move = b.pop(); break;
            case 'C':
                move = c.pop(); break;
            default : break;
            }
            // move the disk out of the original tower.
            remove(labelMap.get(move));
            
            // move the disk to the target tower
            switch(path.charAt(3)){
            case 'A':
                a.push(move); drawLabel(20, 300 - a.size() * 10, move); break;
            case 'B':
                b.push(move); drawLabel(200, 300 - b.size() * 10, move); break;
            case 'C':
                c.push(move); drawLabel(380, 300 - c.size() * 10, move); break;
            default : break;
            }
            try {
                repaint();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void init(){
        // draw title
        JLabel ilabel = new JLabel();
        ilabel.setBounds(20, 20, 300, 20);
        ilabel.setText("init:        " + init);
        this.add(ilabel);
        JLabel tlabel = new JLabel();
        tlabel.setBounds(20, 40, 300, 20);
        tlabel.setText("target:  " + target);
        this.add(tlabel);
        JLabel labelA = new JLabel();
        labelA.setBounds(20, 100, 300, 20);
        labelA.setText("A");
        this.add(labelA);
        JLabel labelB = new JLabel();
        labelB.setBounds(200, 100, 300, 20);
        labelB.setText("B");
        this.add(labelB);
        JLabel labelC = new JLabel();
        labelC.setBounds(380, 100, 300, 20);
        labelC.setText("C");
        this.add(labelC);
        
        // draw tower
        Integer[] arrayA = new Integer[a.size()];
        a.toArray(arrayA);
        Integer[] arrayB = new Integer[b.size()];
        b.toArray(arrayB);
        Integer[] arrayC = new Integer[c.size()];
        c.toArray(arrayC);
        int maxL = Math.max(Math.max(arrayA.length, arrayB.length), arrayC.length);
        for (int i = 0; i < maxL; i++){
            if (i < arrayA.length) drawLabel(20, 290 - i * 10, arrayA[i]);
            if (i < arrayB.length) drawLabel(200, 290 - i * 10, arrayB[i]);
            if (i < arrayC.length) drawLabel(380, 290 - i * 10, arrayC[i]);
        }
    }
    private void drawLabel(int rowIndex, int columnIndex, int num){
        JLabel label = new JLabel();
        label.setBounds(rowIndex, columnIndex, 300, 20);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i <= num; i++) text.append("0");
        label.setText(text.toString());
        this.add(label);
        labelMap.put(num, label);
    }
}
