package com.ryu.bigdata.dto.requestDto;

import com.ryu.bigdata.dto.models.DailyInfo;
import com.ryu.bigdata.dto.models.DailyRate;
import com.ryu.bigdata.dto.models.DailySearch;
import com.ryu.bigdata.dto.models.GoodsEval;
import lombok.Data;

import java.util.List;

@Data
public class DailyRequestDto {

    private List<DailyInfo> dailyInfo; // 일일정보
    private List<DailySearch> dailySearch; // 일일인기검색어
    private List<DailyRate> dailyRate; // 일일상품반응률
    private List<GoodsEval> goodsEval; // 상품평가

}
