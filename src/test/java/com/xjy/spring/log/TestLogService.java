package com.xjy.spring.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xjy.spring.service.LogService;
import com.xjy.spring.service.LogService2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestLogService {
	@Autowired
	@Qualifier("logService")
	private LogService logService;
	
	@Autowired
	@Qualifier("logService2")
	private LogService2 logService2;
	
	@Test
	public void test() {
		logService.log1();
		logService.log2();
		logService2.log2();
	}
}
