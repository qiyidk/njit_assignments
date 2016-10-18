package njit.cs656.QMT.movieRecommendation.core.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import njit.cs656.QMT.movieRecommendation.core.User;

/**
 * <p>
 * UserSimilarity
 * </p>
 *
 * @author mawenbin
 * @version 2015-11-14
 */
public class UserSimilarity {
    private int neighborhoodSize = 100;
    private int recommendeeSize = 100;
    
    public List<User> updateUserSimilarity(List<User> userList){
        try {
            URI uri = this.getClass().getClassLoader().getResource("ratings.csv").toURI();
            File file = new File(uri);
            DataModel dataModel = new FileDataModel(file);
            org.apache.mahout.cf.taste.similarity.UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);

            UserNeighborhood neighborhood = new NearestNUserNeighborhood(neighborhoodSize, userSimilarity, dataModel);
            Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, userSimilarity);
            for(User user: userList) {
                List<RecommendedItem> recommendations = recommender.recommend(user.getUserID(), recommendeeSize);
                for(RecommendedItem item : recommendations) {
                    ((UserImpl) user).setRecommendMovie(new SimilartityEntryImpl((int)item.getItemID(), item.getValue()));
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
        //unimplemented
        return userList;
    }
}
