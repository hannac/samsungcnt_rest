package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 상품유통채널
@Data
public class SkuRetailer {
    @NotNull
    private String skuId;//	SKU 품번	정수
    private String retailerCode;//	유통 채널	문자열
}