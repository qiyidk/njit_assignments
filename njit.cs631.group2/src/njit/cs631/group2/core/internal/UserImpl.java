package njit.cs631.group2.core.internal;

import njit.cs631.group2.core.User;
import njit.cs631.group2.core.UserType;

/**
 * <p>
 * User
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class UserImpl implements User{
    private String id;
    private String userName;
    private String password;
    private String address;
    private UserType type;
    
    public UserImpl(String id, String userName, String password, String address, UserType type){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }


}
