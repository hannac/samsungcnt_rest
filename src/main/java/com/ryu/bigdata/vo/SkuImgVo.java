package com.ryu.bigdata.vo;

import lombok.Data;

@Data
public class SkuImgVo {

    private String vector;
    private int skuId;
    private double skuImgId;
    private String s3Path;
    private String regDateTime;
    private int numofvector;
    private String ownYn;
}
