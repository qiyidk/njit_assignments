package njit.cs656.QMT.movieRecommendation.service;

import java.util.List;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;

/**
 * <p>
 * GetMovieList
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class GetMovieList {
    
    /**
     * get movie lists by the fuzzy match string 
     * @param fuzzy match string 
     * @return
     */
    public List<Movie> getCloselyMatchMovieList(String match){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getCloselyMatchMovieList(match);
    }
    
    /**
     * get movie lists by the fuzzy match string, return the first "num" movies 
     * @param fuzzy match string
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getCloselyMatchMovieList(String match, int num){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getCloselyMatchMovieList(match, num);
    }
    
    /**
     * get movie lists by the prefix 
     * @param prefix
     * @return
     */
    public List<Movie> getMovieList(String prefix){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getMovieList(prefix);
    }
    
    /**
     * get movie lists by the prefix, return the first "num" movies 
     * @param prefix
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getMovieList(String prefix, int num){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getMovieList(prefix, num);
    }
    
    
    /**
     * get movie lists by user
     * @param user
     * @return
     */
    public List<Movie> getMovieList(User user){
        return  new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getMovieList(user);
    }
    
    /**
     * get movie lists by user, return the first "num" movies 
     * @param user
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getMovieList(User user, int num){
        return new njit.cs656.QMT.movieRecommendation.core.service.GetMovieList().getMovieList(user, num);
    }
}
