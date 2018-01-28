package per.zyf.aspect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.JsonObject;

@SuppressWarnings("unchecked")
@Aspect
@Component
public class ControllerAspect {
    
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerLog() {}

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 请求的IP
        String ip = request.getRemoteAddr();
        // 用户代理（用来获取设备信息）
        String userAgent = request.getHeader("User-Agent");
        Enumeration<String> paramName = request.getParameterNames();
        JsonObject paramsJsonObj = new JsonObject();
        while(paramName.hasMoreElements()) {
            String name = paramName.nextElement().toString();
            String value = request.getParameter(name);
            paramsJsonObj.addProperty(name, value);
        }

        // 执行前时间
        Long beforeTime = System.currentTimeMillis();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beforeTimeStr = df.format(beforeTime);

        // 控制台输出
        System.out.println("=====前置通知开始=====");
        System.out.println("请求IP:" + ip);
        System.out.println("客户端:" + userAgent);
        System.out.println("请求时间:" + beforeTimeStr);
        System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
        System.out.println("请求参数:" + paramsJsonObj.toString());
       
        // 执行方法
        Object rtnObj = joinPoint.proceed();
        
        // 执行后时间
        Long afterTime = System.currentTimeMillis();
        
        System.out.println("执行时间:" + (afterTime - beforeTime));
        System.out.println();
        
        return rtnObj;
    }
}
