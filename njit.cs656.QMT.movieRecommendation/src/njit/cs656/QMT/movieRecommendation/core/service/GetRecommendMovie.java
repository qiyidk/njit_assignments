package njit.cs656.QMT.movieRecommendation.core.service;

import java.util.ArrayList;
import java.util.List;
import njit.cs656.QMT.movieRecommendation.core.Movie;
import njit.cs656.QMT.movieRecommendation.core.User;
import njit.cs656.QMT.movieRecommendation.core.internal.MovieImpl;
import njit.cs656.QMT.movieRecommendation.core.internal.UserImpl;

/**
 * <p>
 * GetRecommendMovie
 * </p>
 *
 * @author qiyi
 * @version 2015-12-6
 */
public class GetRecommendMovie {

    public List<SimilartityEntry> getRecommendMovieByUser(User user){
        List<SimilartityEntry> list = new ArrayList<SimilartityEntry>();
        UserImpl u = (UserImpl)user;
        list = u.getRecommendMovies();
        return list;
    }
    public List<SimilartityEntry> getRecommendMovieByMovie(Movie movie){
        List<SimilartityEntry> list = new ArrayList<SimilartityEntry>();
        MovieImpl m = (MovieImpl)movie;
        list = m.getSimilarMovies();
        return list;
    }
}
