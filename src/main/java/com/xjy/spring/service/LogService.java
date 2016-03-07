package com.xjy.spring.service;

import org.springframework.stereotype.Component;

import com.xjy.spring.log.core.Log;

@Component("logService")
public class LogService {
	
	@Log
	public String log1() {
		System.out.println("logService->log1");
		return "logService->log1";
	}
	
	public void log2() {
		System.out.println("logService->log2");
	}
	
}
