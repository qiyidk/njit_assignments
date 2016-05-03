package njit.cs631.group2.core.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;
import njit.cs631.group2.core.internal.DataBaseConnection;
import njit.cs631.group2.core.internal.SystemPara;

/**
 * <p>
 * SQLHandler
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class SQLHandler {
    public static List<Map<String, String>> execute(SQLTemplate template){
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();
        String selectDataBase = "USE " + SystemPara.database;
        DataBaseConnection db = null;
        Connection connection = null;
        Statement stat =null;
        ResultSet rs = null;
        try {
            db = new DataBaseConnection();
            connection = db.getConnection();
            stat = connection.createStatement();
            stat.execute(selectDataBase);
            if (template.getOper() == SQLKeyword.QUERY){
                rs = stat.executeQuery(template.getSQL());
                ResultSetMetaData metaData = rs.getMetaData();
                int n = metaData.getColumnCount();
                while(rs.next()){
                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 1; i <= n; i++){
                        map.put(metaData.getColumnLabel(i), rs.getString(i));
                    }
                    res.add(map);
                }
            }
            else stat.executeUpdate(template.getSQL());
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            try{
                if (rs != null) rs.close();
                if (stat != null) stat.close();
                if (connection != null) connection.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return res;
    }
}
