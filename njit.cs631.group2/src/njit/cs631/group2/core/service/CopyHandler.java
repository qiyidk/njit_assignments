package njit.cs631.group2.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import njit.cs631.group2.core.Copy;
import njit.cs631.group2.core.SQLTemplate;
import njit.cs631.group2.core.SQLTemplate.SQLKeyword;

/**
 * <p>
 * CopyHandler
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class CopyHandler {

    public static synchronized Copy createCopy(String docId, String LIBID, String POSITION){
        SQLTemplate getNextCopyNo = new SQLTemplate(SQLKeyword.QUERY, "Copy");
        HashMap<String, String> condition = new HashMap<String, String>();
        condition.put("LIBID", LIBID);
        condition.put("docId", docId);
        getNextCopyNo.setCondition(condition);
        List<Map<String,String>> copys = SQLHandler.execute(getNextCopyNo);
        int copyNo = 1 + copys.size();
        Copy copy = new Copy(docId, String.valueOf(copyNo), LIBID, POSITION);
        SQLTemplate template = new SQLTemplate(SQLKeyword.INSERT, "COPY");
        String[] values = new String[]{docId, String.valueOf(copyNo), LIBID, POSITION};
        template.setValues(values);
        SQLHandler.execute(template);
        return copy;
    }
}
