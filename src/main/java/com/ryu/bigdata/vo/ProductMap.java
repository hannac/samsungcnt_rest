package com.ryu.bigdata.vo;

import java.util.*;

public class ProductMap {
	
	private Map<String, Object> product;
	
	public ProductMap() {
		product = new HashMap<String, Object>();
	}
	
	public void addProductProperty(String key, Object value) {
		product.put(key, value);
	}
	
	
}