package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.requestDto.SkuRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SkuService {

    @Transactional
    public Map<String ,Object> insertSkuBase(SkuRequestDto skuRequestDto) {
        Map resultMap = new HashMap();
        return resultMap;
    }




}
