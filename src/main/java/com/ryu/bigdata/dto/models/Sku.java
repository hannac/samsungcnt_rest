package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Sku {
    @NotNull
    private int skuId; // 품번
    private String skuName; // 품명
    private int colorId; // 색상
    private int brandId; // 브랜드코드
    private String ssfCategory; // SSF 카테고리
    private String origin; //	원산지	문자열
    private String tagPrice; //	정소가	정수
    private String collectPath; //	URL	문자열
    private String descr; //	제품설명	문자열
    private String salesStartYn; //	판매시작일	문자
    private String salesEndYn; //	판매종료일	문자
}
