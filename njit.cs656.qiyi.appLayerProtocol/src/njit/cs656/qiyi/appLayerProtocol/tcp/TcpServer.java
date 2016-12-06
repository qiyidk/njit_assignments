package njit.cs656.qiyi.appLayerProtocol.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * Simple MultiThread TcpServer
 * </p>
 *
 * @author qiyi
 * @version 2015��10��8��
 */
public class TcpServer {

    public TcpServer(int port) {
        System.out.println("Server is running on port:"+port);
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
            while (true) {
                Socket s = socket.accept();
                if (s != null)
                    startNewThread(s);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
        	try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private void startNewThread(Socket s) {
        Thread4TCPserver thread = new Thread4TCPserver(s);
        thread.run();
    }

    private class Thread4TCPserver extends Thread {
        private Socket s;

        public Thread4TCPserver(Socket s) {
            this.s = s;
        }

        public void run() {
            InputStream input = null;
            BufferedReader in = null;
            DataOutputStream out = null;
            try {
                input = s.getInputStream();
                in = new BufferedReader(
                        new InputStreamReader(input));
                String str = null;
                StringBuilder response = new StringBuilder("Server Response:\n");
                while ((str = in.readLine()) != null) {
                    if (str.equals("exit")) break; // use "exit" as a terminal signal
                    response.append(str.toUpperCase()).append("\n");
                }
                response.append("exit");// append "exit" to to let a client know the data has already been sent 
                out = new DataOutputStream(s.getOutputStream());
                out.writeBytes(response.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                if (in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (s != null){
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
        new TcpServer(port);
    }

}
