import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
* Description: Socket Utility
* Scenario: ALL
* @author: glory chou
*/
public class SocketUtility{

    private final String SERVERIP = "198.98.20.9"; // ip
    private final int SERVERPORT = 6188; // port
    
    /**
     * 
     * @param inPacket 需要Socket传输的报文
     * @return Socket服务器端返回的结果
     */
    public String socketAccess(String inPacket) {
        // 返回结果
        String outpacket = "";
        try {
            Socket socket = new Socket(SERVERIP, SERVERPORT);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader bfout = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Socket传输报文
            pw.println(inPacket);
            pw.flush();
            
            // 获取返回结果
            outpacket = bfout.readLine();
            
            // 关闭输入、输出流和Socket
            pw.close();
            bfout.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return outpacket;
    }
}