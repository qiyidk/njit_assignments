package njit.cs631.group2.core;

/**
 * <p>
 * Copy
 * </p>
 *
 * @author qiyi
 * @version 2016-4-28
 */
public class Copy {
    private String docId;
    private String COPYNO;
    private String LIBID;
    private String POSITION;
    
    public Copy(String docId, String COPYNO, String LIBID, String POSITION){
        this.docId = docId;
        this.COPYNO = COPYNO;
        this.LIBID = LIBID;
        this.POSITION = POSITION;
    }
    
    public boolean equals(Object c){
        if (!(c instanceof Copy)) return false;
        Copy d = (Copy)c;
        return this.docId == d.docId && this.COPYNO == d.COPYNO && this.LIBID == d.LIBID;
    }
    
    public int hashCode(){
        return docId.hashCode() * 3 + COPYNO.hashCode() * 5 + LIBID.hashCode() * 7;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getCOPYNO() {
        return COPYNO;
    }

    public void setCOPYNO(String cOPYNO) {
        COPYNO = cOPYNO;
    }

    public String getLIBID() {
        return LIBID;
    }

    public void setLIBID(String lIBID) {
        LIBID = lIBID;
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String pOSITION) {
        POSITION = pOSITION;
    }

    
    
}

