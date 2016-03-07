package com.xjy.spring.log.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解，默认的日志处理器只是简单的打印了耗时
 * @author freeman.xu
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
	/** key */
	String key() default "";
	/** 日志描述 */
	String desc() default "";
	/** 日志处理器 */
	String logProcessor() default "";
}
