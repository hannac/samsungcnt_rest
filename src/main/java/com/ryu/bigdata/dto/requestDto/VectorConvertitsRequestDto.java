package com.ryu.bigdata.dto.requestDto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class VectorConvertitsRequestDto {

    @NotNull
    @Max(14)
    @Min(14)
    private String regDateTime; // 등록일시

    private int skuId;
    private int skuImgId;
}
