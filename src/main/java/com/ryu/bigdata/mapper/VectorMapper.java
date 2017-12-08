package com.ryu.bigdata.mapper;

import com.ryu.bigdata.vo.SkuImgVo;
import org.mapstruct.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
public interface VectorMapper {

	List<SkuImgVo> selectVectorNoList(SkuImgVo skuImgVo);

	List<SkuImgVo> selectVectorYesList();

	int updateVector(SkuImgVo skuImgVo);
}
