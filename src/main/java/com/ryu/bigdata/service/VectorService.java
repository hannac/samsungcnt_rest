package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.requestDto.VectorConvertitsRequestDto;
import com.ryu.bigdata.dto.models.VectorItem;
import com.ryu.bigdata.dto.requestDto.VectorUpsertRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.mapper.VectorMapper;
import com.ryu.bigdata.vo.SkuImgVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VectorService {

    @Autowired
    VectorMapper vectorMapper;

    public Map<String, Object> selectVectorNoList(VectorConvertitsRequestDto vectorConvertitsRequestDto) {

        log.debug(vectorConvertitsRequestDto.toString());

        Map resultMap = new HashMap();
        SkuImgVo skuImgVo = new SkuImgVo();
        skuImgVo.setRegDateTime(vectorConvertitsRequestDto.getRegDateTime());
        List<SkuImgVo> resultList = vectorMapper.selectVectorNoList(skuImgVo);
        resultMap.put("data", resultList);
        resultMap.put("result", new CommonResult());
        return resultMap;
    }

    public Map<String, Object> selectVectorYesOne(VectorConvertitsRequestDto vectorConvertitsRequestDto) {

        Map resultMap = new HashMap();
        SkuImgVo skuImgVo = new SkuImgVo();
        skuImgVo.setSkuId(vectorConvertitsRequestDto.getSkuId());
        skuImgVo.setSkuImgId(vectorConvertitsRequestDto.getSkuImgId());
        List<SkuImgVo> resultList = vectorMapper.selectVectorYesList(skuImgVo);
        resultMap.put("data", resultList);
        resultMap.put("result", new CommonResult());
        return resultMap;
    }

    public Map<String, Object> upsertVector(VectorUpsertRequestDto vectorUpsertRequestDto) {

        log.debug(vectorUpsertRequestDto.toString());

        Map resultMap = new HashMap();
        try {

            if (vectorUpsertRequestDto.getBulkYn().equals("Y")) {
                // 멀티 저장
                List<VectorItem> list = vectorUpsertRequestDto.getList();
                for (VectorItem item : list) {
                    SkuImgVo skuImgVo = new SkuImgVo();
                    skuImgVo.setSkuId(item.getSkuId());
                    skuImgVo.setSkuImgId(item.getSkuImgId());
                    skuImgVo.setVector(item.getVector().toString());
                    vectorMapper.updateVector(skuImgVo);
                }
                // 쿼리 호출
            } else {
                // 싱글 저장
                VectorItem vectorItem = vectorUpsertRequestDto.getOne();
                // 쿼리 호출
                SkuImgVo skuImgVo = new SkuImgVo();
                skuImgVo.setSkuId(vectorItem.getSkuId());
                skuImgVo.setSkuImgId(vectorItem.getSkuImgId());
                skuImgVo.setVector(vectorItem.getVector().toString());
                vectorMapper.updateVector(skuImgVo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            resultMap.put("result", new CommonResult());
        }
        return resultMap;
    }
}
