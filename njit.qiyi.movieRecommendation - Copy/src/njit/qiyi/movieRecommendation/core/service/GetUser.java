package njit.qiyi.movieRecommendation.core.service;

import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.Cache;

/**
 * <p>
 * GetUser
 * </p>
 *
 * @author qiyi
 * @version 2015-11-21
 */
public class GetUser {
    public User getUser(int id){
        return Cache.getCache().getUser(id);
    }
}
