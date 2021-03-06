package com.ryu.bigdata.vo;

public class CompetitionInfoSelet {
	private String productId;
	private int startYear;
	private int startWeek;
	private int endYear;
	private int endWeek;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(int endWeek) {
		this.endWeek = endWeek;
	}

	public CompetitionInfoSelet(String productId, int startYear, int startWeek, int endYear, int endWeek) {
		super();
		this.productId = productId;
		this.startYear = startYear;
		this.startWeek = startWeek;
		this.endYear = endYear;
		this.endWeek = endWeek;
	}

}
