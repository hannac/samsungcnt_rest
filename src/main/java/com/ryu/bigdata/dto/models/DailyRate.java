package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 일일상품반응률
@Data
public class DailyRate {
    @NotNull
    private String retailerCode;//: 유통 채널,
    private int day;//: '일',
    private int id;//: '품번',
    private int clickCnt;//: '클릭수',

}
