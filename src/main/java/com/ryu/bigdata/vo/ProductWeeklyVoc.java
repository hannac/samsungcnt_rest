package com.ryu.bigdata.vo;

import java.util.List;

public class ProductWeeklyVoc extends ProductWeekly {
	
	private List<Voc> vocList;
	
	public ProductWeeklyVoc()
	{
		
	}

	public List<Voc> getVocList() {
		return vocList;
	}

	public void setVocList(List<Voc> vocList) {
		this.vocList = vocList;
	}

	public ProductWeeklyVoc(long productId, String productCd, int accSales, String brandNm, String releaseDt,
			int retailPrice, String endOfLife, String productNm, String item, String color, String season, String size,
			String fabric, String imageUrl, String crawlUrl, String ownProductYn, float simillarScore,
			List<WeeklySales> weeklySales, List<Voc> vocList) {
		super(productId, productCd, accSales, brandNm, releaseDt, retailPrice, endOfLife, productNm, item, color, season, size,
				fabric, imageUrl, crawlUrl, ownProductYn, simillarScore, weeklySales);
		// TODO Auto-generated constructor stub
		
		this.vocList = vocList;
	}
}
