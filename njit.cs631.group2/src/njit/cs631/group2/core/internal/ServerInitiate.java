package njit.cs631.group2.core.internal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import njit.cs631.group2.core.UserType;


/**
 * <p>
 * Server initiation service
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class ServerInitiate {
    public void init(){
        System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server is starting...");
        DataBaseConnection db = null;
        Connection connection = null;
        String selectDataBase = "USE " + SystemPara.database;
        String getUsers = "Select * from L_User";
        String getBranches = "Select * from Branch";
        Statement stat =null;
        ResultSet rs = null;
        try{
            if (SystemPara.reset){
                // reset data
                System.out.println(new Date(System.currentTimeMillis()).toString() + ": reset database...");
                new DataBaseInitiate().init(); 
            }
            // set cache
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": loading cache...");
            db = new DataBaseConnection();
            connection = db.getConnection();
            stat = connection.createStatement();
            stat.execute(selectDataBase);
            rs = stat.executeQuery(getUsers);
            while(rs.next()){
                String id = rs.getString(1);
                UserType type = Enum.valueOf(UserType.class, rs.getString(2));
                String userName = rs.getString(3);
                String address = rs.getString(4);
                String password = rs.getString(5);
                UserImpl user = new UserImpl(id, userName, password, address, type);
                Cache.getCache().addUser(user);
            }
            rs = stat.executeQuery(getBranches);
            while(rs.next()){
                String LIBID = rs.getString(1);
                String LNAME = rs.getString(2);
                String LLOCATION = rs.getString(3);
                BranchImpl branch = new BranchImpl(LIBID, LNAME, LLOCATION);
                Cache.getCache().addBranch(branch);
            }
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server has started...");
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
