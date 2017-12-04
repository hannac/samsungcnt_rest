package com.ryu.bigdata.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Search {
	private String product_num;
	private String image_name;
	private String score;
	private String ours;
	private String s3url;

	public String getProduct_num() {
		return product_num;
	}

	public void setProduct_num(String product_num) {
		this.product_num = product_num;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOurs() {
		return ours;
	}

	public void setOurs(String ours) {
		this.ours = ours;
	}
	
	

	public String getS3url() {
		return s3url;
	}

	public void setS3url(String s3url) {
		this.s3url = s3url;
	}

	public Search() {
	}

	public Search(String product_num, String image_name, String score, String ours, String s3url) {
		super();
		this.product_num = product_num;
		this.image_name = image_name;
		this.score = score;
		this.ours = ours;
		this.s3url = s3url;
	}

}
