package njit.cs656.QMT.movieRecommendation.core.service;

import java.util.List;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.internal.Cache;

/**
 * <p>
 * GetMovieInfo
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class GetMovieInfo {
    
    /**
     * get the leftmost completely match movie
     * @param title
     * @return
     */
    public Movie getMovie(String title){
        List<Movie> movieList = Cache.getCache().getMovieList();
        for (Movie m : movieList){
            if (m.getTitle().equalsIgnoreCase(title)) return m;
        }
        return null;
    } 
    
    /**
     * get movie by id
     * @param id
     * @return
     */
    public Movie getMovie(int id){
        return Cache.getCache().getMovie(id);
    } 
}
