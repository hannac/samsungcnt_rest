package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 브랜드
@Data
public class Brand {
    @NotNull
    private String brandId;//	브랜드코드	정수
    private String brandName;//	브랜드명	문자열
}
