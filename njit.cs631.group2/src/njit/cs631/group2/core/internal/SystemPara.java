package njit.cs631.group2.core.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * <p>
 * SystemPara
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class SystemPara {
    
    public static String connectionURL = null;
    public static String userName = null;
    public static String password = null;
    public static boolean reset = false;
    public static String database = null;
    public static boolean debug = false;
    public static boolean redirect = false;
    
    static{
        Properties p = new Properties();
        InputStream io = null;
        try {
            io = SystemPara.class.getClassLoader().getResourceAsStream("SystemPara.properties");
            p.load(io);
            connectionURL = p.getProperty("connectionURL");
            userName = p.getProperty("userName");
            password = p.getProperty("password");
            reset = p.getProperty("reset").equals("true")? true : false;
            database = p.getProperty("database");
            debug = p.getProperty("debug").equals("true")? true : false;
            redirect = p.getProperty("redirect").equals("true")? true : false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (io != null){
                try {
                    io.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
