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
	// 字符流读取长度
	final private int STREAMLEN = 1024; 

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
			BufferedReader brin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			// Socket传输报文
			pw.println(inPacket);
			pw.flush();

			// 临时存储区
			char tmp[] = new char[this.STREAMLEN];
			int len;
			
			while(true) {
				len = brin.read(tmp , 0, this.STREAMLEN);
				if(len != -1)
				{
					outpacket.append(new String(tmp, 0, len));
				}
				// 读到输入流结尾或者临时存储区读不满则退出此次数据读取
				if(len == -1 || len < this.STREAMLEN) break;
			}
			
			// 关闭输入、输出流和Socket
			pw.close();
			brin.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return outpacket;
	}
}