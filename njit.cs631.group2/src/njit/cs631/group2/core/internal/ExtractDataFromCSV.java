package njit.cs631.group2.core.internal;

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
 * @version 2016-4-26
 */
public class ExtractDataFromCSV {
    public static List<String[]> extractData(String name) {
        List<String[]> list = new ArrayList<String[]>();
        CsvReader reader = null;
        try {
            reader = new CsvReader(ExtractDataFromCSV.class.getClassLoader().getResourceAsStream(name), Charset.forName("UTF-8"));
            while (reader.readRecord()) {
                String[] str = reader.getValues();
                if (str != null && str.length > 0 && !"".equals(str[0].trim())) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : str){
                        if (s != null && !"".equals(s.trim())) {
                            sb.append(s);
                        }
                    }
                    list.add(sb.toString().replaceAll("\"", "").replaceAll("&amp;", "").split(";"));
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
