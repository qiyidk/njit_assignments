package njit.qiyi.movieRecommendation.core.internal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import njit.qiyi.movieRecommendation.core.User;

/**
 * <p>
 * CSVUtil
 * </p>
 *
 * @author qiyi
 * @version 2015-11-13
 */
public class CSVUtil {
    public static List<String[]> extractData(String name) {
        List<String[]> list = new ArrayList<String[]>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(CSVUtil.class.getClassLoader().getResourceAsStream(name), Charset.forName("UTF-8"));
            while (reader.readRecord()) {
                String[] str = reader.getValues();
                if (str != null && str.length > 0) {
                    if (str[0] != null && !"".equals(str[0].trim())) {
                        list.add(str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) reader.close();
        }
        return list;
    }
    public static void writeData(String name, List<User> data){
	CsvWriter writer = null;
	try{
	    writer = new CsvWriter(name);
	    writer.write("userId");
	    writer.write("movieId");
	    writer.write("rating");
	    writer.endRecord();
	    for (User user : data){
		for (HashMap.Entry<Integer, Double> entry : user.getRatings().entrySet()){
		    writer.write(String.valueOf(user.getUserID()));
		    writer.write(String.valueOf(entry.getKey()));
		    writer.write(String.valueOf(entry.getValue()));
		    writer.endRecord();
		}
	    }
			
	}
	catch(Exception e){
	    e.printStackTrace();
	} finally {
            if (writer != null) writer.close();
        }
    }
}
