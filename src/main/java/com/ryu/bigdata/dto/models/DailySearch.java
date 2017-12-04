package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 일일인기검색어
@Data
public class DailySearch {
    @NotNull
    private String retailerCode;//: 유통 채널,
    private int day;//: '일',
    private String searchWord;//: '검색어',
    private int rank;//: '순위',
    private int searchCnt;//: '검색수

}