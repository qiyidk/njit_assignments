package njit.cs631.group2.core.internal;

import njit.cs631.group2.core.Branch;

/**
 * <p>
 * BranchImpl
 * </p>
 *
 * @author qiyi
 * @version 2016-4-29
 */
public class BranchImpl implements Branch {

    private String LIBID;
    private String LNAME;
    private String LLOCATION;
    
    public BranchImpl(String LIBID, String LNAME, String LLOCATION){
        this.LIBID = LIBID;
        this.LNAME = LNAME;
        this.LLOCATION = LLOCATION;
    }
    
    public String getLIBID() {
        return LIBID;
    }
    public void setLIBID(String lIBID) {
        LIBID = lIBID;
    }
    public String getLNAME() {
        return LNAME;
    }
    public void setLNAME(String lNAME) {
        LNAME = lNAME;
    }
    public String getLLOCATION() {
        return LLOCATION;
    }
    public void setLLOCATION(String lLOCATION) {
        LLOCATION = lLOCATION;
    }
    
    

}
