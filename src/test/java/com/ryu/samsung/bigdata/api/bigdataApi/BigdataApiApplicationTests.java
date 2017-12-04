package com.ryu.samsung.bigdata.api.bigdataApi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ryu.bigdata.BigdataApiApplication;
import com.ryu.bigdata.controller.RestApiController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BigdataApiApplication.class)
public class BigdataApiApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
	RestApiController cont;
	
	@Test
	public void testStartDate() {
		cont.getWeekofYear("20171114");
		System.out.println("==============");
	}

}
