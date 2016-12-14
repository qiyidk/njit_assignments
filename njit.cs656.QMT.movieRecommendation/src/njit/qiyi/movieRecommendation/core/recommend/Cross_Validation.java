/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.ResultImpl;
import njit.qiyi.movieRecommendation.core.internal.UserImpl;
import njit.qiyi.movieRecommendation.core.service.Result;

/**
 * @author qiyi
 *
 */
public class Cross_Validation {
    
    private static Random rand = new Random();
    private int fold;
    public Cross_Validation(int fold){
	this.fold = fold;
    }
    
    public List<Result> validation(List<User> userData, List<Movie> movies){
	List<Result> res = new ArrayList<Result>();
	String prefix = "training_";
	Map<Integer, User> userMap = new HashMap<Integer, User>();
	for (User u : userData) userMap.put(u.getUserID(), u);
	int[] correct = new int[5];
	int[] size = new int[5];
	for (int k = 0; k < fold; k++){
	    String fileName = prefix + k + ".csv";
	    List<User> testdata = gen(userData, fileName);
	    NeighborhoodRecommend nr = new NeighborhoodRecommend(fileName);
	    //LatentFactorModelRecommend lr = new LatentFactorModelRecommend(fileName);
	    nr.estimateRatings(SimilarityMetric.COSINE, movies, testdata, false);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[0] += u.getRatings().size();
		correct[0] += check(actual, u);
	    }
	}
	Result r = new ResultImpl(SimilarityMetric.COSINE + "_NonAdjusted", size[0], correct[0]);
	res.add(r);
	return res;
    }
    private List<User> gen(List<User> originalData, String trainFileName){
	List<User> testdata = new ArrayList<User>();
	List<User> traindata = new ArrayList<User>();
	for (User user : originalData){
	    UserImpl u_train = new UserImpl();
	    UserImpl u_test = new UserImpl();
	    testdata.add(u_test);
	    traindata.add(u_train);
	    u_test.setId(user.getUserID());
	    u_train.setId(user.getUserID());
	    Map<Integer, Double> ratings = user.getRatings();
	    int movieLeft = ratings.size();
	    int testLeft = ratings.size() / fold;
	    boolean isTest = false;
	    for (int movieId : ratings.keySet()){
		if (testLeft != 0){
		    int r = rand.nextInt(movieLeft);
		    if (r < testLeft) {
			isTest = true;
			testLeft--;
		    }
		}
		movieLeft--;
		if (isTest) u_test.setRating(movieId, ratings.get(movieId));
		else u_train.setRating(movieId, ratings.get(movieId));
	    }
	}
	return testdata;
    }
    // return the number of correct prediction
    private int check(User actual, User predict){
	int correct = 0;
	for (int movieId : predict.getRatings().keySet()){
	    double realRating = actual.getRatings().get(movieId);
	    double predictRating = predict.getRatings().get(movieId);
	    if (Math.round(realRating) == Math.round(predictRating)) correct++;
	}
	return correct;
    }
    
}
