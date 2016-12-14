package njit.qiyi.movieRecommendation.core.internal;

import njit.qiyi.movieRecommendation.core.SimilarityEntry;

/**
 * <p>
 * EntryImpl
 * </p>
 *
 * @author qiyi
 * @version 2015-12-7
 */
public class SimilartityEntryImpl implements SimilarityEntry{
    public int key;
    public double similarity;
    public SimilartityEntryImpl(int key, double similarity){
        this.key = key;
        this.similarity = similarity;
    }
    @Override
    public int compareTo(SimilarityEntry o) {
        // TODO Auto-generated method stub
        if (this.similarity > o.getSimilarity()) return 1;
        else if (this.similarity < o.getSimilarity()) return -1;
        return 0;
    }
    @Override
    public int getKey() {
        // TODO Auto-generated method stub
        return key;
    }
    @Override
    public double getSimilarity() {
        // TODO Auto-generated method stub
        return similarity;
    }
}
