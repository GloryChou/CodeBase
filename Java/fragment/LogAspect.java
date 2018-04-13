package com.dashuju.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

/**
 * @author : LEVE TEAM
 * @date : Created in 2018/1/22 11:22
 */
@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Pointcut("execution(public * com.dashuju.controller..*.*(..)) && !execution(public * com.dashuju.controller..*.imUploadFile(..))")
	public void publishLog() {
	}

	@Around("publishLog()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

		Object rtnObj = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			// 请求方IP
			String ip = request.getRemoteAddr();
			// 若使用Nginx转发HTTP请求，则从"X-Real-IP"属性中获取IP
			if(request.getHeader("X-Real-IP") != null && !request.getHeader("X-Real-IP").isEmpty()) {
				ip = request.getHeader("X-Real-IP");
			}
			// 请求方式
			String method = request.getMethod();
			// 请求资源
			String uri = request.getRequestURI();
			// 用户代理（用来获取设备信息）
			String userAgent = request.getHeader("User-Agent");
			Enumeration<String> paramName = request.getParameterNames();
			StringBuilder sb = new StringBuilder();
			JsonObject paramsJsonObj = new JsonObject();
			while (paramName.hasMoreElements()) {
				String name = paramName.nextElement().toString();
				if (name.equals("apiKey") || name.equals("apiSecret")) {
					continue;
				}
				String value = request.getParameter(name);
				paramsJsonObj.addProperty(name, value);
			}

			// 执行前时间
			Long startTime = System.currentTimeMillis();

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String startTimeStr = df.format(startTime);

			// 执行方法
			rtnObj = joinPoint.proceed();
			// 执行后时间
			Long endTime = System.currentTimeMillis();

			// 拼接日志内容
			final JSONObject logJsonObj = new JSONObject();
			logJsonObj.put("ip", ip);
			logJsonObj.put("method", method);
			logJsonObj.put("userAgent", userAgent);
			logJsonObj.put("uri", uri);
			logJsonObj.put("params", paramsJsonObj.toString());
			logJsonObj.put("runTime", endTime - startTime);
			logJsonObj.put("startTime", startTimeStr);

			logger.info(logJsonObj.toString());
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error(e.getMessage(), e);
		}

		return rtnObj;
	}
}
