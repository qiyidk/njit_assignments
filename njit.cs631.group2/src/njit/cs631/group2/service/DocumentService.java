package njit.cs631.group2.service;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs631.group2.core.Copy;
import njit.cs631.group2.core.Document;
import njit.cs631.group2.core.Document.DocumentType;
import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;
import njit.cs631.group2.core.service.SQLHandler;

/**
 * <p>
 * DocumentService
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class DocumentService {
    
    public enum Operation{
        CHECKOUT("CHECKOUT"),
        RESERVE("STUDENT"),
        RETURN("RETURN");
        
        private String name;
        private Operation(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
    }
    
    public static List<Document> getDocument(String id, String title, String publisherName){
        
        List<Document> documents = new ArrayList<Document>();
        List<String> tableName = new ArrayList<String>();
        tableName.add("Document");
        if (publisherName != null) tableName.add("PUBLISHER");
        
        SQLTemplate template = new SQLTemplate(SQLKeyword.QUERY, tableName);
        HashMap<String, String> condition = new HashMap<String, String>();
        
        if (id != null) condition.put("DOCID", id);
        if (title != null) condition.put("TITLE", title);
        if (publisherName != null) {
            condition.put("t1.PUBLISHERID", "t2.PUBLISHERID");
            condition.put("PUBNAME", publisherName);
        }
        template.setCondition(condition);
        List<Map<String,String>> res = SQLHandler.execute(template);
        for (Map<String,String> r : res){
            
            SQLTemplate getExtraInfo = new SQLTemplate(SQLKeyword.QUERY, r.get("TYPE"));
            HashMap<String, String> c = new HashMap<String, String>();
            c.put("DOCID", r.get("DOCID"));
            getExtraInfo.setCondition(c);
            Map<String, String> extraInfo = SQLHandler.execute(getExtraInfo).get(0);
            
            Document d = new Document(r.get("DOCID"), r.get("TITLE"), r.get("PDATE"), r.get("PUBLISHERID"), Enum.valueOf(DocumentType.class, r.get("TYPE")), extraInfo);
            documents.add(d);
        }
        return documents;
    }
    
    public static synchronized boolean process(String userId, String libId, String docId, String copyNo, Operation oper){
        if (!isAvailable(userId, libId, docId, copyNo, oper)) return false;
        SQLTemplate template = null;
        String[] InsertValues = null;
        List<String[]> updateValues = new ArrayList<String[]>();
        HashMap<String, String> condition = null;
        Date d = new Date(System.currentTimeMillis());
        String CurrentTime = DateFormat.getInstance().format(d);
        
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        if (h >= 18 && oper.equals(Operation.RESERVE)) return false; // cannot reserve book after 6pm
        
        if (oper.equals(Operation.RESERVE)) {
            template = new SQLTemplate(SQLKeyword.INSERT, "RESERVES");
            InsertValues = new String[]{null, userId, docId, libId, copyNo, CurrentTime};
            template.setValues(InsertValues);
        }
        else if (oper.equals(Operation.CHECKOUT)) {
            template = new SQLTemplate(SQLKeyword.INSERT, "BORROWS");
            InsertValues = new String[]{null, userId, docId, libId, copyNo, CurrentTime, null};
            template.setValues(InsertValues);
        }
        else if (oper.equals(Operation.RETURN)) {
            template = new SQLTemplate(SQLKeyword.UPDATE, "BORROWS");
            condition = new HashMap<String, String>();
            condition.put("DOCID", docId);
            condition.put("LIBID", libId);
            condition.put("COPYNO", copyNo);
            condition.put("RDTIME", null);
            updateValues.add(new String[]{"RDTIME" ,CurrentTime});
            template.setValues(updateValues);
            template.setCondition(condition);
        }
        else return false;
        
        SQLHandler.execute(template);
        return true;
    }
    
    private static boolean isAvailable(String userId, String libId, String docId, String copyNo, Operation oper){
        // Note that this can be done more efficiently by adding a cache to store the effective borrows and reservations 
        // to simplify, just use sql query to check the status
        List<Map<String, String>> res = getBorrowInfo(libId, docId, copyNo);
        if (oper.equals(Operation.RETURN)){
            if (res.size() != 0 
                    && res.get(0).get("READERID").equals(userId)) return true; //return 
            else return false;
        }
        if (res.size() != 0) return false;//borrowed
        
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        if (h >= 18) {
            if (oper == Operation.RESERVE) return false; // cannot make a reservation after 18
            else return true; // all reservation has expired
        }
        
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        
        SQLTemplate template_reserve = new SQLTemplate(SQLKeyword.QUERY, "RESERVES");
        String time = y + "-" + m + "-" + d + " 00:00:00";
        HashMap<String, String> condition = new HashMap<String, String>();
        condition = new HashMap<String, String>();
        condition.put("DOCID", docId);
        condition.put("LIBID", libId);
        condition.put("COPYNO", copyNo);
        condition.put("DTIME", ">=" + "\"" + time + "\"");
        template_reserve.setCondition(condition);
        List<Map<String, String>> res2 = SQLHandler.execute(template_reserve);
        if (res2.size() == 0 || res2.get(0).get("READERID").equals(userId)) return true;
        else return false; 
    }
    
    private static List<Copy> getTodayReservations(){
        List<Copy> res = new ArrayList<Copy>();
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        if (h >= 18) return res;
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        
        SQLTemplate template_reserve = new SQLTemplate(SQLKeyword.QUERY, "RESERVES");
        String time = y + "-" + m + "-" + d + " 00:00:00";
        HashMap<String, String> condition = new HashMap<String, String>();
        condition = new HashMap<String, String>();
        condition.put("DTIME", ">=" + "\"" + time + "\"");
        template_reserve.setCondition(condition);
        List<Map<String, String>> res2 = SQLHandler.execute(template_reserve);
        for (Map<String, String> r : res2){
            Copy copy = new Copy(r.get("DOCID"), r.get("COPYNO"), r.get("LIBID"), null);
            res.add(copy);
        }
        return res;
    }
    
    private static List<Map<String, String>> getBorrowInfo(String libId, String docId, String copyNo){
        SQLTemplate template_bor = new SQLTemplate(SQLKeyword.QUERY, "BORROWS");
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("DOCID", docId);
        condition.put("LIBID", libId);
        condition.put("COPYNO", copyNo);
        condition.put("RDTIME", null);
        template_bor.setCondition(condition);
        return SQLHandler.execute(template_bor);
    }
    
    
    /**
     * Compute fine for specified copy which is not returned, return -1 if copy doesn't exist
     * @param libId
     * @param docId
     * @param copyNo
     * @return
     */
    public static double computeFine(String libId, String docId, String copyNo){
        List<Map<String, String>> res = getBorrowInfo(libId, docId, copyNo);
        if (res.size() == 0) return -1;
        return computeFine(res.get(0));
    }
    
    
    static double computeFine(Map<String, String> Bor_tuple){
        String borrowTime = Bor_tuple.get("BDTIME");
        String returnTime = Bor_tuple.get("RDTIME");
        String[] v = borrowTime.split(" ");
        String[] d = v[0].split("-");
        int year = Integer.parseInt(d[0]);
        int month = Integer.parseInt(d[1]);
        int day = Integer.parseInt(d[2]);
        String[] t = v[1].trim().split(":");
        int hh = Integer.parseInt(t[0]);
        int mm = Integer.parseInt(t[1]);
        int ss = (int)Double.parseDouble(t[2]);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hh, mm, ss);
        long time = 0;
        if (returnTime == null) time = System.currentTimeMillis();
        else{
            String[] v2 = returnTime.split(" ");
            String[] d2 = v2[0].split("-");
            int year2 = Integer.parseInt(d2[0]);
            int month2 = Integer.parseInt(d2[1]);
            int day2 = Integer.parseInt(d2[2]);
            String[] t2 = v2[1].trim().split(":");
            int hh2 = Integer.parseInt(t2[0]);
            int mm2 = Integer.parseInt(t2[1]);
            int ss2 = (int)Double.parseDouble(t2[2]);
            Calendar c2 = Calendar.getInstance();
            c2.set(year2, month2 - 1, day2, hh2, mm2, ss2);
            time = c2.getTimeInMillis();
        }
        double diff = time - c.getTimeInMillis();
        int diff_day = (int) Math.ceil(diff / 1000 / 3600 / 24);
        return diff_day > 20 ? (diff_day - 20) * 0.2 : 0;
    }
    
    /**
     * get the status of available copy of specified document
     * @param docId
     * @return copies that cannot be borrowed will be eliminated from the the result
     * @return status 1 represents can borrow can reserve 
     * @return status 2 represents can borrow only
     */
    public static Map<Copy, Integer> getAvailableCopyStatus(String docId){
        Map<Copy, Integer> res = new HashMap<Copy, Integer>();
        String sql = "Select * from COPY t1, BORROWS t2 where t1.DOCID = \""
                      + docId 
                      + "\" and "
                      + "t1.DOCID = t2.DOCID and "
                      + "t1.LIBID = t2.LIBID and "
                      + "t1.COPYNO = t2.COPYNO and "
                      + "(t1.DOCID, t1.LIBID, t1.COPYNO) NOT IN (SELECT DOCID, LIBID, COPYNO FROM BORROWS WHERE RDTIME is null)";
        SQLTemplate template = new SQLTemplate(sql);
        List<Map<String, String>> list = SQLHandler.execute(template);
        for (Map<String, String> tuple : list){
            Copy copy = new Copy(tuple.get("DOCID"), tuple.get("COPYNO"), tuple.get("LIBID"), tuple.get("POSITION"));
            res.put(copy, 1);
        }
        List<Copy> reserve = getTodayReservations();
        for (Copy c : reserve){
            res.put(c, 2);
        }
        return res;
    }
    
    /**
     * get reservation info of specified user, including copy information and corresponding status 
     * @param userId
     * @return
     */
    public static Map<Copy, String> getReservedInfo(String userId){
        SQLTemplate template_reserve = new SQLTemplate(SQLKeyword.QUERY, "RESERVES");
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("READERID", userId);
        template_reserve.setCondition(condition);
        template_reserve.setOrderBY("DTIME");
        template_reserve.setAsc(false);
        List<Map<String, String>> res = SQLHandler.execute(template_reserve);
        Map<Copy, String> r = new HashMap<Copy, String>();
        for (Map<String, String> reserve : res){
            String status = null;
            if (h > 18) status = "expired";
            else{
                String[] v = reserve.get("DTIME").split(" ");
                String[] dd = v[0].split("-");
                int year = Integer.parseInt(dd[0]);
                int month = Integer.parseInt(dd[1]);
                int day = Integer.parseInt(dd[2]);
                if (isTheSameDay(y, m, d, year, month, day)) status = "reserved";
                else status = "expired";
            }
            Copy copy = new Copy(reserve.get("DOCID"), reserve.get("COPYNO"), reserve.get("LIBID"), null);
            r.put(copy, status);
        }
        return r;
    }
    
    private static boolean isTheSameDay(int y, int m, int d, int y1, int m1, int d1){
        return y == y1 && m == m1 && d == d1;
    }
    
    
    /**
     * return the borrow info of a specified user, including copy information and corresponding fine 
     * @param userId
     * @return
     */
    public static Map<Copy, Double> getBorrowInfo(String userId){ 
        Map<Copy, Double> res = new HashMap<Copy, Double>();
        SQLTemplate template_bor = new SQLTemplate(SQLKeyword.QUERY, "BORROWS");
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("READERID", userId);
        template_bor.setCondition(condition);
        template_bor.setGroupBY("BDTIME");
        template_bor.setAsc(false);
        List<Map<String,String>> list =  SQLHandler.execute(template_bor);
        for (Map<String,String> m : list){
            Copy copy = new Copy(m.get("DOCID"), m.get("COPYNO"), m.get("LIBID"), null);
            res.put(copy, computeFine(m));
        }
        return res;
    }
    
    public static List<Document> getMostPopularBooks(String libId){
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("LIBID", libId);
        return getMostPopularBook(condition);
    }
    
    public static List<Document> getMostPopularBooksOfThisYear(){
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        String time = y + "-" + "1" + "-" + "1" + " 00:00:00";
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("BDTIME", condition.put("DTIME", ">=" + "\"" + time + "\""));
        return getMostPopularBook(condition);
    }
    
    private static List<Document> getMostPopularBook (Map<String, String> condition){
        SQLTemplate template = new SQLTemplate(SQLKeyword.QUERY, "BORROWS");
        template.setCondition(condition);
        template.setGroupBY("DOCID");
        template.setOrderBY(SQLKeyword.COUNT.getName());
        template.setAsc(false);
        template.setRsCapacity(10);
        template.setAggregateFunc(SQLKeyword.COUNT);
        template.setAggregateAttr("DOCID");
        List<Map<String, String>> list = SQLHandler.execute(template);
        List<Document> res = new ArrayList<Document>();
        for (Map<String, String> r : list){
            Document d = getDocument(r.get("DOCID"), null, null).get(0);
            res.add(d);
        }
        return res;
    }
    
}