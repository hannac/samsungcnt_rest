package com.ryu.bigdata.vo;

import lombok.Data;

@Data
public class SkuImgVo {

    private String vector;
    private int skuId;
    private int skuImgId;
    private String s3Path;
    private String regDateTime;
}
