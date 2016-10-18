package njit.cs656.QMT.movieRecommendation.core.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;

/**
 * <p>
 * Server initiation service
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class ServerInitiate {
    public void init(){
        System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server is starting...");
        DataBaseConnection connect = new DataBaseConnection();
        // if need, recalculate data
        if (SystemPara.needReCalculate){
            // reset data
            connect.resetAllData(); 
            
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Import movies...");
            // import movies
            String movieFile = "movies.csv";
            List<Movie> movieList = DataImport.importMovie(movieFile);
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Import users...");
            // import users
            String userFile = "ratings.csv";
            List<User> userList = DataImport.importUser(userFile);
            
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Calculate movie and user similarity...");
            // maintain movieSimilarity and userSimilarity
            movieList = new MovieSimilarity().updateMovieSimilarity(movieList);
            userList = new UserSimilarity().updateUserSimilarity(userList);
            
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Update database...");
            // update dataBase
            connect.insertMovies(movieList);
            connect.insertUsers(userList);
        }
        else{
            // set cache
            Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>();
            Map<Integer, User> userMap = new HashMap<Integer, User>();
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Initiate movies...");
            List<Movie> movieList = connect.getMovieList();
            for (Movie m : movieList){
                movieMap.put(m.getID(), m);
            }
            Cache.getCache().setMovieCache(movieMap);
            Cache.getCache().setMovieList(movieList);
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Initiate users...");
            List<User> userList = connect.getUserList();
            for (User u : userList){
                userMap.put(u.getUserID(), u);
            }
            Cache.getCache().setUserCache(userMap);
        }
        // close connection
        connect.close();
    }
}
