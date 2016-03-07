package com.xjy.spring.log.core;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogProcessor extends AbstractLogProcessor {
	private static final Logger logger = LoggerFactory.getLogger(DefaultLogProcessor.class);

	@Override
	public Object log(MethodInvocation invocation) throws Throwable {
		Class<?> targetClass = getTargetClass(invocation);
		Method specificMethod = getSpecificMethod(invocation);
		String logDesc = getLogDesc(targetClass, specificMethod);
		logger.info("start execute [{}] method [{}]，desc is [{}]", targetClass, specificMethod, logDesc);
		long start = System.currentTimeMillis();
		Object result = null;
		try {
			result = invocation.proceed();
		} catch (Throwable e) {
			logger.error("execute [" + targetClass + "] method [" + specificMethod + "] error", e);
			throw e;
		}
		long end = System.currentTimeMillis();
		logger.info("end execute [{}] method [{}]，desc is [{}]，cost[{}] ms", targetClass, specificMethod, logDesc, (end - start));
		return result;
	}


}
