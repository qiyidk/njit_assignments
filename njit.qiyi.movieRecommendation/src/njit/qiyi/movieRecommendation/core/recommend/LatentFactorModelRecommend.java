/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;

import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.UserImpl;

/**
 * @author qiyi
 *
 */
public class LatentFactorModelRecommend {
    DataModel model = null;
    public LatentFactorModelRecommend(String fileName){
	try {
            URI uri = this.getClass().getClassLoader().getResource(fileName).toURI();
            File file = new File(uri);
            model = new FileDataModel(file);
	}
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void estimateRatings(Factorizer factorizer, List<User> testdata){
	try {
	    SVDRecommender recommender = new SVDRecommender(model, factorizer);
	    for (User user : testdata) {
		for (int movieID : user.getRatings().keySet()){
		    try {
			double rating = recommender.estimatePreference(user.getUserID(), movieID);
			((UserImpl)user).setEstimateRating(movieID, rating);
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                //e.printStackTrace();
	            }
		}
	    }
	    
	} catch (TasteException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
