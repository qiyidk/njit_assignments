package njit.cs656.QMT.movieRecommendation.core.service;

/**
 * <p>
 * Entry
 * </p>
 *
 * @author qiyi
 * @version 2015-12-7
 */
public interface SimilartityEntry extends Comparable<SimilartityEntry>{
    public int getKey();
    public double getSimilarity();
}
