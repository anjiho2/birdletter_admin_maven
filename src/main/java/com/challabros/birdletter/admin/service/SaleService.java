package com.challabros.birdletter.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.challabros.birdletter.admin.dto.CornSaleListDto;
import com.challabros.birdletter.admin.dto.ItemBuyRankDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.SaleMapper;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Service
public class SaleService {
	
	private final static Logger logger = LoggerFactory.getLogger(SaleService.class);
	
	@Autowired
	private SaleMapper saleMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 팝콘 구매 내역
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18
	 * </pre>  
	 * @param sPage
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<CornSaleListDto> totalCornPopcornSaleList(int sPage, String startDate, 
			String endDate, String itemType) {
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		
		HashMap<String, Object>paramMap = new HashMap<>();
		paramMap.put("start", pagingDto.getStart());
		paramMap.put("end", pagingDto.getEnd());
		paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
		paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
		
		List<CornSaleListDto>Arr = new ArrayList<CornSaleListDto>();
		if (Value.CORN.equals(itemType)) Arr = saleMapper.totalCornSaleList(paramMap);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 팝콘 구매 내역 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .14
	 * </pre>  
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @return
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object> totalCornPopcornSaleListCnt(String startDate, 
			String endDate, String itemType) {
		HashMap<String, Object>resultMap = new HashMap<>();
		if (Value.CORN.equals(itemType)) 
			resultMap = saleMapper.totalCornSaleListCnt(DateUtils.getOclock(startDate), DateUtils.getLastDayOclock(endDate));
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 콘, 팝콘 판매 총합, 총건수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 06 .30
	 * </pre> 
	 * @param itemType
	 * @return
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Integer> getSumCornPopcornSale(String itemType) {
		if ("".equals(itemType)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		HashMap<String, Integer> resultMap = new HashMap<>();
		if (Value.CORN.equals(itemType))resultMap =  saleMapper.getSumCornSale();
		
		return resultMap;
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param itemType
	 * @return
	 */
	@Transactional(readOnly = true)
	public int countCornPopcornBuyLog(String startDate, String endDate, String itemType) {
		int cnt=0;
		if (Value.CORN.equals(itemType)) 
			cnt = saleMapper.countCornBuyLog(DateUtils.getOclock(startDate), DateUtils.getLastDayOclock(endDate));
		return cnt;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 아이템 판매 순위 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18
	 * </pre> 
	 * @param productName
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ItemBuyRankDto> itemBuyRank(int sPage, int listCount, String productName) {
		PagingDto pagingDto = Util.getPaging(sPage, listCount);
		String start = pagingDto.getStart();
		String end = pagingDto.getEnd();
		
		List<ItemBuyRankDto>Arr = saleMapper.itemBuyRank(start, end, productName);
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 아이템 판매 순위 리스트 개수 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12. 23
	 * </pre>  
	 * @param productName
	 * @return
	 */
	@Transactional(readOnly = true)
	public int itemBuyRankCnt(String productName) {
		return saleMapper.itemBuyRankCnt(Util.isNullValue(productName, ""));
	}
	
	@Transactional(readOnly = true)
	public List<HashMap<String, Object>> itemBuyTop10() {
		List<HashMap<String, Object>> resultMap = new ArrayList<>();
		List<ItemBuyRankDto>Arr = this.itemBuyRank(0, 0, "");
		if (Arr.size() > 0) {
			for (int i=1; i<=10; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				for (ItemBuyRankDto itemBuyRankDto : Arr) {
					if (itemBuyRankDto.getRowNum() == i) {
						map.put("productName", itemBuyRankDto.getProductName());
						map.put("cnt", itemBuyRankDto.getCnt());
						break;
					} else {
						map.put("productName", "");
						map.put("cnt", 0);
					}
				}
				resultMap.add(map);
			}
		}
		return resultMap;
	}

}
