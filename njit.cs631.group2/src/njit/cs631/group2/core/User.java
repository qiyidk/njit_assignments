package njit.cs631.group2.core;

/**
 * <p>
 * User
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public interface User {
    
    public String getId();
    public String getUserName();
    public String getPassword();
    public String getAddress();
    public UserType getType();
}
