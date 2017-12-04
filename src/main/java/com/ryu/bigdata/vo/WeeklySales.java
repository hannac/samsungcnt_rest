package com.ryu.bigdata.vo;

public class WeeklySales {

	private String week;
	private int sales;
	
	public WeeklySales() { }

	public WeeklySales(String week, int sales) {
		super();
		this.week = week;
		this.sales = sales;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	@Override
	public String toString() {
		return "{\"week\":\"" + week + "\", \"sales\":" + sales + "}";
	}

	
	
	
	
	
}
