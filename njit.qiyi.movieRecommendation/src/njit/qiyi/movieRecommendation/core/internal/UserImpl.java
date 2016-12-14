package njit.qiyi.movieRecommendation.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.qiyi.movieRecommendation.core.SimilarityEntry;
import njit.qiyi.movieRecommendation.core.User;

/**
 * <p>
 * UserImpl
 * </p>
 *
 * @author qiyi
 * @version 2015-11-13
 */
public class UserImpl implements User{
    
    private int id;
    private Map<Integer, Double> ratings = new HashMap<Integer, Double>();
    private Map<Integer, Double> EstimateRatings = new HashMap<Integer, Double>();
    private List<SimilarityEntry> recommendMovie = new ArrayList<SimilarityEntry>();
    
    @Override
    public int getUserID() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public Map<Integer, Double> getRatings() {
        // TODO Auto-generated method stub
        Map<Integer, Double> ratings = new HashMap<Integer, Double>();
        ratings.putAll(this.ratings);
        return ratings;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRatings(Map<Integer, Double> ratings) {
        this.ratings.putAll(ratings);
    }
    public void setRating(int movieID, double rating) {
        this.ratings.put(movieID, rating);
    }
    /**
     * get a copy of recommendMovies
     * @return
     */
    public List<SimilarityEntry> getRecommendMovies() {
        List<SimilarityEntry> recommendMovie = new ArrayList<SimilarityEntry>();
        recommendMovie.addAll(this.recommendMovie);
        return recommendMovie;
    }

    public void setRecommendMovies(List<SimilarityEntry> recommendMovie) {
        this.recommendMovie.addAll(recommendMovie);
    }  
    public void setRecommendMovie(SimilarityEntry entry) {
        this.recommendMovie.add(entry);
    }

    public Map<Integer, Double> getEstimateRatings() {
        return EstimateRatings;
    }

    public void setEstimateRatings(Map<Integer, Double> estimateRatings) {
        EstimateRatings = estimateRatings;
    }  
    
    public void setEstimateRating(int movieId, double rating) {
        EstimateRatings.put(movieId, rating);
    }  
    
    public void resetEstimateRating(){
	EstimateRatings = new HashMap<Integer, Double>();
    }
    
}
