package njit.cs656.QMT.movieRecommendation.service;

import njit.cs656.QMT.movieRecommendation.core.User;

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
        return new njit.cs656.QMT.movieRecommendation.core.service.GetUser().getUser(id);
    }
}
