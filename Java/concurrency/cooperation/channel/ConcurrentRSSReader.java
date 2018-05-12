package cooperation.channel;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

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


}
