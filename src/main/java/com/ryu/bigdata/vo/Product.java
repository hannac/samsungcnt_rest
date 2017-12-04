package com.ryu.bigdata.vo;

public class Product {
	private long productId;
	private String productCd;
	private int accSales;
	private String brandNm;
	private String releaseDt;
	private int retailPrice;
	private String endOfLife;
	private String productNm;
	private String item;
	private String color;
	private String season;
	private String size;
	private String fabric;
	private String imageUrl;
	private String crawlUrl;
	private String ownProductYn;
	private float simillarScore;
	private String fileNm;

	public Product() {

	}

	public Product(long productId, String productCd, int accSales, String brandNm, String releaseDt, int retailPrice,
			String endOfLife, String productNm, String item, String color, String season, String size, String fabric,
			String imageUrl, String crawlUrl, String ownProductYn, float simillarScore) {
		super();
		this.productId = productId;
		this.productCd = productCd;
		this.accSales = accSales;
		this.brandNm = brandNm;
		this.releaseDt = releaseDt;
		this.retailPrice = retailPrice;
		this.endOfLife = endOfLife;
		this.productNm = productNm;
		this.item = item;
		this.color = color;
		this.season = season;
		this.size = size;
		this.fabric = fabric;
		this.imageUrl = imageUrl;
		this.crawlUrl = crawlUrl;
		this.ownProductYn = ownProductYn;
		this.simillarScore = simillarScore;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public int getAccSales() {
		return accSales;
	}

	public void setAccSales(int accSales) {
		this.accSales = accSales;
	}

	public String getBrandNm() {
		return brandNm;
	}

	public void setBrandNm(String brandNm) {
		this.brandNm = brandNm;
	}

	public String getReleaseDt() {
		return releaseDt;
	}

	public void setReleaseDt(String releaseDt) {
		this.releaseDt = releaseDt;
	}

	public int getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getEndOfLife() {
		return endOfLife;
	}

	public void setEndOfLife(String endOfLife) {
		this.endOfLife = endOfLife;
	}

	public String getProductNm() {
		return productNm;
	}

	public void setProductNm(String productNm) {
		this.productNm = productNm;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCrawlUrl() {
		return crawlUrl;
	}

	public void setCrawlUrl(String crawlUrl) {
		this.crawlUrl = crawlUrl;
	}

	public String getOwnProductYn() {
		return ownProductYn;
	}

	public void setOwnProductYn(String ownProductYn) {
		this.ownProductYn = ownProductYn;
	}

	public float getSimillarScore() {
		return simillarScore;
	}

	public void setSimillarScore(float simillarScore) {
		this.simillarScore = simillarScore;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

}
