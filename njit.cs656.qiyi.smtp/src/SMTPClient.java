import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import sun.misc.BASE64Encoder;

/**
 * <p>
 * SMTPClient
 * </p>
 *
 * @author qiyi
 * @version 2015Äê10ÔÂ22ÈÕ
 */
public class SMTPClient {

    private BASE64Encoder encode = new BASE64Encoder();//encode username and password
    private SSLSocket socket;

    public SMTPClient(String host, int port)
            throws UnknownHostException, IOException {
        // connect to server
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, new TrustManager[] { new MyX509TrustManager() },
                    new SecureRandom());
            SSLSocketFactory sf = context.getSocketFactory();
            socket = (SSLSocket)sf.createSocket(host, port);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws UnknownHostException, IOException {
        // TODO Auto-generated method stub
        MailMessage message = new MailMessage();
        message.setFrom("qiyidk@gmail.com"); // from address
        String[] recipients = new String[1];
        recipients[0] = "yq63@njit.edu";
        message.setTO(recipients); // recipients        
        message.setSubject("testSubject"); //mail subject
        message.setContent("testContent"); //mail content

        String server = "smtp.gmail.com"; //mail server
        message.setUser("qiyidk@gmail.com"); // set userName
        message.setPassword(""); // set password
        int port = 465; // set port
        SMTPClient smtp = new SMTPClient(server, port);
        boolean flag;
        flag = smtp.sendMail(message, server);
        if (flag) {
            System.out.println("Successed to send email");
        } else {
            System.out.println("failed to send email");
        }
    }
    
    private boolean sendMail(MailMessage message, String server) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            helo(server, in, out); // HELO
            authLogin(message, in, out); // AUTH LOGIN
            mailfrom(message.getFrom(), in, out); // MAIL FROM
            rcpt(message.getTO(), in, out); // RCPT
            data(message.getFrom(), message.getTO(),
                    message.getSubject(), message.getContent(), in, out); // DATA
            quit(in, out); // QUIT
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void helo(String server, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        result = getResult(in);
        //status 220 represents connect successfully
        if (result != 220) {
            throw new IOException("failed to connect to the server");
        }
        result = sendServer("HELO " + server, in, out);
        //status 250 represents helo successfully
        if (result != 250) {
            throw new IOException("failed to register to the server");
        }
    }
    
    private int getResult(BufferedReader in) {
        String line = "";
        try {
            line = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //get status code from server
        StringTokenizer st = new StringTokenizer(line, " ");
        return Integer.parseInt(st.nextToken());
    }
    
    private void authLogin(MailMessage message, BufferedReader in,
            BufferedWriter out) throws IOException {
        int result;
        result = sendServer("AUTH LOGIN", in, out);
        if (result != 334) {
            throw new IOException("failed to connect to authentication server");
        }

        result = sendServer(encode.encode(message.getUser().getBytes()), in,
                out);
        if (result != 334) {
            throw new IOException("username is invalid");
        }
        result = sendServer(encode.encode(message.getPassword().getBytes()), in,
                out);

        if (result != 235) {
            throw new IOException("password is invalid");
        }
    }


    private int sendServer(String str, BufferedReader in, BufferedWriter out)
            throws IOException {
        out.write(str);
        out.newLine();
        out.flush();
        return getResult(in);
    }

    private void mailfrom(String source, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        result = sendServer("MAIL FROM:<" + source + ">", in, out);
        if (result != 250) {
            throw new IOException("failed to set mail from");
        }
    }

    private void rcpt(String[] recipientList, BufferedReader in, BufferedWriter out)
            throws IOException {
        int result;
        String recipients = "";
        recipients = recipients + "RCPT TO:";
        for (int i = 0; i < recipientList.length; i++){
            recipients = recipients + "<" + recipientList[i] + ">";
        }
        result = sendServer(recipients, in, out);
        if (result != 250) {
            throw new IOException("failed to set recipients");
        }
    }

    private void data(String from, String[] recipientList, String subject, String content,
            BufferedReader in, BufferedWriter out) throws IOException {
        int result;
        result = sendServer("DATA", in, out);
        if (result != 354) {
            throw new IOException("failed to start sending data");
        }
        out.write("From: " + from);
        out.newLine();
        String recipients = "";
        for (int i = 0; i < recipientList.length; i++){
            recipients = recipients + recipientList[i];
        }
        out.write("To: " + recipients);
        out.newLine();
        out.write("Subject: " + subject);
        out.newLine();
        out.newLine();
        out.write(content);
        out.newLine();
        result = sendServer(".", in, out);
        if (result != 250) {
            throw new IOException("failed to send data");
        }
    }

    private void quit(BufferedReader in, BufferedWriter out) throws IOException {
        int result;
        result = sendServer("QUIT", in, out);
        if (result != 221) {
            throw new IOException("failed to quit");
        }
    }
}
