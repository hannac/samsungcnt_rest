package com.ryu.bigdata.dto.models;


import lombok.Data;

import javax.validation.constraints.NotNull;

// 상품이미지
@Data
public class GoodsImg {
    @NotNull
    private String goodsImgId;//	이미지품번	정수
    private String skuId;//	SKU 품번	정수
    private String s3Path;//	이미지 URL	문자열
    private String thumbnailPath;//	THUMBNAIL 경로	문자열
}

