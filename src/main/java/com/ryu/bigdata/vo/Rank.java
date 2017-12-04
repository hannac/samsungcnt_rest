package com.ryu.bigdata.vo;

public class Rank {
	private int rank;
	private String sClass;
	private double probability;
	
	
	public Rank() { 	}
	
	public Rank(int rank, String sClass, double probability) {
		super();
		this.rank = rank;
		this.sClass = sClass;
		this.probability = probability;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getsClass() {
		return sClass;
	}

	public void setsClass(String sClass) {
		this.sClass = sClass;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	@Override
	public String toString() {
		return "{\"rank\":" + rank + ", \"sClass\":\"" + sClass + "\", \"probability\":" + probability + "}";
	}
	
	
}
