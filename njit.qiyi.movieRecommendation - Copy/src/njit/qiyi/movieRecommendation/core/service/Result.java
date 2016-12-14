/**
 * 
 */
package njit.qiyi.movieRecommendation.core.service;

/**
 * @author qiyi
 * result of validation
 */
public interface Result {
    public int size();
    public int correct();
    public double accuracy();
    public String getName();
}
