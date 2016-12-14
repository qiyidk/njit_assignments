/**
 * 
 */
package njit.qiyi.movieRecommendation.core.internal;

import njit.qiyi.movieRecommendation.core.service.Result;

/**
 * @author qiyi
 *
 */
public class ResultImpl implements Result {
    private int size;
    private int correct;
    private String name;
    public ResultImpl(String name, int size, int correct){
	this.size = size;
	this.correct = correct;
	this.name = name;
    }

    @Override
    public int size() {
	// TODO Auto-generated method stub
	return size;
    }

    @Override
    public int correct() {
	// TODO Auto-generated method stub
	return correct;
    }

    @Override
    public double accuracy() {
	// TODO Auto-generated method stub
	return 1.0 * correct / size;
    }

    @Override
    public String getName() {
	// TODO Auto-generated method stub
	return name;
    }

}
