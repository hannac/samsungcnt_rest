package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.requestDto.VectorConvertitsRequestDto;
import com.ryu.bigdata.dto.requestDto.VectorUpsertRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.mapper.VectorMapper;
import com.ryu.bigdata.vo.SkuImgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VectorService {

    @Autowired
    VectorMapper vectorMapper;

    public Map<String, Object> selectVectorNoList(VectorConvertitsRequestDto vectorConvertitsRequestDto) {

        Map resultMap = new HashMap();
//        Stream<VectorConvertitsResponseDto> vectorConvertitsResponseDtoStream = Stream.of(
//                new VectorConvertitsResponseDto(1, 1,"http://s3path"),
//                new VectorConvertitsResponseDto(2, 1,"http://s3path"),
//                new VectorConvertitsResponseDto(3, 1,"http://s3path"),
//                new VectorConvertitsResponseDto(4, 1,"http://s3path")
//        );
//        List resultList = vectorConvertitsResponseDtoStream.collect(Collectors.toList());
        SkuImgVo skuImgVo = new SkuImgVo();
        skuImgVo.setRegDateItem(vectorConvertitsRequestDto.getRegDateTime());
        List resultList = vectorMapper.selectVectorNoList(skuImgVo);
        resultMap.put("data", resultList);
        resultMap.put("result", new CommonResult());
        return resultMap;
    }

    @Transactional
    public Map<String, Object> upsertVector(VectorUpsertRequestDto vectorUpsertRequestDto) {

        Map resultMap = new HashMap();
        try {

            if (vectorUpsertRequestDto.getBulkYn().equals("Y")) {
                // 멀티 저장
                List<VectorUpsertRequestDto.VectorItem> list = vectorUpsertRequestDto.getVectorItems();
                for (VectorUpsertRequestDto.VectorItem item : list) {
                    SkuImgVo skuImgVo = new SkuImgVo();
                    skuImgVo.setSkuId(item.getSkuId());
                    skuImgVo.setSkuImgId(item.getSkuImgId());
                    skuImgVo.setVector(item.getVector());
                    vectorMapper.updateVector(skuImgVo);
                }
                // 쿼리 호출
            } else {
                // 싱글 저장
                VectorUpsertRequestDto.VectorItem vectorItem = vectorUpsertRequestDto.getVectorItem();
                // 쿼리 호출
                SkuImgVo skuImgVo = new SkuImgVo();
                skuImgVo.setSkuId(vectorItem.getSkuId());
                skuImgVo.setSkuImgId(vectorItem.getSkuImgId());
                skuImgVo.setVector(vectorItem.getVector());
                vectorMapper.updateVector(skuImgVo);
            }

        } catch (Exception e) {
            e.getMessage();
        } finally {
            resultMap.put("result", new CommonResult());
        }
        return resultMap;
    }
}
