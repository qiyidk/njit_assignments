package njit.cs656.QMT.movieRecommendation.core.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;


import njit.cs656.QMT.movieRecommendation.core.Movie;

/**
 * <p>
 * MovieSimilarity
 * </p>
 *
 * @author mawenbin
 * @version 2015-11-14
 */
public class MovieSimilarity {
    private int neighborhoodSize = 200;
    public List<Movie> updateMovieSimilarity(List<Movie> movieList){
        
        try {
            URI uri = this.getClass().getClassLoader().getResource("ratings.csv").toURI();
            File file = new File(uri);
            DataModel dataModel = new FileDataModel(file);
            EnhancedLogLikelihoodSimilarity similarity = new EnhancedLogLikelihoodSimilarity(dataModel);
            ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel,
                    similarity);

            for (Movie movie : movieList) {
                try {
                    List<RecommendedItem> similarItems = recommender.mostSimilarItems(movie.getID(), neighborhoodSize);
                    for (RecommendedItem item : similarItems) {
                        ((MovieImpl) movie).setSimilarMovie(
                                new SimilartityEntryImpl((int) item.getItemID(), item.getValue()));
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
            }
          }
          catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        // unimplemented
        return movieList;
    }
}
