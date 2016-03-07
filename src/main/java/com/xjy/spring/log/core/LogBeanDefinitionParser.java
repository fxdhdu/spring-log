package com.xjy.spring.log.core;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class LogBeanDefinitionParser implements BeanDefinitionParser {
	private static final String logAnnotationProcessorBeanName = "com.xjy.core.log.internalLogAnnotationBeanPostProcessor";

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);

		// Register component for the surrounding <task:annotation-driven> element.
		CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), source);
		parserContext.pushContainingComponent(compDefinition);

		// Nest the concrete post-processor bean in the surrounding component.
		BeanDefinitionRegistry registry = parserContext.getRegistry();

		if (registry.containsBeanDefinition(logAnnotationProcessorBeanName)) {
			parserContext.getReaderContext().error(
					"Only one LogAnnotationProcessorBeanName may exist within the context.", source);
		} else {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(
					"com.xjy.core.log.LogAnnotationBeanPostProcessor");
			builder.getRawBeanDefinition().setSource(source);
			registerPostProcessor(parserContext, builder, logAnnotationProcessorBeanName);
		}
		// Finally register the composite component.
		parserContext.popAndRegisterContainingComponent();

		return null;
	}
	
	private static void registerPostProcessor(
			ParserContext parserContext, BeanDefinitionBuilder builder, String beanName) {

		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		parserContext.getRegistry().registerBeanDefinition(beanName, builder.getBeanDefinition());
		BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(), beanName);
		parserContext.registerComponent(new BeanComponentDefinition(holder));
	}

}
