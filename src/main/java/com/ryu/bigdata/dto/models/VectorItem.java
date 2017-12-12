package com.ryu.bigdata.dto.models;

import lombok.Data;

import java.util.Map;

@Data
public class VectorItem {
    private int skuImgId; // SKU_IMG_ID
    private int skuId; // SKU ID
    private int numofvector; // numofvector
    private Map[] vector;
}
