package com.ryu.bigdata.dto.requestDto;

import com.ryu.bigdata.dto.models.VectorItem;
import lombok.Data;

import java.util.List;

@Data
public class VectorUpsertRequestDto {

    private List<VectorItem> list;
    private VectorItem one;
    private String bulkYn; // 벌크유무

}
