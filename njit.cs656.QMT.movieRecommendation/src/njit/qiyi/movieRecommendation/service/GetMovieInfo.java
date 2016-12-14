package njit.qiyi.movieRecommendation.service;

import njit.qiyi.movieRecommendation.core.Movie;

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
        return new njit.qiyi.movieRecommendation.core.service.GetMovieInfo().getMovie(title);
    }
    
    /**
     * get movie by id
     * @param id
     * @return
     */
    public Movie getMovie(int id){
        return new njit.qiyi.movieRecommendation.core.service.GetMovieInfo().getMovie(id);
    }
}
