package com.ryu.bigdata.vo;

/**
 * product 의 속성들을 정의한 열거형
 * -----------------------------------------------------------------------
 * 리턴해야 하는 json 스트링에 더 필요한 속성이 있으면 여기에 추가하여 사용해야 합니다.
 * 
 * @author azc2004
 *
 */
public enum ProductProperty {
	
	// by.hnc -------------------------------------------------------------
	// 추가적으로 필요한 product 의 속성이 있으면 여기에 추가
	// 속성명의 선언은 아래와 같이 대문자로 하고 실제로 json 문자열에 사용될 낙타표기식의 값은
	// 괄호 안에 문자열로 표기 후 콤마로 나열합니다.
	PRODUCTID("productId"),
	PRODUCTCD("productCd"),
	ACCSALES("accSales"),
	BRANDNM("brandNm"),
	RELEASEDT("releaseDt"),
	RETAILPRICE("retailPrice"),
	ENDOFLIFE("endOfLife"),
	PRODUCTNM("productNm"),
	ITEM("item"),
	COLOR("color"),
	SEASON("season"),
	SIZE("size"),
	FABRIC("fabric"),
	WEEKLYSALES("weeklySales"),
	IMAGEURL("imageUrl"),
	CRAWLURL("crawlUrl"),
	OWNPRODUCTYN("ownProductYn"),
	VOCLIST("vocList"),
	
	NUMOFCLASSIFICATION("numofclassification"),
	NUMOFTEXTURE("numoftexture"),
	NUMOFFABRIC("numoffabric"),
	NUMOFSHAPE("numofshape"),
	NUMOFDETAIL("numofdetail"),
	NUMOFSEARCH("numofsearch"),
	
	RANK("rank"),
	CLASS("class"),
	PROBABILITY("probability"),
	SIMILLARSCORE("simillarScore");
	
	// 이하는 추가 및 수정이 필요 없으니 위쪽의 속성명에 추가
	final private String name;
	
	private ProductProperty(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}