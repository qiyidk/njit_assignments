package njit.cs631.group2.core.service;

import java.util.Collection;

import njit.cs631.group2.core.Branch;
import njit.cs631.group2.core.internal.Cache;

/**
 * <p>
 * BranchHandler
 * </p>
 *
 * @author qiyi
 * @version 2016-4-29
 */
public class BranchHandler {
    public static Collection<Branch> getBranches(){
        return Cache.getCache().getBranches();
    }
    
    public static Branch getBranch(String LibId){
        return Cache.getCache().getBranch(LibId);
    }
}
