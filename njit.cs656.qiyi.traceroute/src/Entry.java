
import java.util.List;

/**
 * <p>
 * Entry
 * </p>
 *
 * @author qiyi
 * @version 2015Äê9ÔÂ21ÈÕ
 */
public class Entry extends Thread{
    
    private static String destination;
    private static String host;
    private static String sender;
    private static String password;
    private static String[] recipients;
    
    public void run() {
        while (true){
            try{
                TraceRoute tr = new TraceRoute(destination);
                String content = null;
                if (tr.getFaultedRouters() == null) {
                    System.out.println("Cannot connect to the destination:" + destination);
                    content = "Cannot connect to the destination:" + destination;
                    sendEmail(content);
                }
                else if (tr.getFaultedRouters().size() != 0){
                    StringBuilder sb = new StringBuilder(); // the content of the email
                    List<String> routers = tr.getFaultedRouters();
                    sb.append("The faulted routers were:").append("\n\r");
                    for (String router : routers) {
                        sb.append(router).append("\n\r");
                    }
                    sendEmail(sb.toString());
                }
                Thread.sleep(30000); // pause 30s
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        destination = "www.google.com"; // the destination of trace route
        
        // smtp server
        host = "smtp.gmail.com";
        
        // sender
        sender = "qiyidk@gmail.com";
        // password
        password = "";
        // recipients
        recipients = new String[1];
        recipients[0] = "yq63@njit.edu";
        
        // schedule
        Thread t = new Entry();
        t.start();
    }
    
    private void sendEmail(String content){
        MailMange mail = new MailMange(host, sender, recipients, null, null, "Faulted Routers", content);
        mail.sendMail(sender, password);
    }

}
