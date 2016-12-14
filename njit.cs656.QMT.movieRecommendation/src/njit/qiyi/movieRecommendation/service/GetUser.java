package njit.qiyi.movieRecommendation.service;

import njit.qiyi.movieRecommendation.core.User;

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
        return new njit.qiyi.movieRecommendation.core.service.GetUser().getUser(id);
    }
}
