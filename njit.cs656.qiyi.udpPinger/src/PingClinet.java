import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * PingClinet
 * </p>
 *
 * @author qiyi
 * @version 2015��10��24��
 */
public class PingClinet {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	DatagramSocket socket = null;
        try{
            InetAddress address = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// set date format 
            for (int i = 0; i < 10; i++){
                Date date1 = new Date();
                String time1 = df.format(date1);
                String str =  "PING " + i + " "+ time1 + " \r\n";
                System.out.print(str);
                byte[] sendBuf = str.getBytes();
                byte[] receiveBuf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 80);
                socket.send(packet);
                DatagramPacket receivePakcet = new DatagramPacket(receiveBuf, 1024);;
                socket.setSoTimeout(5000);// set 5000ms timeout
                try{
                    socket.receive(receivePakcet);
                    Date date2 = new Date();
                    String time2 = df.format(date2);
                    System.out.println("receive time: " + time2 + "  RTT: "+ (date2.getTime() - date1.getTime()) + "ms");
                }
                catch(Exception e){
                    System.out.println("timeout");
                }
                Thread.sleep(100);// the interval of each ping is 100ms
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
        	socket.close();
        }
    }

}
