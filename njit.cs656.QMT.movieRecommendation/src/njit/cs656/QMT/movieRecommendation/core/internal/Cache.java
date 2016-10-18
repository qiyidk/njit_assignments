package njit.cs656.QMT.movieRecommendation.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;

/**
 * <p>
 * Cache
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class Cache {
    private Map<Integer, Movie> movieCache = new HashMap<Integer, Movie>(); // movie cache
    private Map<Integer, User> userCache = new HashMap<Integer, User>(); // user cache
    private List<Movie> movieList = new ArrayList<Movie>(); // movie cache for fuzzy matching
    private Cache() {} 
    private static final Cache cache = new Cache(); // Singleton
    public static Cache getCache(){
        return cache;
    }
    
    /**
     * get all movies
     * notice: the return List shouldn't be modified and this method cannot be called outside core layer
     * if need to modify the movie list, call getMovieListCopy() method
     * @return
     */
    public List<Movie> getMovieList(){
        return movieList;
    }
    
    /**
     * get copy of all movies
     * @return
     */
    public List<Movie> getMovieListCopy(){
        List<Movie> list = new ArrayList<Movie>();
        list.addAll(movieList);
        return list;
    }
    
    public Movie getMovie(int id){
        return movieCache.get(id);
    }
    
    /**
     * get copy of all users
     * @return
     */
    public List<User> getUserList(){
        List<User> list = new ArrayList<User>();
        list.addAll(userCache.values());
        return list;
    }
    
    public User getUser(int id){
        return userCache.get(id);
    }
    
    public void setMovieCache(Map<Integer, Movie> movieCache) {
        this.movieCache.putAll(movieCache);
    }

    public void setUserCache(Map<Integer, User> userCache) {
        this.userCache.putAll(userCache);
    }
    public void setMovieList(List<Movie> movieList) {
        this.movieList.addAll(movieList);
    }
       
}
