
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * <p>
 * MailMange based on Mail.jar
 * </p>
 *
 * @author qiyi
 * @version 2015Äê9ÔÂ26ÈÕ
 */

public class MailMange {

    private Session session; // session
    private Message msg; // message

    private InternetAddress fromUsers;
    private InternetAddress[] recipientTO;
    private InternetAddress[] recipientCC;
    private InternetAddress[] recipientBCC;
    private String mailContent;
    private String mailType = "text/html;charset=GBK";
    private String host;

    /**
     * initialize email
     * @param host source email address
     * @param from from user
     * @param to   to user
     * @param cc   cc user
     * @param bcc  bcc user   
     * @param subject email subject
     * @param content email content
     * 
     */
    public MailMange(String host, String from, String[] to, String[] cc, String[] bcc,
            String subject, String content) {
        
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.transport.protocol", "SMTP");
        this.host = host;
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.port", "465");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.socketFactory.fallback", "false");

        // create session
        session = Session.getInstance(p);
        // create message  
        msg = new MimeMessage(session);
        
        try {
            fromUsers = new InternetAddress(from);
            msg.setFrom(fromUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            recipientTO = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                recipientTO[i] = new InternetAddress(to[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, recipientTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (cc != null){
                recipientCC = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; i++) {
                    recipientCC[i] = new InternetAddress(cc[i]);
                }
                msg.addRecipients(Message.RecipientType.CC, recipientCC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bcc != null){
                recipientBCC = new InternetAddress[bcc.length];
                for (int i = 0; i < bcc.length; i++) {
                    recipientBCC[i] = new InternetAddress(bcc[i]);
                }
                msg.addRecipients(Message.RecipientType.BCC, recipientBCC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            msg.setSubject(subject); 
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            this.mailContent = content;
            msg.setContent(mailContent, mailType);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * send mail
     * 
     * @param username
     * @param password
     */
    public void sendMail(String username, String password) {
        if (recipientTO == null || msg == null || session == null) {
            System.err.println("sorry....something is wrong\n "
                    + "recipientTO = " + recipientTO + "\n msg = " + msg
                    + "\n session = " + session);
            return;
        }

        // authentication  
        try {
            // set mail
            Transport tran = session.getTransport("smtp");
            tran.connect(host, username, password);
            Address[] a = msg.getAllRecipients();
            tran.sendMessage(msg, a);
            System.out.println("send email...");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
