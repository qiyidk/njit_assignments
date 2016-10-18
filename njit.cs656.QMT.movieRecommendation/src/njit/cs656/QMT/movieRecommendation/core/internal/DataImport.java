package njit.cs656.QMT.movieRecommendation.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;

/**
 * <p>
 * DataImport
 * </p>
 *
 * @author qiyi
 * @version 2015-11-29
 */
public class DataImport {
    private static class MovieRatingCount{
        private double sum = 0;
        private int count = 0;
    }
    public static List<Movie> importMovie(String FileName){
        List<String[]> data = ExtractDataFromCSV.extractData(FileName);
        List<Movie> movies = new ArrayList<Movie>();
        Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>();
        for (int i = 1; i < data.size(); i++){
            String[] s = data.get(i);
            MovieImpl movie = new MovieImpl();
            movie.setId(Integer.parseInt(s[0]));
            int index = 0;
            for (int j = 0; j < s[1].length(); j++){
                if ((s[1].charAt(j) == '(') && (s[1].charAt(j + 5) == ')') &&
                        ((s[1].charAt(j + 1) == '1') || (s[1].charAt(j + 1) == '2'))){
                    index = j;
                    break;
                }
            }
            String title = s[1].substring(0, index).split(",")[0];
            String issueTime = index == 0? "0" :s[1].substring(index + 1, index + 5);
            movie.setTitle(title);
            movie.setIssueTime(Integer.parseInt(issueTime));
            String[] genres = s[2].split("\\|");  // notice: | is a regular expression meta-character
            List<String> l = new ArrayList<String>();
            for (String g: genres) l.add(g);
            movie.setGenres(l);
            movieMap.put(movie.getID(), movie);
            movies.add(movie);
        }
        // set cache
        Cache.getCache().setMovieList(movies);
        Cache.getCache().setMovieCache(movieMap);
        return movies;
    }
    public static List<User> importUser(String FileName){
        List<String[]> data = ExtractDataFromCSV.extractData(FileName);
        List<User> users = new ArrayList<User>();
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        Map<Integer, MovieRatingCount> ratingMap = new HashMap<Integer, MovieRatingCount>();
        UserImpl user = null;
        int lastUser = -1; // the id of last scanned user 
        int id = -1;
        for (int i = 1; i < data.size(); i++){
            String[] s = data.get(i);
            id = Integer.parseInt(s[0]);
            if (id != lastUser){
                if (user != null) {
                    userMap.put(lastUser, user);
                    users.add(user);
                }
                user = new UserImpl();
                lastUser = id;
                user.setId(id);
            }
            int movieid = Integer.parseInt(s[1]);
            double rating = Double.parseDouble(s[2]);
            // calculate average rating
            if (ratingMap.containsKey(movieid)){
                MovieRatingCount count = ratingMap.get(movieid);
                count.count++;
                count.sum = count.sum + rating;
            }
            else{
                MovieRatingCount count = new MovieRatingCount();
                ratingMap.put(movieid, count);
                count.count = 1;
                count.sum = rating;
            }
            user.setRating(movieid, rating);
        }
        // deal with the last user
        if (user != null) {
            userMap.put(id, user);
            users.add(user);
        }
        // set cache
        for (Movie movie : Cache.getCache().getMovieList()){
            MovieImpl m = (MovieImpl) movie;
            MovieRatingCount count = ratingMap.get(m.getID());
            double avg = (count == null) || (count.count == 0) ? 0 : count.sum / count.count;
            m.setAvgRating(avg);
        }
        Cache.getCache().setUserCache(userMap);
        return users;
    }
}
