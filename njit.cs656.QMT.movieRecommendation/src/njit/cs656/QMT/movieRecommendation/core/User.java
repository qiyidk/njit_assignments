package njit.cs656.QMT.movieRecommendation.core;

import java.util.Map;

/**
 * <p>
 * read-only user model
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public interface User {
    public int getUserID(); //userID
    public Map<Integer, Double> getRatings();// the ratings<movieID, rating> that were given by the user     
}
