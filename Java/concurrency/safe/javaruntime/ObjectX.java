package safe.javaruntime;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kyle
 * @create 2018/5/25 23:16
 */
public class ObjectX implements Serializable {
    private static final long serialVersionUID = 1L;
    private static AtomicInteger ID_Generator = new AtomicInteger(0);
    private Date timeCreated = new Date();
    private int id;

    public ObjectX() {
        this.id  = ID_Generator.getAndIncrement();
    }

    public void greet(String message) {
        String msg = toString() + ":" + message;
        System.out.println(msg);
    }

    @Override
    public String toString() {
        return "[" + timeCreated + "] ObejectX [" + id + "]";
    }
}
