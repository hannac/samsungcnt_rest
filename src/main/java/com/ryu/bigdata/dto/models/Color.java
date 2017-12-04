package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 색상
@Data
public class Color {
    @NotNull
    private String colorId;//	색상코드	정수
    private String colorName;//	색상명	문자열
}