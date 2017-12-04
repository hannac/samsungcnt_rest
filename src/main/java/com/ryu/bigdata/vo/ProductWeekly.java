package com.ryu.bigdata.vo;

import java.util.List;

public class ProductWeekly extends Product {

	private List<WeeklySales> weeklySales;
	
	public ProductWeekly()
	{
		
	}

	public List<WeeklySales> getWeeklySales() {
		return weeklySales;
	}

	public void setWeeklySales(List<WeeklySales> weeklySales) {
		this.weeklySales = weeklySales;
	}

	public ProductWeekly(long productId, String productCd, int accSales, String brandNm, String releaseDt,
			int retailPrice, String endOfLife, String productNm, String item, String color, String season, String size,
			String fabric, String imageUrl, String crawlUrl, String ownProductYn, float simillarScore, List<WeeklySales> weeklySales) {
		super(productId, productCd, accSales, brandNm, releaseDt, retailPrice, endOfLife, productNm, item, color, season, size,
				fabric, imageUrl, crawlUrl, ownProductYn, simillarScore);
		// TODO Auto-generated constructor stub
		
		this.weeklySales = weeklySales;
	}

}
