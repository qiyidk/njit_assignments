package njit.qiyi.movieRecommendation.test;

import java.util.ArrayList;
import java.util.List;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.DataImport;
import njit.qiyi.movieRecommendation.core.internal.ServerInitiate;
import njit.qiyi.movieRecommendation.core.recommend.Cross_Validation;
import njit.qiyi.movieRecommendation.core.service.Result;
import njit.qiyi.movieRecommendation.service.RecommendMovie;

/**
 * <p>
 * Test
 * </p>
 *
 * @author qiyi
 * @version 2015-11-21
 */
public class Test {
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
/*        new ServerInitiate().init();
        List<Movie> movielist = new ArrayList<Movie>();
        List<Movie> list = new RecommendMovie().getRecommendMovies(movielist);
        for (Movie m : list){
            System.out.print(m.getID() + "   " + m.getTitle()+ "   " + m.getIssueTime() + "   ");
            for (String s: m.getGenres()){
                System.out.print(s);
            }
            System.out.println();
        }*/
        String MovieFile = "movies_test.csv";
        String ratingFile = "ratings_test.csv";
        Cross_Validation cv = new Cross_Validation(5);
        List<Movie> movieList = DataImport.readMovie(MovieFile);
        List<User> users = DataImport.readUser(ratingFile, movieList);
        List<Result> l = cv.validation(users, movieList);
        System.out.println(l.get(0).correct());
        System.out.println(l.get(0).accuracy());
    }

}
