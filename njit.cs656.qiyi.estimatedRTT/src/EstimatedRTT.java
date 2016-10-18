import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 * EstimatedRTT
 * </p>
 *
 * @author qiyi
 * @version 2015年10月23日
 */
public class EstimatedRTT {

    private int estimatedRTT;
    private int devRTT;
    private int timeoutInterval;
    private String server;
    
    public EstimatedRTT(String server){
        this.server = server;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int n = 100; // the total number of experiments
        String server = "www.baidu.com";
        EstimatedRTT rtt = new EstimatedRTT(server);
        rtt.calculateEstimatedRTT(n);
    }
    
    /**
     * calculate and draw estimatedRTT
     * @param n  the total number of experiments 
     */
    public void calculateEstimatedRTT(int n){
        // initiate
        int maxX = n; // the maximum horizontal coordinate
        int maxY = 500; // the maximum vertical coordinate
        // set coordinate
        Draw d = new Draw();
        d.setCanvasSize(800, 600);
        d.setXscale(-10, maxX);
        d.setYscale(-10, maxY);
        // draw coordinate axis
        d.setPenColor(); //black
        d.line(0, 0, 0, maxY);
        d.line(0, 0, maxX, 0);
        d.text(n - 20, 0.9 * maxY, "blue: sample RTT");
        d.text(n - 20, 0.8 * maxY, "green: estimated RTT");
        d.text(n - 20, 0.7 * maxY, "red: timeout Interval");
        // draw vertical scale
        d.text(0, maxY, String.valueOf(maxY));
        d.text(0, maxY * 0.8, String.valueOf(maxY * 0.8));
        d.text(0, maxY * 0.6, String.valueOf(maxY * 0.6));
        d.text(0, maxY * 0.4, String.valueOf(maxY * 0.4));
        d.text(0, maxY * 0.2, String.valueOf(maxY * 0.2));
        
        // calculate and draw the first point
        int sampleRTT = getSampleRTT();
        devRTT = 0;
        estimatedRTT = sampleRTT;
        timeoutInterval = estimatedRTT;
        d.setPenColor(0, 0, 255); // blue
        d.point(0, sampleRTT);
        d.setPenColor(0, 255, 0); // green
        d.point(0, estimatedRTT);
        d.setPenColor(255, 0, 0); // red
        d.point(0, timeoutInterval);
        int lastSampleRTT = 0; // use to track to the last point to draw a line
        int lastEstimatedRTT = 0; // use to track to the last point to draw a line
        int lastTimeoutInterval = 0; // use to track to the last point to draw a line
        
        for (int i = 1; i < n; i++){
            lastSampleRTT = sampleRTT;
            sampleRTT = getSampleRTT();
            lastEstimatedRTT = estimatedRTT;
            estimatedRTT = (int) ((1 - 0.125) * estimatedRTT + 0.125 * sampleRTT);
            devRTT = (int) ((1 - 0.25) * devRTT + 0.25 * Math.abs(estimatedRTT - sampleRTT));
            lastTimeoutInterval = timeoutInterval;
            timeoutInterval = estimatedRTT + 4 * devRTT;
            draw(d, i, sampleRTT, lastSampleRTT, lastEstimatedRTT, lastTimeoutInterval);
        }
    }
    
    private void draw(Draw d, int i, int sampleRTT, int lastSampleRTT, int lastEstimatedRTT, int lastTimeoutInterval){
        d.setPenColor(0, 0, 255); // blue
        d.point(i, sampleRTT);
        d.line(i - 1, lastSampleRTT, i, sampleRTT);
        d.setPenColor(0, 255, 0); // green
        d.point(i, estimatedRTT);
        d.line(i - 1, lastEstimatedRTT, i, estimatedRTT);
        d.setPenColor(255, 0, 0); // red
        d.point(i, timeoutInterval);
        d.line(i - 1, lastTimeoutInterval, i, timeoutInterval);
    }
    private int getSampleRTT(){
        int sampleRTT = 0;
        String command = "ping " + server;
        InputStream in = null;
        BufferedReader br = null;
        try{
            in = Runtime.getRuntime().exec(command).getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String str;
            while ((str = br.readLine()) != null){
                if (str.contains("average") || str.contains("平均")){
                    break;
                }
            }
            char[] chars = str.toCharArray(); // str stores the result line
            int begin = 0;
            int end = 0; 
            for (int i = chars.length - 1; i >= 0; i--){
                if (chars[i] == 'm') end = i;
                if (chars[i] == '=') {
                    begin = i + 2;
                    break;
                }
            }
            return Integer.parseInt(str.substring(begin, end));
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (br != null){
                try{
                    br.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return sampleRTT;
    }

}
