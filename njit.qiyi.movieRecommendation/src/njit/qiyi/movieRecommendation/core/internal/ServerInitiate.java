package njit.qiyi.movieRecommendation.core.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.recommend.Cross_Validation;
import njit.qiyi.movieRecommendation.core.recommend.NeighborhoodRecommend;
import njit.qiyi.movieRecommendation.core.recommend.SimilarityMetric;
import njit.qiyi.movieRecommendation.core.service.Result;

/**
 * <p>
 * Server initiation service
 * </p>
 *
 * @author qiyi
 * @version 2015-11-14
 */
public class ServerInitiate {
    private String userFile = "ratings.csv";
    private String movieFile = "movies.csv";
    private String testMovieFile = "movies_test.csv";
    private String testRatingFile = "ratings_test.csv";
    
    public void init(){
        System.out.println(new Date(System.currentTimeMillis()).toString() + ": The server is starting...");
        DataBaseConnection connect = new DataBaseConnection();
        // if need, recalculate data
        if (SystemPara.needReCalculate){
            // reset data
            connect.resetAllData(); 
            
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Import movies...");
            // import movies
            
            List<Movie> movieList = DataImport.importMovie(movieFile);
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Import users...");
            // import users
            
            List<User> userList = DataImport.importUser(userFile);
            
            if (SystemPara.cross_validation){
        	System.out.println(new Date(System.currentTimeMillis()).toString() + ": Do Cross Validation...");

                Cross_Validation cv = new Cross_Validation(5);
                List<Movie> movieList2 = DataImport.readMovie(testMovieFile);
                List<User> users = DataImport.readUser(testRatingFile, movieList2);
                List<Result> l = cv.validation(users, movieList2);
                System.out.printf("%-30s%-30s%-30s", "Name" , "Correct/Total" ,"Accuracy");
        	System.out.println();
                for (Result r : l){
                    System.out.printf("%-30s%-30s%-30s", r.getName() , r.correct() + "/" +  r.size(), Math.round(r.accuracy() * 10000) * 1.0 / 100 + "%");
                    System.out.println();
                }
            }
            System.out.println(new Date(System.currentTimeMillis()).toString() + ": Calculate movie similarity...");
            // maintain movieSimilarity  
            new NeighborhoodRecommend(userFile).setItemNeighborhood(movieList, SimilarityMetric.PEARSON_CORRELATION);
            
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
