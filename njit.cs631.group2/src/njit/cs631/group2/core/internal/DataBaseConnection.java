package njit.cs631.group2.core.internal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 * <p>
 * DataBaseConnection
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class DataBaseConnection {
   
    private Connection connection = null;
    
    public DataBaseConnection(){       
        String connectionURL = "jdbc:mysql://" + SystemPara.connectionURL;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            connection =  DriverManager.getConnection(connectionURL + "?rewriteBatchedStatements=true", SystemPara.userName, SystemPara.password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
}
