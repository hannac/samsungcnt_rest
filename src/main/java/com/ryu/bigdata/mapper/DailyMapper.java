package com.ryu.bigdata.mapper;

import com.ryu.bigdata.dto.models.DailyInfo;
import com.ryu.bigdata.dto.models.DailyRate;
import com.ryu.bigdata.dto.models.DailySearch;
import com.ryu.bigdata.dto.models.GoodsEval;
import com.ryu.bigdata.vo.SkuImgVo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface DailyMapper {

	int insertDailyInfo(DailyInfo dailyInfo);

	int insertDailySearch(DailySearch dailySearch);

	int insertDailyRate(DailyRate dailyRate);

	int insertGoosEval(GoodsEval goodsEval);
}
