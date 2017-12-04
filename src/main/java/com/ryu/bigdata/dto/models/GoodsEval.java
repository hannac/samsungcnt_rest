package com.ryu.bigdata.dto.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

// 상품평가
@Data
public class GoodsEval {
    @NotNull
    private String skuId;//: 'sku 품번',
    private String raterId;//: '평가자',
    private int evalPoint;//: '평점',
    private String goodsComment;//: '상품평',
    private String evalDateTime;//: '평가일시',

}