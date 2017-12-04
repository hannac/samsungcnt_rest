package com.ryu.bigdata.dto.requestDto;

import com.ryu.bigdata.dto.models.*;
import lombok.Data;

import java.util.List;

@Data
public class SkuRequestDto {

    private Sku sku;
    private List<Material> material; // 소재
    private List<GoodsSize> goodsSize; // 사이즈
    private List<GoodsImg> goodsImg; // 이미지
    private List<Brand> brand; // 브랜드
    private List<Color> color; // 컬러
    private List<Size> size; // 사이즈
    private List<Retailer> retailer; // 유통채널
    private List<SkuRetailer> skuRetailer; // 상품유통채널

}
