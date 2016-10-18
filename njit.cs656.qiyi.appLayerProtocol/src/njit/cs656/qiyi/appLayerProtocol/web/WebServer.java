package njit.cs656.qiyi.appLayerProtocol.web;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * WebServer
 * </p>
 *
 * @author qiyi
 * @version 2015Äê10ÔÂ9ÈÕ
 */
public class WebServer {

    public WebServer(int port) {
        System.out.println("Server is running on port:" + port);
        Map<String, String> request = new HashMap<String, String>();
        BufferedReader inFromClient = null;
        DataInputStream inFromFile = null;
        PrintStream outToClient = null;
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Socket s = null;
        if (socket != null) {
            while (true) {
                try {
                    s = socket.accept();
                    inFromClient = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    String str = null;
                    // get and parse request
                    while ((str = inFromClient.readLine()) != null) {
                        if (str.startsWith("GET")) {
                            request.put("GET",
                                    str.substring(5, str.length() - 8));
                        } else {
                            char[] chars = str.toCharArray();
                            for (int i = 0; i < chars.length; i++) {
                                if (chars[i] == ':') {
                                    request.put(str.substring(0, i),
                                            str.substring(i + 1));
                                    break;
                                }
                            }
                        }
                        if (str.length() == 0)
                            break; // each request contains a blank line to represent the end of the request
                    }
                    // get resources and response
                    String fileName_original = request.get("GET"); // get request FileName
                    char[] chars = fileName_original.toCharArray();
                    for (int i = 0; i < chars.length; i++) {
                        if (chars[i] == '/')
                            chars[i] = File.separator.toCharArray()[0];
                    }
                    String fileName = new String(chars);
                    String path = System.getProperty("user.dir")
                            + File.separator + "resources" + File.separator
                            + fileName;
                    FileInputStream fileInput = new FileInputStream(path);
                    inFromFile = new DataInputStream(fileInput);
                    outToClient = new PrintStream(s.getOutputStream(), false); // do not flush automatically
                    outToClient.println("HTTP/1.1 200 OK");
                    outToClient
                            .println("Content-Type: application/octet-stream"); // byte stream type
                    outToClient.println(); // the end of header
                    byte[] b = new byte[8096]; // buffer
                    while (inFromFile.read(b) != -1) {
                        outToClient.write(b);
                        b = new byte[8096]; // buffer
                    }
                    outToClient.flush();
                } catch (IOException e) {
                    try {
                        outToClient = new PrintStream(s.getOutputStream(), false);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    outToClient.println("HTTP/1.1 404 Not Found");
                    outToClient.println();
                    outToClient.println("404 Not Found");
                    outToClient.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally

                {
                    if (inFromClient != null) {
                        try {
                            inFromClient.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (inFromFile != null) {
                        try {
                            inFromFile.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (outToClient != null) {
                        outToClient.close();
                    }
                }
                if (s != null) {
                    try {
                        s.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int port = 7171;
        new WebServer(port);
    }

}
