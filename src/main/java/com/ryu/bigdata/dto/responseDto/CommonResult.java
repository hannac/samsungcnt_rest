package com.ryu.bigdata.dto.responseDto;

import lombok.Data;

@Data
public class CommonResult {
    private String code = "200";
    private String message = "성공";
}
