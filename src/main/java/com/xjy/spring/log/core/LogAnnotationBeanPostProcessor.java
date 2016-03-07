package com.xjy.spring.log.core;

import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

@SuppressWarnings("serial")
public class LogAnnotationBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements BeanFactoryAware {
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		LogAnnotationAdvisor advisor = new LogAnnotationAdvisor(new DefaultLogProcessor());
		advisor.setBeanFactory(beanFactory);
		this.advisor = advisor;
	}
	
}
