package per.gc.nettyserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Sharable��ʾ�˶�����channel�乲��
 * handler�������ǵľ���ҵ����
 * */
@Sharable //ע��@Sharable����������channels�乲��
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	public void ChannelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("server received data :" + msg);
		ctx.write(msg); // д������
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE); //flush������д�ص�����,��flush��ɺ�ر�channel
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace(); // ��׽�쳣��Ϣ
		ctx.close(); // �����쳣ʱ�ر�channel 
	}
}
