/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.UserImpl;

/**
 * @author qiyi
 *
 */
public class AverageRecommend {
    public void estimateRatings(List<User> testdata, List<Movie> movies){
	Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>();
	for (Movie m : movies) movieMap.put(m.getID(), m);
	for (User user : testdata) {
	    for (int movieID : user.getRatings().keySet()){
		try {
		    double rating = movieMap.get(movieID).getAvgRating();
		    ((UserImpl)user).setEstimateRating(movieID, rating);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            //e.printStackTrace();
	        }
	    }
	}
    }
}
