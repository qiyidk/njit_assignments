package njit.cs656.QMT.movieRecommendation.core.internal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

/**
 * <p>
 * ExtractDataFromCSV
 * </p>
 *
 * @author qiyi
 * @version 2015-11-13
 */
public class ExtractDataFromCSV {
    public static List<String[]> extractData(String name) {
        List<String[]> list = new ArrayList<String[]>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(ExtractDataFromCSV.class.getClassLoader().getResourceAsStream(name), Charset.forName("UTF-8"));
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
            if (reader != null)
                reader.close();
        }
        return list;
    }
}
