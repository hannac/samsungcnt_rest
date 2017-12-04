package com.ryu.bigdata.dto.models;

import lombok.Data;

@Data
public class VectorItem {
    private int skuImgId; // SKU_IMG_ID
    private int skuId; // SKU ID
    private String vector;
}
