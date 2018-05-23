package safe;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 该类可能导致内存泄漏
 * @author Kyle
 * @create 2018/5/23 23:39
 */
public class ThreadLocalMemoryLeak extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final static ThreadLocal<Counter> counterHolder = new ThreadLocal<Counter>() {
        @Override
        protected Counter initialValue() {
            Counter tsoCounter = new Counter();
            return tsoCounter;
        }
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
        try (PrintWriter pwr = resp.getWriter()) {
            pwr.printf("Thread %s, counter:%d", Thread.currentThread().getName(), counterHolder.get().getAndIncrement());
        }
    }

    void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counterHolder.get().getAndIncrement();
        // TODO: 省略其他代码
    }

    // 非线程安全
    class Counter {
        private int i = 0;

        public int getAndIncrement() {
            return i++;
        }
    }
}
