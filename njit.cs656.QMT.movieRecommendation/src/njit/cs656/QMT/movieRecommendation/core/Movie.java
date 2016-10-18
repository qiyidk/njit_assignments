package njit.cs656.QMT.movieRecommendation.core;

import java.util.List;

/**
 * <p>
 * read-only movie model
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public interface Movie {
    public int getID(); // movie id
    public String getTitle(); // movie title
    public List<String> getGenres(); // genres that the movie belongs to
    public int getIssueTime(); // the issue time of the movie
    public double getAvgRating(); //average rating
    public List<String> getTags(); // tags added to the movie
}
