package com.xjy.spring.log.core;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

//@Component
public class LogInterceptor implements MethodInterceptor, Ordered, BeanFactoryAware {
	
	// ===== 经过测试无法注入=======//
//	@Autowired
//	@Qualifier("baseService")
//	private IBaseService baseService;
	// ===== 经过测试无法注入=======//
	private final Map<Method, LogProcessor> logProcessors = new ConcurrentHashMap<Method, LogProcessor>(16);
	private BeanFactory beanFactory;
	private LogProcessor defaultLogProcessor;
	
	public LogInterceptor() {
	}
	
	public LogInterceptor(LogProcessor defaultLogProcessor) {
		this.defaultLogProcessor = defaultLogProcessor;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
		Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
		specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
		LogProcessor logProcessor = determineLogProcessor(specificMethod);
		return logProcessor.log(invocation);
	}
	
	private LogProcessor determineLogProcessor(Method method) {

		LogProcessor logProcessor = this.logProcessors.get(method);
		if (logProcessor == null) {
			logProcessor = this.defaultLogProcessor;
			String qualifier = getExecutorQualifier(method);
			if (StringUtils.hasLength(qualifier)) {
				Assert.notNull(this.beanFactory, "BeanFactory must be set on " + getClass().getSimpleName()
						+ " to access qualified logProcessor '" + qualifier + "'");
				logProcessor = BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.beanFactory, LogProcessor.class, qualifier);
			} else if (logProcessor == null) {
				return null;
			}
			this.logProcessors.put(method, logProcessor);
		}
		return logProcessor;
	}


	private String getExecutorQualifier(Method method) {
		Log log = AnnotationUtils.findAnnotation(method, Log.class);
		if (log == null) {
			log = AnnotationUtils.findAnnotation(method.getDeclaringClass(), Log.class);
		}
		return (log != null ? log.logProcessor() : null);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
