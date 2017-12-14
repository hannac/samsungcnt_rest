package com.ryu.bigdata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ryu.bigdata.dto.models.DailyInfo;
import com.ryu.bigdata.dto.models.DailyRate;
import com.ryu.bigdata.dto.models.DailySearch;
import com.ryu.bigdata.dto.models.GoodsEval;
import com.ryu.bigdata.dto.requestDto.DailyRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.mapper.DailyMapper;

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
    
    /**
     * 무신사, 네이버 (외부 사이트) 인기 검색어만 입력 
     * @param dailyRequestDto
     * @return
     */
    @Transactional
    public Map<String, Object> insertDailyInfoOuterSite(List<DailySearch> searchList) {
    	Map<String, Object> resultMap = new HashMap<>();
    	try {
    		// 일일인기검색어
    		for(DailySearch item : searchList) {
    			dailyMapper.insertDailySearchOuter(item);
    		}
    		resultMap.put("result", new CommonResult());
    		
    	} catch (Exception e) {
    		
    	} finally {
    		
    	}
    	return resultMap;
    }
}
