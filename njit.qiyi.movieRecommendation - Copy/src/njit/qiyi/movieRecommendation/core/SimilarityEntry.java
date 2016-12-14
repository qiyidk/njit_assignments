package njit.qiyi.movieRecommendation.core;

/**
 * <p>
 * Entry
 * </p>
 *
 * @author qiyi
 * @version 2015-12-7
 */
public interface SimilarityEntry extends Comparable<SimilarityEntry>{
    public int getKey();
    public double getSimilarity();
}
