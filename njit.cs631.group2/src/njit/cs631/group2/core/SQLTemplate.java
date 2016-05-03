package njit.cs631.group2.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import njit.cs631.group2.core.internal.Cache;
import njit.cs631.group2.core.internal.SystemPara;

/**
 * <p>
 * SQLTemplate
 * </p>
 *
 * @author qiyi
 * @version 2016-4-26
 */
public class SQLTemplate {
    
    public enum SQLKeyword{
        INSERT("INSERT INTO"),
        QUERY("SELECT"),
        UPDATE("UPDATE"),
        AVG("AVG"),
        COUNT("COUNT");
        
        private String keyword = null;
        private SQLKeyword(String keyword){
            this.keyword = keyword;
        }
        public String getName(){
            return keyword;
        }
    }
    private SQLKeyword oper = null;
    private List<String> tableName = null;
    private Map<String, String> condition = null;
    private List<String> groupBY = null;
    private List<String> OrderBY = null;
    private boolean isAsc = true;
    private int rsCapacity = -1;// maximum result set capacity, -1 represents no limit
    private List<String[]> values = null;
    private SQLKeyword aggregateFunc = null;
    private String aggregateAttr = null;
    private String sql = null;
    
    public SQLTemplate(SQLKeyword oper, List<String> tableName){
        this.oper = oper;
        this.tableName = tableName;
    }
    public SQLTemplate(SQLKeyword oper, String tableName){
        this.oper = oper;
        List<String> list = new ArrayList<String>();
        list.add(tableName);
        this.tableName = list;
    }
    
    public SQLTemplate(String sql){
        this.sql = sql;
    }
    
    public String getSQL(){
        if (this.sql != null) return this.sql;
        String sql = null;
        switch(oper){
        case INSERT : sql = generateInsert();break;
        case QUERY : sql = generateQuery();break;
        case UPDATE : sql = generateUpdate();break;
        default : return null;
        }
        if (SystemPara.debug) System.out.println(sql);
        if (SystemPara.redirect) Cache.getCache().addSql(sql);
        return sql;
    }

    private String generateInsert(){
        StringBuilder sb = new StringBuilder(SQLKeyword.INSERT.getName());
        sb.append(" ")
        .append(tableName.get(0))
        .append(" ")
        .append("VALUES\n");
        for (String[] value : values){
            sb.append("(");
            for (String v : value){
                if (v == null){
                    sb.append("null,");
                }
                else{
                    sb.append("\"")
                    .append(v)
                    .append("\"")
                    .append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);//delete the last ","
            sb.append("),");
        }
        sb.deleteCharAt(sb.length() - 1);//delete the last ","
        sb.append(";");
        return sb.toString();
    }
    
    private String generateQuery(){
        StringBuilder sb = new StringBuilder(SQLKeyword.QUERY.getName() + " ");
        if (getAggregateFunc() != null) {
            for (String g : getGroupBY()){
                sb.append(g + ", ");
            }
            sb.append(getAggregateFunc().getName())
            .append("(")
            .append(getAggregateAttr())
            .append(") as ")
            .append(getAggregateFunc().getName());
        }
        else sb.append("*");
        sb.append(" FROM ");
        for (int i = 0; i < tableName.size(); i++){
            String t = tableName.get(i);
            sb.append(t);
            sb.append(" t" + (i + 1));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);//delete the last ","
        if (condition != null) {
            sb.append(" WHERE ");
            for (Entry<String, String> e : condition.entrySet()){
                if (e.getValue() == null){
                    sb.append(e.getKey())
                    .append(" is null")
                    .append(" and ");
                }
                else if (e.getValue().startsWith("<") || e.getValue().startsWith(">")){
                    sb.append(e.getKey())
                    .append(e.getValue())
                    .append(" and ");
                }
                else{
                    sb.append(e.getKey())
                    .append(" = \"")
                    .append(e.getValue())
                    .append("\" and ");
                }
            }
            sb.delete(sb.length() - 5, sb.length() - 1);
            
        }
        if (groupBY != null) {
            sb.append(" ").append("Group By ");
            for (String g : groupBY) sb.append(g).append(",");
            sb.deleteCharAt(sb.length() - 1);//delete the last ","
        }
        if (OrderBY != null){
            sb.append(" ").append("Order By ");
            for (String o : OrderBY) sb.append(o).append(",");
            sb.deleteCharAt(sb.length() - 1);//delete the last ","
            if (!isAsc) sb.append(" ").append("desc");
        }
        if (rsCapacity != -1) sb.append("Limit " + rsCapacity);
        sb.append(";");
        return sb.toString();
    }
    
    private String generateUpdate(){
        StringBuilder sb = new StringBuilder(SQLKeyword.UPDATE.getName());
        sb.append(" ")
        .append(tableName.get(0))
        .append(" Set ");
        
        for (String[] value : values){
            if (value[1] == null){
                sb.append(value[0])
                .append(" = ")
                .append("null")
                .append(",");
            }
            else{
                sb.append(value[0])
                .append(" = ")
                .append("\"")
                .append(value[1])
                .append("\"")
                .append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);//delete the last ","
        
        if (condition != null) {
            sb.append(" WHERE ");
            for (Entry<String, String> e : condition.entrySet()){
                if (e.getValue() == null){
                    sb.append(e.getKey())
                    .append(" is null")
                    .append(" and ");
                }
                else if (e.getValue().startsWith("<") || e.getValue().startsWith(">")){
                    sb.append(e.getKey())
                    .append(e.getValue())
                    .append(" and ");
                }
                else{
                    sb.append(e.getKey())
                    .append(" = \"")
                    .append(e.getValue())
                    .append("\" and ");
                }
            }
            sb.delete(sb.length() - 5, sb.length() - 1);
            
        }
        sb.append(";");
        return sb.toString();
    }

    public SQLKeyword getOper() {
        return oper;
    }

    public void setOper(SQLKeyword oper) {
        this.oper = oper;
    }

    public List<String> getTableName() {
        return tableName;
    }

    public void setTableName(List<String> tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getCondition() {
        return condition;
    }

    /**
     * set condition attribute and value
     * you can use t1.attributeName to reference the attribute of the first table
     * @param condition
     */
    public void setCondition(Map<String, String> condition) {
        this.condition = condition;
    }

    public List<String> getGroupBY() {
        return groupBY;
    }

    public void setGroupBY(String groupBY) {
        List<String> list = new ArrayList<String>();
        list.add(groupBY);
        this.groupBY = list;
    }
    
    public void setGroupBY(List<String> groupBY) {
        this.groupBY = groupBY;
    }

    public List<String> getOrderBY() {
        return OrderBY;
    }

    public void setOrderBY(String orderBY) {
        List<String> list = new ArrayList<String>();
        list.add(orderBY);
        OrderBY = list;
    }
    
    public void setOrderBY(List<String> orderBY) {
        OrderBY = orderBY;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public int getRsCapacity() {
        return rsCapacity;
    }

    public void setRsCapacity(int rsCapacity) {
        this.rsCapacity = rsCapacity;
    }

    public List<String[]> getValues() {
        return values;
    }

    public void setValues(String[] values) {
        List<String[]> list = new ArrayList<String[]>();
        list.add(values);
        this.values = list;
    }
    
    public void setValues(List<String[]> values) {
        this.values = values;
    }

    public SQLKeyword getAggregateFunc() {
        return aggregateFunc;
    }

    public void setAggregateFunc(SQLKeyword aggregateFunc) {
        this.aggregateFunc = aggregateFunc;
    }
    public String getAggregateAttr() {
        return aggregateAttr;
    }
    public void setAggregateAttr(String aggregateAttr) {
        this.aggregateAttr = aggregateAttr;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    
    
}

