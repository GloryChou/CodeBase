package per.gc.nettyserver;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	private static final int port = 8080;
	
	public void start() throws InterruptedException {
		ServerBootstrap b = new ServerBootstrap(); // ������������
		EventLoopGroup group = new NioEventLoopGroup(); // ͨ����������ʽ(NIO)���������Ӻʹ�������
		
		try {
			b.group(group);
			b.channel(NioServerSocketChannel.class); // ����NIO���͵�channel
			b.localAddress(new InetSocketAddress(port)); // ���ü����˿�
			b.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception { //�����ӵ���ʱ�ᴴ��һ��channel
					// pipeline����channel�е�Handler����channel���������һ��handler������ҵ��
					ch.pipeline().addLast("myHandler", new EchoServerHandler());
				} 
			});
			
			ChannelFuture f = b.bind().sync(); // ������ɣ���ʼ��server��ͨ������syncͬ����������ֱ���󶨳ɹ�
			System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
			f.channel().closeFuture().sync(); // Ӧ�ó����һֱ�ȴ���ֱ��channel�ر�
		} catch (Exception e) {
			e.printStackTrace();
	    } finally {
	    	group.shutdownGracefully().sync(); // �ر�EventLoopGroup���ͷŵ�������Դ�����������߳�
	    }

	}
	public static void main(String[] args) {
		try {
			new EchoServer().start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
