package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.models.DailyInfo;
import com.ryu.bigdata.dto.models.DailyRate;
import com.ryu.bigdata.dto.models.DailySearch;
import com.ryu.bigdata.dto.models.GoodsEval;
import com.ryu.bigdata.dto.requestDto.DailyRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.mapper.DailyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class DailyInfoService {

    @Autowired
    DailyMapper dailyMapper;

    @Transactional
    public Map<String, Object> insertDailyInfo(DailyRequestDto dailyRequestDto) {
        Map resultMap = new HashMap();

        try {
            // 일일정보
            for(DailyInfo item : dailyRequestDto.getDailyInfo()) {
                dailyMapper.insertDailyInfo(item);
            }
            // 일일인기검색어
            for(DailySearch item : dailyRequestDto.getDailySearch()) {
                dailyMapper.insertDailySearch(item);
            }
            // 일일상품반응률
            for(DailyRate item : dailyRequestDto.getDailyRate()) {
                dailyMapper.insertDailyRate(item);
            }
            // 상품평가
            for(GoodsEval item : dailyRequestDto.getGoodsEval()) {
                dailyMapper.insertGoosEval(item);
            }

            resultMap.put("result", new CommonResult());

        } catch (Exception e) {

        } finally {

        }
        return resultMap;
    }
}
