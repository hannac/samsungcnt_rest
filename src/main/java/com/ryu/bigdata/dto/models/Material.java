package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 소재
@Data
public class Material {
    @NotNull
    private String materialId; //	소재품번	정수
    private String skuId; //	품번	정수
    private String materialComposed; //	구성	문자열
    private String materialName; //	소재명	문자열
    private String rate; //	소재비율	실수
}
