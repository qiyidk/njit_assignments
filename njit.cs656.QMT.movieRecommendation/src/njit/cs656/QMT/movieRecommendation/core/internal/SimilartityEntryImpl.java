package njit.cs656.QMT.movieRecommendation.core.internal;

import njit.cs656.QMT.movieRecommendation.core.service.SimilartityEntry;

/**
 * <p>
 * EntryImpl
 * </p>
 *
 * @author qiyi
 * @version 2015Äê12ÔÂ7ÈÕ
 */
public class SimilartityEntryImpl implements SimilartityEntry{
    public int key;
    public double similarity;
    public SimilartityEntryImpl(int key, double similarity){
        this.key = key;
        this.similarity = similarity;
    }
    @Override
    public int compareTo(SimilartityEntry o) {
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
