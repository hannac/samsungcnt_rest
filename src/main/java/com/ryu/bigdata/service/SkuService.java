package com.ryu.bigdata.service;

import com.ryu.bigdata.dto.models.*;
import com.ryu.bigdata.dto.requestDto.SkuRequestDto;
import com.ryu.bigdata.dto.responseDto.CommonResult;
import com.ryu.bigdata.mapper.SkuBaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SkuService {

    @Autowired
    SkuBaseMapper skuBaseMapper;

    public Map<String ,Object> insertSkuBase(SkuRequestDto skuRequestDto) {
        Map resultMap = new HashMap();

        try {

            // sku : 단건
            skuBaseMapper.insertSku(new Sku());

            // material : 다건
            for (Material item : skuRequestDto.getMaterial()) {
                skuBaseMapper.insertMaterial(item);
            }
            // goodsSize : 다건
            for (GoodsSize item : skuRequestDto.getGoodsSize()) {
                skuBaseMapper.insertGoodsSize(item);
            }
            // goodsImg : 다건
            for (GoodsImg item : skuRequestDto.getGoodsImg()) {
                skuBaseMapper.insertGoodsImg(item);
            }
            // brand : 다건
            for (Brand item : skuRequestDto.getBrand()) {
                skuBaseMapper.insertBrand(item);
            }
            // color : 다건
            for (Color item : skuRequestDto.getColor()) {
                skuBaseMapper.insertColor(item);
            }
            // size : 다건
            for (Size item : skuRequestDto.getSize()) {
                skuBaseMapper.insertSize(item);
            }
            // retailer : 다건
            for (Retailer item : skuRequestDto.getRetailer()) {
                skuBaseMapper.insertRetailer(item);
            }
            // skuRetailer : 다건
            for (SkuRetailer item : skuRequestDto.getSkuRetailer()) {
                skuBaseMapper.insertSkuRetailer(item);
            }

            resultMap.put("result", new CommonResult());


        } catch (Exception e) {
            log.debug(e.getMessage());
            CommonResult commonResult = new CommonResult();
            commonResult.setCode("500");
            commonResult.setMessage(e.getMessage());

            resultMap.put("result", commonResult);

        } finally {

        }

        return resultMap;
    }




}
