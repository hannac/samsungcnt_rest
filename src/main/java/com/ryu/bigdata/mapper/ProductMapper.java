package com.ryu.bigdata.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ryu.bigdata.vo.ImageInfo;
import com.ryu.bigdata.vo.Product;
import com.ryu.bigdata.vo.WeeklySales;
import com.ryu.bigdata.vo.WeeklySalesSearch;


@Mapper
public interface ProductMapper {

	/**
	 * product 테이블의 전체 건수 카운트
	 * @return
	 */
	public Integer selectCount();
	public Product selectProductInfo(long productId);
	public Product selectProductInfoByImg(String ImageUrl);
	public List<WeeklySales> selectWeeklyInfo(WeeklySalesSearch weeklySalesSearch);
	
	// 시연용
	public Product selectProductInfoByProductCD(String productCd);
	public void insertProductInfo(Product product);
	public void insertProductDetailInfo(Product product);
	public int selectMaxID();
}
