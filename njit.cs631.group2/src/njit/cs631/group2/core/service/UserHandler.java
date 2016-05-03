package njit.cs631.group2.core.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;
import njit.cs631.group2.core.User;
import njit.cs631.group2.core.UserType;
import njit.cs631.group2.core.internal.Cache;
import njit.cs631.group2.core.internal.DataBaseConnection;
import njit.cs631.group2.core.internal.SystemPara;
import njit.cs631.group2.core.internal.UserImpl;

/**
 * <p>
 * UserHandler
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class UserHandler {
    
    /**
     * create user
     * @param userName
     * @param password
     * @param address
     * @param type
     * @return
     */
    public static synchronized User createUser(String userName, String password, String address, UserType type){
        UserImpl u = new UserImpl(Cache.getCache().getNextUserSeqNo(), userName, password, address, type);
        Cache.getCache().addUser(u);
        insertUserToDataBase(u);
        return u;
    }
    
    /**
     * get user by id
     * @param id
     * @return
     */
    public static User getUser(String id){
        return Cache.getCache().getUser(id);
    }
    
    private static void insertUserToDataBase(User user){
        DataBaseConnection db = null;
        Connection connection = null;
        String selectDataBase = "USE " + SystemPara.database;
        SQLTemplate t = new SQLTemplate(SQLKeyword.INSERT, "L_Users");
        t.setValues(new String[]{user.getId(), user.getType().getName(), user.getUserName(), user.getAddress(), user.getPassword()});
        String insertUsers = t.getSQL();
        Statement stat =null;
        try{
            db = new DataBaseConnection();
            connection = db.getConnection();
            stat = connection.createStatement();
            stat.execute(selectDataBase);
            stat.executeUpdate(insertUsers);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
