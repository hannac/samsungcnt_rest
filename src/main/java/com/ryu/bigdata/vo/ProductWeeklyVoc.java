package com.ryu.bigdata.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductWeeklyVoc extends ProductWeekly {
	private List<Voc> vocList;
}
