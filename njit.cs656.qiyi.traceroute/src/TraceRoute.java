
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * TraceRoute
 * </p>
 *
 * @author qiyi
 * @version 2015年9月21日
 */
public class TraceRoute {
    
    private String destination;
    private String[] result = new String[36]; // the result the trace route; 
                                              // the maximum number of results is 30 hops plus 6 extra rows 
    private boolean isConnected; // whether is connected to the destination 
    private int n; // the row number of the result
   
    public TraceRoute(String destination){
        this.destination = destination;
        isConnected = testConnection();
        if (isConnected) traceRoute();
    }
    
    /**
     * getFaultedRouters
     * @return if not connected return null
     */
    public List<String> getFaultedRouters(){
        if (!isConnected) return null;
        List<String> routers = new ArrayList<String>();
        int i = 4; // starts with line 5
        while (i < n - 2){ // exclude the last two lines
            char[] chars = result[i].toCharArray();
            boolean needRecord = false; // whether current result need to be recorded
            int nextItem = 2; // the current item which is to be scanned
            for (int j = 0; j < chars.length; j++){
                if (nextItem == 5){
                    if (needRecord) routers.add("The " + (i-3) + "th router:" + result[i].substring(j).trim());
                    i++;
                    break;
                }
                if (chars[j] == '*') {
                    // find faulted router
                    needRecord = true;
                    // move to next item
                    nextItem++;
                    continue;
                }
                if (chars[j] == 's'){
                    // move to next item
                    nextItem++;
                    continue;
                }
                
            }
        }
        return routers;
    }
    
    /**
     * test the connection to destination
     * @param destination
     * @return 
     */
    private boolean testConnection(){
        String command = "ping " + destination;
        InputStream in = null;
        BufferedReader br = null;
        try{
            in = Runtime.getRuntime().exec(command).getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String str;
            while ((str = br.readLine()) != null){
                if (str.contains("TTL=") 
                        || str.contains("时间=")) return true; // use "TTL="、"时间=" as a flag to recognize a successful ping
            }
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
        return false;
    }
    
    
    private void traceRoute(){
        String command = "tracert " + destination;
        InputStream in = null;
        BufferedReader br = null;
        try{
            in = Runtime.getRuntime().exec(command).getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            String str;
            while ((str = br.readLine()) != null){
                System.out.println(str);
                result[n++] = str;
            }
        }
        catch(Exception e){
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
    }
}
