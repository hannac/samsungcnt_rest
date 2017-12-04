package com.ryu.bigdata.vo;

public class Voc {

	private double vocRating;
	private String voc;

	public Voc() { }
	
	public Voc(double vocRating, String voc) {
		super();
		this.vocRating = vocRating;
		this.voc = voc;
	}

	public double getVocRating() {
		return vocRating;
	}

	public void setVocRating(double vocRating) {
		this.vocRating = vocRating;
	}

	public String getVoc() {
		return voc;
	}

	public void setVoc(String voc) {
		this.voc = voc;
	}

	@Override
	public String toString() {
		return "{\"vocRating\":" + vocRating + ", \"voc\":\"" + voc + "\"}";
	}
	
	
	
}
