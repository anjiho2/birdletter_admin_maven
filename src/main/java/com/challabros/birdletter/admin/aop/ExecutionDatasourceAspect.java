package com.challabros.birdletter.admin.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.util.GenericSignature.ClassSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.challabros.birdletter.admin.define.datasource.ContextHolder;
import com.challabros.birdletter.admin.define.datasource.DataSource;

@Aspect
@Component
@Order(value = 1)
public class ExecutionDatasourceAspect {
	
	final static Logger logger = LoggerFactory.getLogger(ExecutionDatasourceAspect.class);
	
	@Around("execution(public* com.challabros.birdletter.admin.service.*.*(..))")
	public Object doManagerProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("############## do data source aspect ##############");
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		
		DataSource dataSource = method.getAnnotation(DataSource.class);
		if (dataSource != null) {
			ContextHolder.setDataSourceType(dataSource.value());
			logger.info("data source >> " + dataSource.value());
		}
		if(dataSource == null){
			logger.info("default data source.....");
		}
		Object returnValue = joinPoint.proceed();
		ContextHolder.clearDataSourceType();
		
		return returnValue;
	}
}
