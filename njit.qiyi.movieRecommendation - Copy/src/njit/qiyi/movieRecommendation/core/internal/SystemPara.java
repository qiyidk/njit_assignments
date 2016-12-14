package njit.qiyi.movieRecommendation.core.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * <p>
 * SystemPara
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class SystemPara {
    
    public static String connectionURL = null;
    public static String userName = null;
    public static String password = null;
    public static String database = null;
    public static boolean needReCalculate = false;
    public static boolean cross_validation = false;
    
    static{
        Properties p = new Properties();
        InputStream io = null;
        try {
            io = SystemPara.class.getClassLoader().getResourceAsStream("SystemPara.properties");
            p.load(io);
            connectionURL = p.getProperty("connectionURL");
            userName = p.getProperty("userName");
            password = p.getProperty("password");
            database = p.getProperty("database");
            needReCalculate = p.getProperty("needReCalculate").equals("true")? true : false;
            cross_validation = p.getProperty("cross_validation").equals("true")? true : false;

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
