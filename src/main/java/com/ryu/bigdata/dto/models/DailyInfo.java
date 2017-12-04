package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 일일정보
@Data
public class DailyInfo {
    @NotNull
    private int skuId;//: 'sku 품번',
    private String size;//: '사이즈' -- 2,
    private String retailerCode;//: 유통 채널,
    private String stdDate;//: '기준일자',
    private int salesPrice;//: '판매금액',
    private String discountYn;//: '할인여부',
    private int salesCnt;//: '판매수량',
    private int stockCnt;//: '재고수량',
    private String salesStartYn;//: '판매시작일',
    private String salesEndYn;//: '판매종료일'
    private float discountRate;//: '할인율',
    private String bestYn;//: 'best여부',
    private String soldoutYn;//: '품절여부',
    private String newYn;//: '신상여부'

}

