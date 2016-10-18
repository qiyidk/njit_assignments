
/**
 * <p>
 * MailMessage
 * </p>
 *
 * @author qiyi
 * @version 2015Äê10ÔÂ22ÈÕ
 */
public class MailMessage {

    private String from;
    private String[] TO;
    private String subject;
    private String content;
    private String user;
    private String password;
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String[] getTO() {
        return TO;
    }
    public void setTO(String[] tO) {
        TO = tO;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}