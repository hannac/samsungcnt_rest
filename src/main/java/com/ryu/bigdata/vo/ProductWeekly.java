package com.ryu.bigdata.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductWeekly extends Product {
	private List<WeeklySales> weeklySales;
	public void setWeeklySales(List<WeeklySales> weeklySales) {
		this.weeklySales = weeklySales;
	}
}
