package njit.cs631.group2.core.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import njit.cs631.group2.core.Branch;
import njit.cs631.group2.core.User;

/**
 * <p>
 * Cache
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class Cache {
    
    private static final Cache cache = new Cache();

    private Cache(){
        
    }
    
    public static Cache getCache(){
        return cache;
    }
    
    private HashMap<String, User> users = new HashMap<String, User>();
    private HashMap<String, Branch> branches = new HashMap<String, Branch>();
    private List<String> sql = new ArrayList<String>();
    
    public User getUser(String id){
        return users.get(id);
    }
    public void addUser(User user){
        users.put(user.getId(), user);
    }
    
    public void addBranch(Branch b){
        branches.put(b.getLIBID(), b);
    }
    public Collection<Branch> getBranches(){
        return branches.values();
    }
    
    public Branch getBranch(String LibId){
        return branches.get(LibId);
    }
    
    public String getNextUserSeqNo(){
        String index = String.valueOf(users.size());
        for (int i = 0; i < 5 - index.length(); i++) index = "0" + index; 
        return index;
    }
    
    public void addSql(String newSql){
        sql.add(newSql);
    }

    public List<String> getSql() {
        return sql;
    }
    
    
}
