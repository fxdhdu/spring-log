package com.xjy.spring.log.core;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

public abstract class AbstractLogProcessor implements LogProcessor {
	
	protected Class<?> getTargetClass(MethodInvocation invocation) {
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		return targetClass;
	}
	
	protected Method getSpecificMethod(MethodInvocation invocation) {
		Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), getTargetClass(invocation));
		specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
		return specificMethod;
	}
	
	protected String getLogDesc(Class<?> targetClass, Method specificMethod) {
		Log log = getLog(targetClass, specificMethod);
		if(log != null) {
			return log.desc();
		}
		return specificMethod.getName();
	}
	
	protected Log getLog(Class<?> targetClass, Method specificMethod) {
		Log log = AnnotationUtils.getAnnotation(specificMethod, Log.class);
		if(log == null) {
			log = AnnotationUtils.findAnnotation(targetClass, Log.class);
		}
		return log;
	}
	
}
