package njit.cs656.QMT.movieRecommendation.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import njit.cs656.QMT.movieRecommendation.core.User;
import njit.cs656.QMT.movieRecommendation.core.service.SimilartityEntry;

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
    private List<SimilartityEntry> recommendMovie = new ArrayList<SimilartityEntry>();
    
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
    public List<SimilartityEntry> getRecommendMovies() {
        List<SimilartityEntry> recommendMovie = new ArrayList<SimilartityEntry>();
        recommendMovie.addAll(this.recommendMovie);
        return recommendMovie;
    }

    public void setRecommendMovies(List<SimilartityEntry> recommendMovie) {
        this.recommendMovie.addAll(recommendMovie);
    }  
    public void setRecommendMovie(SimilartityEntry entry) {
        this.recommendMovie.add(entry);
    }  
    
}
