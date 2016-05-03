package njit.cs631.group2.core;

import java.util.Map;

/**
 * <p>
 * Document
 * </p>
 *
 * @author qiyi
 * @version 2016-4-29
 */
public class Document {
    private String DOCID;
    private String TITLE;
    private String PDATE;
    private String PUBLISHERID;
    private DocumentType type;
    private Map<String, String> extraInfo;
    
    public enum DocumentType {
        
        BOOK("BOOK"),
        JOURNAL_VOLUME("JOURNAL_VOLUME"),
        PROCEEDINGS("PROCEEDINGS");
        
        private String name;
        private DocumentType(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
    }
    
    public Document(String DOCID, String TITLE, String PDATE, String PUBLISHERID, DocumentType type, Map<String, String> extraInfo){
        this.DOCID = DOCID;
        this.TITLE = TITLE;
        this.PDATE = PDATE;
        this.PUBLISHERID = PUBLISHERID;
        this.type = type;
        this.extraInfo = extraInfo;
    }
    public String getDOCID() {
        return DOCID;
    }
    public void setDOCID(String dOCID) {
        DOCID = dOCID;
    }
    public String getTITLE() {
        return TITLE;
    }
    public void setTITLE(String tITLE) {
        TITLE = tITLE;
    }
    public String getPDATE() {
        return PDATE;
    }
    public void setPDATE(String pDATE) {
        PDATE = pDATE;
    }
    public String getPUBLISHERID() {
        return PUBLISHERID;
    }
    public void setPUBLISHERID(String pUBLISHERID) {
        PUBLISHERID = pUBLISHERID;
    }
    public DocumentType getType() {
        return type;
    }
    public void setType(DocumentType type) {
        this.type = type;
    }
    public Map<String, String> getExtraInfo() {
        return extraInfo;
    }
    public void setExtraInfo(Map<String, String> extraInfo) {
        this.extraInfo = extraInfo;
    }

    
}