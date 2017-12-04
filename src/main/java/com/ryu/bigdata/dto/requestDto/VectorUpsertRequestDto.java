package com.ryu.bigdata.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class VectorUpsertRequestDto {

    @Data
    public class VectorItem {
        private int skuImgId; // SKU_IMG_ID
        private int skuId; // SKU ID
        private String vector;
    }

    private String bulkYn; // 벌크유무
    private List<VectorItem> vectorItems; // 어레이 값 왔는지 체크
    private VectorItem vectorItem; // 하나에 값이면 insert
}
