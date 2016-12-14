/**
 * 
 */
package njit.qiyi.movieRecommendation.core.recommend;


/**
 * @author qiyi
 *
 */
public enum SimilarityMetric {
    LOGLIKELIHOOD("LOGLIKELIHOOD"),
    PEARSON_CORRELATION("PEARSON_CORRELATION"),
    COSINE("COSINE");
    private String name;
    private SimilarityMetric(String name){
	this.name = name;
    }
    public String getName(){
	return name;
    }
}
