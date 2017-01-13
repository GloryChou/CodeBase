import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
* Description: Socket Utility
* Scenario: ALL
* @author: glory chou
*/
public class SocketUtils {
	// 字符流读取长度
	final private int STREAMLEN = 1024; 
	
	private String serverip;
	private int serverport;
	
	public String getServerip() {
		return serverip;
	}
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}
	public int getServerport() {
		return serverport;
	}
	public void setServerport(int serverport) {
		this.serverport = serverport;
	}
	
	// Socket访问
	public String accessSocket(String inPacket) {
		// 返回结果
		StringBuilder outPacket = new StringBuilder("");
		
		try {
			// 创建socket对象
			socket = new Socket(this.serverip, this.serverport);
			// 创建输入、输出流对象
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			brin = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
			
			// 传输报文
			pw.println(new String(inPacket.getBytes("GBK"), "GBK"));
			pw.flush();
			
			// 获取返回报文
			char tmp[] = new char[this.STREAMLEN];
			int len;
			while((len = brin.read(tmp, 0, this.STREAMLEN)) > 0) {
				outPacket.append(new String(tmp, 0, len));
			}
		} catch (UnknownHostException e) {
			System.out.println("Socket访问主机出错！");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			System.out.println("Socket数据传输出错！");
			e.printStackTrace();
			return null;
		} finally {
			try {
				// 关闭输入、输出流以及socket连接
				pw.close();
				brin.close();
				socket.close();
			} catch (IOException e) {
				System.out.println("Socket输入输出流关闭时出错！");
				e.printStackTrace();
			}
		}
		
		return outPacket.toString();
	}
}