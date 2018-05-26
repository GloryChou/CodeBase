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
