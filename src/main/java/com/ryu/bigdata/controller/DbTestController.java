package com.ryu.bigdata.controller;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ryu.bigdata.mapper.ProductMapper;

@RestController
public class DbTestController {

	
	public static final Logger logger = LoggerFactory.getLogger(DbTestController.class);
	
	@Autowired
	private ProductMapper mapper;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory factory;
	
	@GetMapping(value = "/countAll")
	public Integer countAll() {
		logger.debug("-------------------------------------------------------------");
		logger.debug("mapper:" + mapper);
		logger.debug("-------------------------------------------------------------");
		
		System.out.println("-------------------------------------------------------------");
		System.out.println("ds:" + ds);
		System.out.println("factory:" + factory);
		System.out.println("mapper:" + mapper);
		System.out.println("-------------------------------------------------------------");
		
		return mapper.selectCount(); 
	}
	
}
