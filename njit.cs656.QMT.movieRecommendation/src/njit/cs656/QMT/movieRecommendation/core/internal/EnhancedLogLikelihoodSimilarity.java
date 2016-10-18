package njit.cs656.QMT.movieRecommendation.core.internal;

import java.util.Collection;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.RefreshHelper;
import org.apache.mahout.cf.taste.impl.similarity.AbstractItemSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.similarity.PreferenceInferrer;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.stats.LogLikelihood;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * See <a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.14.5962">
 * http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.14.5962</a> and
 * <a href="http://tdunning.blogspot.com/2008/03/surprise-and-coincidence.html">
 * http://tdunning.blogspot.com/2008/03/surprise-and-coincidence.html</a>.
 */
public final class EnhancedLogLikelihoodSimilarity extends AbstractItemSimilarity implements UserSimilarity {

  private static final Logger log = LoggerFactory.getLogger(EnhancedLogLikelihoodSimilarity.class);

  public EnhancedLogLikelihoodSimilarity(DataModel dataModel) {
    super(dataModel);
  }
  
  /**
   * @throws UnsupportedOperationException
   */
  @Override
  public void setPreferenceInferrer(PreferenceInferrer inferrer) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public double userSimilarity(long userID1, long userID2) throws TasteException {

    DataModel dataModel = getDataModel();
    FastIDSet prefs1 = dataModel.getItemIDsFromUser(userID1);
    FastIDSet prefs2 = dataModel.getItemIDsFromUser(userID2);
    
    long prefs1Size = prefs1.size();
    long prefs2Size = prefs2.size();
    long intersectionSize =
        prefs1Size < prefs2Size ? prefs2.intersectionSize(prefs1) : prefs1.intersectionSize(prefs2);
    if (intersectionSize == 0) {
      return Double.NaN;
    }
    long numItems = dataModel.getNumItems();
    double logLikelihood =
        LogLikelihood.logLikelihoodRatio(intersectionSize,
                                         prefs2Size - intersectionSize,
                                         prefs1Size - intersectionSize,
                                         numItems - prefs1Size - prefs2Size + intersectionSize);
    return 1.0 - 1.0 / (1.0 + logLikelihood);
  }
  
  @Override
  public double itemSimilarity(long itemID1, long itemID2) throws TasteException {
    DataModel dataModel = getDataModel();
    PreferenceArray prefs1 = dataModel.getPreferencesForItem(itemID1);
    PreferenceArray prefs2 = dataModel.getPreferencesForItem(itemID2);
    
    double meanValue = (dataModel.getMaxPreference() + dataModel.getMinPreference())/2.0;
    int AandB = 0;
    int AnotB = 0;
    int BnotA = 0;
    int notAandnotB = 0;
    
    if (prefs1 == null) {
      return 0;
    }
    if (prefs2 == null) {
      return 0;
    }
    
    

    int size1 = prefs1.length();
    int size2 = prefs2.length();
    //int count = 0;
    int i = 0;
    int j = 0;
    long userID1 = prefs1.getUserID(0);
    long userID2 = prefs2.getUserID(0);
    while (true) {
      if (userID1 < userID2) {
        if (++i == size1) {
          break;
        }
        userID1 = prefs1.getUserID(i);
      } else if (userID1 > userID2) {
        if (++j == size2) {
          break;
        }
        userID2 = prefs2.getUserID(j);
      } else {
        float valueA = prefs1.getValue(i);
        float valueB = prefs2.getValue(j);
        if(valueA>=meanValue && valueB>=meanValue) {
          AandB++;
        }
        if(valueA<meanValue && valueB<meanValue) {
          notAandnotB++;
        }
        if(valueA>=meanValue && valueB<meanValue) {
          AnotB++;
        }
        if(valueA<meanValue && valueB>=meanValue) {
          BnotA++;
        }

        if (++i == size1 || ++j == size2) {
          break;
        }
        userID1 = prefs1.getUserID(i);
        userID2 = prefs2.getUserID(j);
      }
    }
    double logLikelihood = 
        LogLikelihood.logLikelihoodRatio(AandB,
                                            BnotA,
                                            AnotB,
                                            notAandnotB);
    log.info("AandB {} AnotB {} BnotA {} NotANotB {} logLike {}", AandB, AnotB, BnotA, notAandnotB, logLikelihood);
    
    return 1.0 - 1.0 / (1.0 + logLikelihood);
  }

  @Override
  public double[] itemSimilarities(long itemID1, long[] itemID2s) throws TasteException {
    int length = itemID2s.length;
    double[] result = new double[length];
    for (int i = 0; i < length; i++) {
      result[i] = itemSimilarity(itemID1, itemID2s[i]);
    }
    return result;
  }

  @Override
  public void refresh(Collection<Refreshable> alreadyRefreshed) {
    alreadyRefreshed = RefreshHelper.buildRefreshed(alreadyRefreshed);
    RefreshHelper.maybeRefresh(alreadyRefreshed, getDataModel());
  }
  
  @Override
  public String toString() {
    return "LogLikelihoodSimilarity[dataModel:" + getDataModel() + ']';
  }
  
}
