package com.xjy.spring.log.core;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

@SuppressWarnings("serial")
public class LogAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
	
	private Advice advice;

	private Pointcut pointcut;
	
	public LogAnnotationAdvisor(LogProcessor logProcessor) {
		Set<Class<? extends Annotation>> logAnnotationTypes = new LinkedHashSet<Class<? extends Annotation>>(2);
		logAnnotationTypes.add(Log.class);
		this.advice = buildAdvice(logProcessor);
		this.pointcut = buildPointcut(logAnnotationTypes);
	}
	
	protected Advice buildAdvice(LogProcessor logProcessor) {
		return new LogInterceptor(logProcessor);
	}
	
	protected Pointcut buildPointcut(Set<Class<? extends Annotation>> logAnnotationTypes) {
		ComposablePointcut result = null;
		for (Class<? extends Annotation> logAnnotationType : logAnnotationTypes) {
			Pointcut cpc = new AnnotationMatchingPointcut(logAnnotationType, true);
			Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(logAnnotationType);
			if (result == null) {
				result = new ComposablePointcut(cpc).union(mpc);
			}
			else {
				result.union(cpc).union(mpc);
			}
		}
		return result;
	}
	
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	@Override
	public Advice getAdvice() {
		return this.advice;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (this.advice instanceof BeanFactoryAware) {
			((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
		}
	}

}
