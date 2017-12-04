package com.ryu.bigdata.dto.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DailyRequestDto {

    private DailyInfo[] dailyInfos; // 일일정보
    private DailySearch[] dailySearches; // 일일인기검색어
    private DailyRate[] dailyRates; // 일일상품반응률
    private GoodsEval[] goodsEvals; // 상품평가

    // 일일정보
    @Data
    public class DailyInfo {
        @NotNull
        private int skuId;//: 'sku 품번',
        private String size;//: '사이즈' -- 2,
        private String retailerCode;//: 유통 채널,
        private String stdDate;//: '기준일자',
        private int salesPrice;//: '판매금액',
        private String discountYn;//: '할인여부',
        private int salesCnt;//: '판매수량',
        private int stockCnt;//: '재고수량',
        private String salesStartYn;//: '판매시작일',
        private String salesEndYn;//: '판매종료일'
        private float discountRate;//: '할인율',
        private String bestYn;//: 'best여부',
        private String soldoutYn;//: '품절여부',
        private String newYn;//: '신상여부'

    }

    // 일일인기검색어
    @Data
    public class DailySearch {
        @NotNull
        private String retailerCode;//: 유통 채널,
        private int day;//: '일',
        private String searchWord;//: '검색어',
        private int rank;//: '순위',
        private int searchCnt;//: '검색수

    }

    // 일일상품반응률
    @Data
    public class DailyRate {
        @NotNull
        private String retailerCode;//: 유통 채널,
        private int day;//: '일',
        private int id;//: '품번',
        private int clickCnt;//: '클릭수',

    }

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
}
