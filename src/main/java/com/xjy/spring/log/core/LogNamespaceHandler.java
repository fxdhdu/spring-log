package com.xjy.spring.log.core;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class LogNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		this.registerBeanDefinitionParser("annotation-driven", new LogBeanDefinitionParser());
	}

}
