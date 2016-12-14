package njit.qiyi.movieRecommendation.core.internal;

import java.util.ArrayList;
import java.util.List;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.SimilarityEntry;

/**
 * <p>
 * MovieImpl
 * </p>
 *
 * @author qiyi
 * @version 2015-11-13
 */
public class MovieImpl implements Movie{
    private int id;
    private String title; 
    private List<String> genres = new ArrayList<String>(); 
    private int issueTime;  
    private double avgRating; 
    private List<String> tags = new ArrayList<String>();
    private List<SimilarityEntry> similarMovies = new ArrayList<SimilarityEntry>();
    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return title;
    }

    @Override
    public List<String> getGenres() {
        // TODO Auto-generated method stub
        List<String> genres = new ArrayList<String>();
        genres.addAll(this.genres);
        return genres;
    }

    @Override
    public int getIssueTime() {
        // TODO Auto-generated method stub
        return issueTime;
    }

    @Override
    public double getAvgRating() {
        // TODO Auto-generated method stub
        return avgRating;
    }

    @Override
    public List<String> getTags() {
        // TODO Auto-generated method stub
        List<String> tags = new ArrayList<String>();
        tags.addAll(this.tags);
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenres(List<String> genres) {
        this.genres.addAll(genres);
    }
    public void setGenre(String genre) {
        genres.add(genre);
    }

    public void setIssueTime(int issueTime) {
        this.issueTime = issueTime;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public void setTag(String tag) {
        this.tags.add(tag);
    }
    
    public void setTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public List<SimilarityEntry> getSimilarMovies() {
        List<SimilarityEntry> similarMovies = new ArrayList<SimilarityEntry>();
        similarMovies.addAll(this.similarMovies);
        return similarMovies;
    }

    public void setSimilarMovies(List<SimilarityEntry> similarMovies) {
        this.similarMovies.addAll(similarMovies);
    }
    public void setSimilarMovie(SimilarityEntry entry) {
        similarMovies.add(entry);
    }
    
}
