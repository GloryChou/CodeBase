package cooperation.channel;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author Kyle
 * @create 2018/5/11 22:27
 */
public class ConcurrentRSSReader {
    public static void main(String[] args) throws Exception {
        final int argc = args.length;
        String url = argc > 0 ? args[0] : "http://lorem-rss.herokuapp.com/feed";

        // 从网络加载RSS中的数据

        // 从输入输出流中解析XML数据
    }

    private static Document parseXML(InputStream in)
            throws ParserConfigurationException, SAXException, IOException {
        // TODO:
        return null;
    }

    private static InputStream loadRSS(final String url) throws IOException {
        final PipedInputStream in = new PipedInputStream();
        // 以in为参数创建PipedOutputStream实例
        final PipedOutputStream out = new PipedOutputStream(in);

        Thread workerThread = new Thread(() -> {
            try {
                doDownload(url, out);
            } catch (Exception e) {

            }
        }, "rss-loader");

        workerThread.start();
        return in;
    }

    static BufferedInputStream issueRequest(String url) throws Exception {
        // TODO:
        return null;
    }

    static void doDownload(String url, OutputStream out) throws Exception {
        ReadableByteChannel readChannel = null;
        WritableByteChannel writeChannel = null;

        try {
            // 对指定URL发起HTTP请求
            BufferedInputStream in = issueRequest(url);
            readChannel = Channels.newChannel(in);
            ByteBuffer buf = ByteBuffer.allocate(1024);
            writeChannel = Channels.newChannel(out);
            while (readChannel.read(buf) > 0) {
                buf.flip();
                writeChannel.write(buf);
                buf.clear();
            }
        } finally {

        }
    }
}
