/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.MovieImpl;
import njit.qiyi.movieRecommendation.core.internal.SimilartityEntryImpl;
import njit.qiyi.movieRecommendation.core.internal.UserImpl;

/**
 * @author qiyi
 *
 */
public class NeighborhoodRecommend {
    private int neighborhoodSize = 200;
    DataModel model = null;
    public NeighborhoodRecommend(String fileName){
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
    public void estimateRatings(SimilarityMetric metric, List<Movie> movies, List<User> testdata, List<User> trainData, boolean adjustedRating){
	ItemSimilarity similarity = getItemSimilarity(metric);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model,
                    similarity);
        Map<Integer, Map<Integer, Double>> Similarities = new HashMap<Integer, Map<Integer, Double>>();
        
        Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>();
        for (Movie m : movies) movieMap.put(m.getID(), m);
        
        Map<Integer, User> trainMap = new HashMap<Integer, User>();
        for (User u : trainData) trainMap.put(u.getUserID(), u);
        
        for (Movie movie : movieMap.values()){
            Map<Integer, Double> similarityMap = new HashMap<Integer, Double>();
            Similarities.put(movie.getID(), similarityMap);
            try {
		List<RecommendedItem> similarItems = recommender.mostSimilarItems(movie.getID(), movieMap.size());
		for (RecommendedItem item : similarItems){
		    similarityMap.put((int)item.getItemID(), (double)item.getValue());
		}
	    } catch (TasteException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
	    }
        }
	for (User user : testdata) {
	    Map<Integer, Double> ratings = user.getRatings();
	    for (int movieId : ratings.keySet()){
		Map<Integer, Double> similarityMap = Similarities.get(movieId);
		Movie movie = movieMap.get(movieId);
    		try {
    		    double rating = 0;
    		    double sumSimilarity = 0;
    		    for (HashMap.Entry<Integer, Double> entry : trainMap.get(user.getUserID()).getRatings().entrySet()){
    			// predict rating = weighted (sum * avg ratio if adjusted)
    			int id = entry.getKey();
    			if (!similarityMap.containsKey(id)) continue;
    			double r = entry.getValue();
    			double sim = similarityMap.get(id);
    			if (sim < 0.5) continue;
    			double adjustFactor = adjustedRating ? movie.getAvgRating() / movieMap.get(id).getAvgRating() : 1;
    			sumSimilarity += sim;
    			rating += sim * r * adjustFactor;
    		    }
    		    ((UserImpl)user).setEstimateRating(movieId, rating / sumSimilarity);
    		} catch (Exception e) {
    		    // TODO Auto-generated catch block
    		    //e.printStackTrace();
    		}
	    }
	}
    }
    public void setItemNeighborhood(List<Movie> movieList, SimilarityMetric metric){
	ItemSimilarity similarity = getItemSimilarity(metric);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(model,
                    similarity);

        for (Movie movie : movieList) {
            try {
                List<RecommendedItem> similarItems = recommender.mostSimilarItems(movie.getID(), neighborhoodSize);
                for (RecommendedItem item : similarItems) {
                    ((MovieImpl) movie).setSimilarMovie(
                            new SimilartityEntryImpl((int) item.getItemID(), item.getValue()));
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    private ItemSimilarity getItemSimilarity(SimilarityMetric metric){
	switch(metric){
	case LOGLIKELIHOOD: return new LogLikelihoodSimilarity(model);
	case PEARSON_CORRELATION : try {
		return new PearsonCorrelationSimilarity(model);
	    } catch (TasteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	case COSINE: try {
		return new UncenteredCosineSimilarity(model);
	    } catch (TasteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	default : return null;
	}
    }
}
