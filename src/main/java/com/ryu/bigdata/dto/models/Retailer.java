package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 유통채널
@Data
public class Retailer {
    @NotNull
    private String retailerCode;//	리터엘러코드	문자열
    private String retailerName;//	유통채널명	문자열
    private String siteUrl;//	URL	문자열
}