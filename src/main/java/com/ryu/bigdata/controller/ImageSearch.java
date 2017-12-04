package com.ryu.bigdata.controller;


public class ImageSearch {
  //data:image/png;base64, 
  private String image;
  private String imageName;
  private String x;
  private String y;
  private String width;
  private String height;
  private String retsize;
  private String imageUrl;
  //소팅순번 : 유사도, 신상품, 인기상품, 높은가격, 낮은가격, 구매후기순, MD추천순
  private String sort;
  //내외부 구분 : 자사, 경쟁사, sns, 전체등..
  private String scope;
  private String ownYn;
  
  public String getScope() {
	  return this.scope;
  }
  public void setScope(String scope) {
	  this.scope=scope;
  }
  
  public String getSort() {
	  return this.sort;
  }
  public void setSort(String sort) {
	  this.sort=sort;
  }
  public String getImageName() {
		return imageName;
	}

	public void setImageName(String image_name) {
		this.imageName = image_name;
	}

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

	public String getRetsize() {
		return retsize;
	}

	public void setRetsize(String retsize) {
		this.retsize = retsize;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String image_url) {
		this.imageUrl = image_url;
	}

	public String getImage() {
		return image;
	}
	public String getOwnYn() {
		return ownYn;
	}
	public void setOwnYn(String ownYn) {
		this.ownYn = ownYn;
	}
	public void setImage(String image) {
		this.image = image;
	}
  
	

}
