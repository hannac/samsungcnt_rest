package com.ryu.bigdata.vo;

public class ImageInfoUrl {
	private String url;
	private String imageNm;
	private String x;
	private String y;
	private String width;
	private String height;
	private String retSize;
	private String ownYn;
	
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getRetSize() {
		return retSize;
	}

	public void setRetSize(String retSize) {
		this.retSize = retSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageNm() {
		return imageNm;
	}

	public void setImageNm(String imageNm) {
		this.imageNm = imageNm;
	}

	public String getOwnYn() {
		return ownYn;
	}

	public void setOwnYn(String ownYn) {
		this.ownYn = ownYn;
	}

	public ImageInfoUrl()
	{
		super();
	}
}
