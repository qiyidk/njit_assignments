package njit.qiyi.movieRecommendation.core.service;

import java.util.ArrayList;
import java.util.List;

import njit.qiyi.movieRecommendation.core.Movie;
import njit.qiyi.movieRecommendation.core.SimilarityEntry;
import njit.qiyi.movieRecommendation.core.User;
import njit.qiyi.movieRecommendation.core.internal.MovieImpl;
import njit.qiyi.movieRecommendation.core.internal.UserImpl;

/**
 * <p>
 * GetRecommendMovie
 * </p>
 *
 * @author qiyi
 * @version 2015-12-6
 */
public class GetRecommendMovie {

    public List<SimilarityEntry> getRecommendMovieByUser(User user){
        List<SimilarityEntry> list = new ArrayList<SimilarityEntry>();
        UserImpl u = (UserImpl)user;
        list = u.getRecommendMovies();
        return list;
    }
    public List<SimilarityEntry> getRecommendMovieByMovie(Movie movie){
        List<SimilarityEntry> list = new ArrayList<SimilarityEntry>();
        MovieImpl m = (MovieImpl)movie;
        list = m.getSimilarMovies();
        return list;
    }
}
