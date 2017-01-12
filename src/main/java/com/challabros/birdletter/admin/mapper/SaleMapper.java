package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.CornSaleListDto;
import com.challabros.birdletter.admin.dto.ItemBuyRankDto;

public interface SaleMapper {
	
	/** select **/
	List<CornSaleListDto> totalCornSaleList(HashMap<String, Object>paramMap);
	
	HashMap<String, Object> totalCornSaleListCnt(@Param("startDate")String startDate, @Param("endDate")String endDate);
	
	HashMap<String, Integer> getSumCornSale();
	
	int countCornBuyLog(@Param("startDate")String startDate, @Param("endDate")String endDate);
	
	List<ItemBuyRankDto> itemBuyRank(@Param("start")String start, @Param("end")String end, @Param("productName")String productName);
	
	int itemBuyRankCnt(@Param("productName")String productName);

}
