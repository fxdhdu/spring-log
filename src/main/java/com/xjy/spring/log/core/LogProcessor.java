package com.xjy.spring.log.core;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 日志处理器
 * @author freeman.xu
 *
 */
public interface LogProcessor {
	
	Object log(MethodInvocation invocation) throws Throwable;
	
}
