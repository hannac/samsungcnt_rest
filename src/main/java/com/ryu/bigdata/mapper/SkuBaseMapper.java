package com.ryu.bigdata.mapper;

import com.ryu.bigdata.dto.models.*;
import com.ryu.bigdata.vo.SkuImgVo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface SkuBaseMapper {

	int insertSku(Sku sku);

	int insertMaterial(Material material);

	int insertGoodsSize(GoodsSize goodsSize);

	int insertGoodsImg(GoodsImg goodsImg);

	int insertBrand(Brand brand);

	int insertColor(Color color);

	int insertSize(Size size);

	int insertRetailer(Retailer retailer);

	int insertSkuRetailer(SkuRetailer skuRetailer);

}
