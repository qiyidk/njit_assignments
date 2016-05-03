package njit.cs631.group2.service;

import java.util.Collection;


import njit.cs631.group2.core.Branch;
import njit.cs631.group2.core.service.BranchHandler;

/**
 * <p>
 * BranchService
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class BranchService {
    public static Collection<Branch> getBranches(){
        return BranchHandler.getBranches();
    }
    
    public static Branch getBranch(String LibId){
        return BranchHandler.getBranch(LibId);
    }
    
}
