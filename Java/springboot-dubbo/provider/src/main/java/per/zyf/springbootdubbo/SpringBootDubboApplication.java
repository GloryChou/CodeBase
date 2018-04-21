package per.zyf.springbootdubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("dubbo-provider.xml")
public class SpringBootDubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboApplication.class, args);
    }
}
