package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 사이즈
@Data
public class GoodsSize {
    @NotNull
    private String sizeId;//	사이즈품번	정수
    private String skuId;//	SKU 품번	정수
    private String size;//	사이즈	문자열
}
