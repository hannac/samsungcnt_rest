package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.requestDto.DailyRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class DailyInfoService {

    @Transactional
    public Map<String, Object> insertDailyInfo(DailyRequestDto dailyRequestDto) {
        Map resultMap = new HashMap();
        return resultMap;
    }
}
