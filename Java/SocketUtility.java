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

	// Socket超时时间
	final private int TIMEOUT = 1000;
	
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
		
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader brin = null;

		try {
			// 创建socket对象
			socket = new Socket(this.serverip, this.serverport);
			socket.setSoTimeout(this.TIMEOUT);
			// 创建输入、输出流对象
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			// 用BufferedReader读取中文计算长度时，一个中文字符长度为1
			// 若要读取字节流，最好使用：new DataInputStream(socket.getInputStream())
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
		} catch (UnknownHostException uhe) {
			System.out.println("Socket访问主机出错！");
			System.out.println(uhe.getMessage());
			uhe.printStackTrace();
			return null;
		} catch (SocketTimeoutException ste) {
			System.out.println("Socket连接超时！");
			System.out.println(ste.getMessage());
			ste.printStackTrace();
			return null;
		} catch (IOException ioe) {
			System.out.println("Socket数据传输出错！");
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
			return null;
		} finally {
			try {
				// 关闭输入、输出流以及socket连接
				if(pw != null) pw.close();
				if(brin != null) brin.close();
				if(socket != null) socket.close();
			} catch (IOException e) {
				System.out.println("Socket输入输出流关闭时出错！");
				e.printStackTrace();
			}
		}
		
		return outPacket.toString();
	}

	/**
	 * Socket访问，返回报文中标识了报文长度
	 * @param inPacket: 报文
	 * @param pktlen: 返回报文中，标识报文长度的字符的字节数
	 **/
	public String accessSocketWithPktlen(String inPacket, int pktlen) {
		StringBuilder outPacket = new StringBuilder("");
		
		Socket socket = null;
		PrintWriter pw = null;
		DataInputStream dis = null;
		
		try {
			// 创建socket对象
			socket = new Socket(this.serverip, this.serverport);
			socket.setSoTimeout(this.TIMEOUT);
			// 创建输入、输出流对象
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			dis = new DataInputStream(socket.getInputStream());
			
			// 传输报文
			pw.println(new String(inPacket.getBytes("GBK"), "GBK"));
			pw.flush();
			
			// 获取返回报文的长度
			byte[] lenbyte = new byte[pktlen];
			dis.read(lenbyte, 0, pktlen);
			int pktlenint = Integer.parseInt(new String(lenbyte));
			
			// 获取报文正文
			byte[] mainpktbytes = new byte[pktlenint];
			dis.read(mainpktbytes, 0, pktlenint);
			outPacket.append(new String((mainpktbytes), "GBK"));
		} catch (UnknownHostException uhe) {
			System.out.println("Socket访问主机出错！");
			System.out.println(uhe.getMessage());
			uhe.printStackTrace();
			return null;
		} catch (SocketTimeoutException ste) {
			System.out.println("Socket连接超时！");
			System.out.println(ste.getMessage());
			ste.printStackTrace();
			return null;
		} catch (IOException ioe) {
			System.out.println("Socket数据传输出错！");
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
			return null;
		} finally {
			try {
				// 关闭输入、输出流以及socket连接
				if(pw != null) pw.close();
				if(dis != null) dis.close();
				if(socket != null) socket.close();
			} catch (IOException e) {
				System.out.println("Socket输入输出流关闭时出错！");
				e.printStackTrace();
			}
		}
		
		return outPacket.toString();
	}
}