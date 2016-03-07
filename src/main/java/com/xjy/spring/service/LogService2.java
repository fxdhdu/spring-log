package com.xjy.spring.service;

import org.springframework.stereotype.Component;

import com.xjy.spring.log.core.Log;

@Component("logService2")
@Log
public class LogService2 {
	
//	@Log
	public void log2() {
		System.out.println("LogService2 --> log2");
	}
	
}
