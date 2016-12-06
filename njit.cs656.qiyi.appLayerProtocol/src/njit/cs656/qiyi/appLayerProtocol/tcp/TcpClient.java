package njit.cs656.qiyi.appLayerProtocol.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>
 * TcpClient
 * </p>
 *
 * @author qiyi
 * @version 2015��10��8��
 */
public class TcpClient {
    
    public TcpClient(String address, int port){

        BufferedReader in = null;
        Socket socket = null;
        try {
            // get information from user input
            System.out.println("Please input your request, type \"exit\" to quit");
            in = new BufferedReader(new InputStreamReader(System.in));
            String str = null;
            StringBuilder input = new StringBuilder("");
            while ((str = in.readLine()) != null){
                if (str.equals("exit")) break;input.append(str).append("\n");
                // append "\n" to let server recognize data as lines
                
            }
            input.append("exit\n");
            // append "exit" to to let a server know the data has already been sent 
            
            // send request
            socket = new Socket(address,port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(input.toString());
            
            // get response and output
            StringBuilder response = new StringBuilder("");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((str = in.readLine()) != null){
                if (str.equals("exit")) break; // use "exit" as a terminal signal
                response.append(str).append("\n");
            }
            System.out.println(response.toString());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String address = "localhost";
        int port = 7171;
        new TcpClient(address, port);
    }
}
