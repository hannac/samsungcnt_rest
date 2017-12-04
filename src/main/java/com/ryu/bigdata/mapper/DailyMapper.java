package com.ryu.bigdata.mapper;

import com.ryu.bigdata.vo.SkuImgVo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface DailyMapper {

	/**
	 * product 테이블의 전체 건수 카운트
	 * @return
	 */
	List<SkuImgVo> selectVectorNoList(SkuImgVo skuImgVo);
	int updateVector(SkuImgVo skuImgVo);
}
