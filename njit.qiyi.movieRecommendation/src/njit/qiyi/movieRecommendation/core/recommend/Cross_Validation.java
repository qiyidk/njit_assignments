/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.RatingSGDFactorizer;
import org.apache.mahout.cf.taste.model.DataModel;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.CSVUtil;
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
	int[] correct = new int[6];
	int[] size = new int[6];
	for (int k = 0; k < fold; k++){
	    String fileName = prefix + k + ".csv";
	    List<User>[] data = gen(userData, fileName);
	    List<User> traindata = data[0];
	    List<User> testdata = data[1];
	    NeighborhoodRecommend nr = new NeighborhoodRecommend(fileName);
	    LatentFactorModelRecommend lr = new LatentFactorModelRecommend(fileName);
	    nr.estimateRatings(SimilarityMetric.LOGLIKELIHOOD, movies, testdata, traindata, true);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[0] += ((UserImpl)u).getEstimateRatings().size();
		correct[0] += check(actual, u);
	    }
	    nr.estimateRatings(SimilarityMetric.LOGLIKELIHOOD, movies, testdata, traindata, false);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[1] += ((UserImpl)u).getEstimateRatings().size();
		correct[1] += check(actual, u);
	    }
	    nr.estimateRatings(SimilarityMetric.COSINE, movies, testdata, traindata, true);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[2] += ((UserImpl)u).getEstimateRatings().size();
		correct[2] += check(actual, u);
	    }
	    nr.estimateRatings(SimilarityMetric.PEARSON_CORRELATION, movies, testdata, traindata, true);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[3] += ((UserImpl)u).getEstimateRatings().size();
		correct[3] += check(actual, u);
	    }
	    try {
		URI uri = this.getClass().getClassLoader().getResource(fileName).toURI();
	        File file = new File(uri);
	        DataModel model = new FileDataModel(file);
		lr.estimateRatings(new RatingSGDFactorizer(model, 3, 100), testdata);
		for (User u : testdata){
		    User actual = userMap.get(u.getUserID());
		    size[4] += ((UserImpl)u).getEstimateRatings().size();
		    correct[4] += check(actual, u);
		}
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    new AverageRecommend().estimateRatings(testdata, movies);
	    for (User u : testdata){
		User actual = userMap.get(u.getUserID());
		size[5] += ((UserImpl)u).getEstimateRatings().size();
		correct[5] += check(actual, u);
	    }
	}
	Result r1 = new ResultImpl(SimilarityMetric.LOGLIKELIHOOD + "_Adjusted", size[0], correct[0]);
	Result r2 = new ResultImpl(SimilarityMetric.LOGLIKELIHOOD + "_NonAdjusted", size[1], correct[1]);
	Result r3 = new ResultImpl(SimilarityMetric.COSINE + "_Adjusted", size[2], correct[2]);
	Result r4 = new ResultImpl(SimilarityMetric.PEARSON_CORRELATION + "_Adjusted", size[3], correct[3]);
	Result r5 = new ResultImpl("MatrixFactorization", size[4], correct[4]);
	Result r6 = new ResultImpl("UseAverageValue", size[5], correct[5]);
	res.add(r1);
	res.add(r2);
	res.add(r3);
	res.add(r4);
	res.add(r5);
	res.add(r6);
	return res;
    }
    @SuppressWarnings("unchecked")
    private List<User>[] gen(List<User> originalData, String trainFileName){
	List<User>[] data = new ArrayList[2]; 
	List<User> testdata = new ArrayList<User>();
	List<User> traindata = new ArrayList<User>();
	data[0] = traindata;
	data[1] = testdata;
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
		isTest = false;
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
	CSVUtil.writeData(trainFileName, traindata);
	return data;
    }
    // return the number of correct prediction
    private int check(User actual, User predict){
	int correct = 0;
	for (int movieId : ((UserImpl)predict).getEstimateRatings().keySet()){
	    double realRating = actual.getRatings().get(movieId);
	    double predictRating = ((UserImpl)predict).getEstimateRatings().get(movieId);
	    if (Math.abs(realRating - predictRating) < 0.75) correct++;
	}
	return correct;
    }
    
}
