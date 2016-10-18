package njit.cs656.QMT.movieRecommendation.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;
import njit.cs656.QMT.movieRecommendation.core.internal.Cache;
import njit.cs656.QMT.movieRecommendation.core.service.GetRecommendMovie;
import njit.cs656.QMT.movieRecommendation.core.service.SimilartityEntry;

/**
 * <p>
 * RecommendMovie
 * </p>
 *
 * @author mawenbin
 * @version 2015-11-14
 */
public class RecommendMovie {
    /**
     * getRecommendMovies
     * @param movieList
     * @param n the number of recommend movies
     * @return the first "n" recommend movies
     */
    public List<Movie> getRecommendMovies(List<Movie> movieList, int n){
        HashMap<Integer, Double> total = new HashMap<Integer, Double>();
        for(Movie m:movieList) {
            List<SimilartityEntry> entrys = new GetRecommendMovie().getRecommendMovieByMovie(m);
            for(SimilartityEntry e: entrys) {
                Double d = null;
                if((d = total.get(e.getKey()))!=null) {
                    total.put(e.getKey(), d+e.getSimilarity());
                }
                else {
                    total.put(e.getKey(),e.getSimilarity());
                }
            }
        }
        
        Set<Entry<Integer, Double>> movies = total.entrySet();
        ArrayList<Entry<Integer, Double>> x = new ArrayList<Entry<Integer, Double>>();
        x.addAll(0, movies);
        
        Collections.sort(x, new Comparator<Entry<Integer, Double>>() {  
            public int compare(Entry<Integer, Double> m1, Entry<Integer, Double> m2) {  
                return m1.getValue().compareTo(m2.getValue());  
            }  
        });
        List<Movie> list = new ArrayList<Movie>();
        for (int j = x.size()-1; j >=0 && j>=x.size()-n; j--){
            list.add(Cache.getCache().getMovie(x.get(j).getKey()));
        }
        return list;
    }
    /**
     * get the first 5 recommend movies
     * @param movieList
     * @return
     */
    public List<Movie> getRecommendMovies(List<Movie> movieList){
        return getRecommendMovies(movieList, 5);
    }

    /**
     * getRecommendMovies
     * @param user
     * @param n the number of recommend movies
     * @return the first "n" recommend movies
     */
    public List<Movie> getRecommendMovies(User user, int n){
        List<SimilartityEntry> entry = new GetRecommendMovie().getRecommendMovieByUser(user);
        List<Movie> list = new ArrayList<Movie>();
        for(int i=0; i<n; i++) {
            list.add(Cache.getCache().getMovie(entry.get(i).getKey()));
        }
        return list;
    }
    /**
     * get the first 5 recommend movies
     * @param user
     * @return
     */
    public List<Movie> getRecommendMovies(User user){
        return getRecommendMovies(user, 5);
    }
}
