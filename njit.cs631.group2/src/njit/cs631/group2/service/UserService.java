package njit.cs631.group2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;
import njit.cs631.group2.core.User;
import njit.cs631.group2.core.UserType;
import njit.cs631.group2.core.service.SQLHandler;
import njit.cs631.group2.core.service.UserHandler;

/**
 * <p>
 * UserService
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class UserService {
    public User createUser(String userName, String password, String address, UserType type){
        return UserHandler.createUser(userName, password, address, type);
    }
    
    public boolean verify(String userName, String password){
        return password == null 
                || UserHandler.getUser(userName).getPassword().equals(password);
    }
    
    public Map<String, Integer> getMostActiveReader(String libId){
        Map<String, Integer> res = new HashMap<String, Integer>();
        SQLTemplate t = new SQLTemplate(SQLKeyword.QUERY, "BORROWS");
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("LIBID", libId);
        t.setCondition(condition);
        t.setGroupBY("READERID");
        t.setAsc(false);
        t.setRsCapacity(10);
        t.setAggregateFunc(SQLKeyword.COUNT);
        t.setAggregateAttr("READERID");
        t.setOrderBY(SQLKeyword.COUNT.getName());
        List<Map<String, String>> list = SQLHandler.execute(t);
        for (Map<String, String> r : list){
            res.put(r.get("READERID"), Integer.parseInt(r.get(SQLKeyword.COUNT.getName())));
        }
        return res;
    }
    
    public Map<String, Double> getAvgFine(){
        Map<String, Double> res = new HashMap<String, Double>();
        SQLTemplate t = new SQLTemplate(SQLKeyword.QUERY, "BORROWS");
        t.setGroupBY("READERID");
        List<Map<String, String>> list = SQLHandler.execute(t);
        String lastReader = null;
        int count = 0;
        double sum = 0;
        for (Map<String, String> tuple : list){
            String reader = tuple.get("READERID");
            if (!reader.equals(lastReader) && (lastReader != null)){
                res.put(lastReader, sum / count);
                count = 1;
                sum = DocumentService.computeFine(tuple);
            }
            else{
                count++;
                sum+= DocumentService.computeFine(tuple);
            }
        }
        return res;
    }
}
