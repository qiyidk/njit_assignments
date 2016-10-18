package njit.cs656.QMT.movieRecommendation.service;

import njit.cs656.QMT.movieRecommendation.core.Movie;

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
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieInfo().getMovie(title);
    }
    
    /**
     * get movie by id
     * @param id
     * @return
     */
    public Movie getMovie(int id){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieInfo().getMovie(id);
    }
}
