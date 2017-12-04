package com.ryu.bigdata.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VectorConvertitsResponseDto {

    private int skuImgId; // SKU_IMG_ID
    private int skuId; // SKU ID
    private String s3Path; // S3 경로


}
