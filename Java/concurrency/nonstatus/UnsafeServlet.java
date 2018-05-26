package safe.nonstatus;

import jdk.nashorn.internal.runtime.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 非线程安全的Servlet示例
 *
 * @author Kyle
 * @create 2018/5/26 23:02
 */
public class UnsafeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Servlet是一个实现javax.servlet.Servlet接口的托管类，而不是一个普通类。
     * 所谓托管类，是指Servlet类实例的创建、初始化以及销毁的整个对象生命周期完全是由Java Web服务器控制的，
     * 而服务器为每一个Servlet类最多只生成一个实例，该唯一实例会被用于处理服务器接收到的多个请求，
     * 即一个Servlet类的实例会被多个线程共享，并且服务器调用Servlet.service方法时并没有加锁，
     * 因此使Servlet实例成为无状态对象有利于提高服务器的并发性。
     * 这也是Servlet类一般不包含实例变量或者静态变量的原因：一旦Servlet类包含实例变量或者静态变量，
     * 我们就需要考虑是否使用锁以保障其线程安全。
     * 
     * 如下示例的问题在于：对于实例变量sdf的访问没有加锁，
     * 从而导致sdf.parse(String)调用解析出来的日期可能是一个客户端根本没有提交过的错误日期。
     */
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strExpiryDate = req.getParameter("expirtyDate");
        try {
            sdf.parse(strExpiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
