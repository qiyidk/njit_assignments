package njit.cs631.group2.core;


/**
 * <p>
 * UserType
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public enum UserType {
    
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    SENIOR_CITIZEN("SENIOR_CITIZEN"),
    STAFF("STAFF");
    
    private String name;
    private UserType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
