package com.ryu.bigdata.dto.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SkuRequestDto {

    @NotNull
    private int skuId; // 품번
    private String skuName; // 품명
    private int colorId; // 색상
    private int brandId; // 브랜드코드
    private String ssfCategory; // SSF 카테고리
    private String origin; //	원산지	문자열
    private String tagPrice; //	정소가	정수
    private String collectPath; //	URL	문자열
    private String descr; //	제품설명	문자열
    private String salesStartYn; //	판매시작일	문자
    private String salesEndYn; //	판매종료일	문자

    private Material[] materials; // 소재
    private GoodsSize[] goodsSizes; // 사이즈
    private GoodsImg[] goodsImgs; // 이미지
    private Brand[] brands; // 브랜드
    private Color[] colors; // 컬러
    private Size[] sizes; // 사이즈
    private Retailer[] retailers; // 유통채널
    private SkuRetailer[] skuRetailers; // 상품유통채널

    // 소재
    @Data
    public class Material {
        @NotNull
        private String materialId; //	소재품번	정수
        private String skuId; //	품번	정수
        private String materialComposed; //	구성	문자열
        private String materialName; //	소재명	문자열
        private String rate; //	소재비율	실수
    }

    // 사이즈
    @Data
    public class GoodsSize {
        @NotNull
        private String sizeId;//	사이즈품번	정수
        private String skuId;//	SKU 품번	정수
        private String size;//	사이즈	문자열
    }

    // 상품이미지
    @Data
    public class GoodsImg {
        @NotNull
        private String goodsImgId;//	이미지품번	정수
        private String skuId;//	SKU 품번	정수
        private String s3Path;//	이미지 URL	문자열
        private String thumbnailPath;//	THUMBNAIL 경로	문자열
    }

    // 브랜드
    @Data
    public class Brand {
        @NotNull
        private String brandId;//	브랜드코드	정수
        private String brandName;//	브랜드명	문자열
    }

    // 색상
    @Data
    public class Color {
        @NotNull
        private String colorId;//	색상코드	정수
        private String colorName;//	색상명	문자열
    }


    // 사이즈
    @Data
    public class Size {
        @NotNull
        private String sizeName;//	사이즈명	문자열
        private String sizeInfo;//	사이즈설명	문자열
    }

    // 유통채널
    @Data
    public class Retailer {
        @NotNull
        private String retailerCode;//	리터엘러코드	문자열
        private String retailerName;//	유통채널명	문자열
        private String siteUrl;//	URL	문자열
    }

    // 상품유통채널
    @Data
    public class SkuRetailer {
        @NotNull
        private String skuId;//	SKU 품번	정수
        private String retailerCode;//	유통 채널	문자열
    }

}
