package njit.cs631.group2.test;

import java.util.List;
import java.util.Map;

import njit.cs631.group2.core.Copy;
import njit.cs631.group2.core.Document;
import njit.cs631.group2.core.internal.ServerInitiate;
import njit.cs631.group2.core.service.CopyHandler;
import njit.cs631.group2.service.DocumentService;
import njit.cs631.group2.service.DocumentService.Operation;

/**
 * <p>
 * Test
 * </p>
 *
 * @author qiyi
 * @version 2016-4-27
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new ServerInitiate().init();
        CopyHandler.createCopy("0375759778", "B01", "xxxx");
        CopyHandler.createCopy("0375759778", "B01", "xxxx");
        CopyHandler.createCopy("0375759778", "B01", "xxxx");
        List<Document> d = DocumentService.getDocument("0375759778", null, null);
        for(Document doc : d){
            System.out.println(doc.getDOCID() + doc.getPDATE());
        }
        System.out.println(DocumentService.process("U0001", "B01", "0375759778", "1", Operation.RESERVE));
        System.out.println(DocumentService.process("U0001", "B01", "0375759778", "2", Operation.CHECKOUT));
        System.out.println(DocumentService.process("U0001", "B01", "0375759778", "1", Operation.CHECKOUT));
        System.out.println(DocumentService.process("U0001", "B01", "0375759778", "2", Operation.RETURN));
        
        System.out.println(DocumentService.computeFine("B01", "0375759778", "1"));
        Map<Copy, String> m = DocumentService.getReservedInfo("U0001");
        for (Copy Copy : m.keySet()){
            System.out.println(Copy.getDocId() + " " + Copy.getCOPYNO() + " " + m.get(Copy));
        }
    }

}
