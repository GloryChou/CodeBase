import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.core.io.ClassPathResource;

/*
* Description: Configurations Reader
* Scenario: Web Project
* @author: glory chou
*/
public class Configuration implements ServletContextListener {
    private final static String FILEPAHT = "conf.properties";    // 配置文件路径
    public static Map<String,String> confmap;            // 配置读取后存储在Map中

    /****** ServletContext生命周期开始时执行方法 ******/
    public void contextInitialized(ServletContextEvent event) {
        initConfMap();
    }
    
    /****** ServletContext生命周期结束时执行方法 ******/
    public void contextDestroyed(ServletContextEvent arg0) {
    }
    
    @SuppressWarnings("rawtypes")
    public void initConfMap() {
        if(confmap==null){
            confmap = new HashMap<String,String>();
        }
        try {
            ClassPathResource cr = new ClassPathResource(FILEPAHT);
            // 读取属性文件
            InputStream inStream = cr.getInputStream();
            Properties props = new Properties();
            props.load(inStream);
            
            Set set = props.keySet();
            for (Object obj : set) {
                if(props.get(obj)!=null && String.valueOf(props.get(obj)).trim().length()>0){
                    confmap.put(String.valueOf(obj).trim(),String.valueOf(props.get(obj)).trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
