package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 사이즈
@Data
public class Size {
    @NotNull
    private String sizeName;//	사이즈명	문자열
    private String sizeInfo;//	사이즈설명	문자열
}
