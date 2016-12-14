package njit.qiyi.movieRecommendation.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.Cache;

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
     * get copy of all movie
     * @return
     */
    public List<Movie> getMovieList(){
        return Cache.getCache().getMovieListCopy();
    }
    
    /**
     * get movie lists by the prefix 
     * @param prefix
     * @return
     */
    public List<Movie> getMovieList(String prefix){
        return getMovieList(prefix, Integer.MAX_VALUE);
    }
    
    /**
     * get movie lists by the prefix, return the first "num" movies 
     * @param prefix
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getMovieList(String prefix, int num){
        List<Movie> movieList = Cache.getCache().getMovieList();
        List<Movie> list = new ArrayList<Movie>();
        int i = 0;
        for (Movie m : movieList){
            if (m.getTitle().toLowerCase().startsWith(prefix.toLowerCase())) {
                i++;
                list.add(m);
                if (i == num) return list;
            }
        }
        return list;
    }
    
    /**
     * get movie lists by the match String 
     * @param fuzzy match string
     * @return
     */
    public List<Movie> getCloselyMatchMovieList(String match){
        return getMovieList(match, Integer.MAX_VALUE);
    }
    
    /**
     * get movie lists by the match string, return the first "num" movies 
     * @param fuzzy match string
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getCloselyMatchMovieList(String match, int num){
        List<Movie> movieList = Cache.getCache().getMovieList();
        Set<Movie> set = new HashSet<Movie>();
        List<Movie> list = new ArrayList<Movie>();
        if (match == null) return list;
        for (Movie m : movieList){
            if (m.getTitle().toLowerCase().contains(match.toLowerCase())) {
                if (!set.contains(m)){
                    set.add(m);
                    list.add(m);
                    if (list.size() == num) return list;
                }
            }
        }
        for (Movie m : movieList){
            String[] matchs = match.toLowerCase().split(" ");
            for (String s : matchs){
                if (s.length() < 2) continue;
                if (m.getTitle().toLowerCase().contains(s)) {
                    if (!set.contains(m)){
                        set.add(m);
                        list.add(m);
                        if (list.size() == num) return list;
                    }
                    break;
                }
            }
        }
        for (Movie m : movieList){
            String[] matchs = match.toLowerCase().split(" ");
            boolean flag = false;
            for (String s : matchs){
                if (s.length() < 2) continue;
                for (int j = s.length() - 1; j >=0 ; j--){
                    String s1 = s.substring(0, j); 
                    String s2 = s.substring(j);
                    if ((s1.length() >= 2) && (m.getTitle().toLowerCase().contains(s1))) {
                        if (!set.contains(m)){
                            set.add(m);
                            list.add(m);
                            if (list.size() == num) return list;
                        }
                        flag = true;
                        break;
                    }
                    if ((s2.length() >= 2) && (m.getTitle().toLowerCase().contains(s2))) {
                        if (!set.contains(m)){
                            set.add(m);
                            list.add(m);
                            if (list.size() == num) return list;
                        }
                        flag = true;
                        break;
                    }
                }
                if (flag) break;
            }
        }
        return list;
    }
    
    /**
     * get movie lists by user
     * @param user
     * @return
     */
    public List<Movie> getMovieList(User user){
        List<Movie> list = new ArrayList<Movie>();
        Set<Integer> movieIDs = user.getRatings().keySet();
        for (int id : movieIDs){
            list.add(Cache.getCache().getMovie(id));
        }
        return list;
    }
    
    /**
     * get movie lists by user, return the first "num" movies 
     * @param user
     * @param num numbers of movies to be returned
     * @return
     */
    public List<Movie> getMovieList(User user, int num){
        List<Movie> list = new ArrayList<Movie>();
        Set<Integer> movieIDs = user.getRatings().keySet();
        int i = 0;
        for (int id : movieIDs){
            i++;
            list.add(Cache.getCache().getMovie(id));
            if (i == num) return list;
        }
        return list;
    }
}
