package njit.cs656.QMT.movieRecommendation.test;

import java.util.ArrayList;
import java.util.List;

import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.internal.ServerInitiate;
import njit.cs656.QMT.movieRecommendation.service.RecommendMovie;

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
     * backlog
     * 
     * 1.tags
     * 2.recommend algs.
     */

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new ServerInitiate().init();
        List<Movie> movielist = new ArrayList<Movie>();
        List<Movie> list = new RecommendMovie().getRecommendMovies(movielist);
        for (Movie m : list){
            System.out.print(m.getID() + "   " + m.getTitle()+ "   " + m.getIssueTime() + "   ");
            for (String s: m.getGenres()){
                System.out.print(s);
            }
            System.out.println();
        }
    }

}
