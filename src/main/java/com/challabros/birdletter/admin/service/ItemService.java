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

import com.challabros.birdletter.admin.define.datasource.ItemSerchType;
import com.challabros.birdletter.admin.define.datasource.ItemType;
import com.challabros.birdletter.admin.define.datasource.StoreType;
import com.challabros.birdletter.admin.dto.CollectionItemInfoDto;
import com.challabros.birdletter.admin.dto.CornInfoDto;
import com.challabros.birdletter.admin.dto.GatchaUseLogDto;
import com.challabros.birdletter.admin.dto.ItemInfoDto;
import com.challabros.birdletter.admin.dto.ItemTypeDto;
import com.challabros.birdletter.admin.dto.NestItemBuyLogDto;
import com.challabros.birdletter.admin.dto.PagingDto;
import com.challabros.birdletter.admin.dto.PopcornInfoDto;
import com.challabros.birdletter.admin.dto.ProductListDto;
import com.challabros.birdletter.admin.dto.UserItemBuyListDto;
import com.challabros.birdletter.admin.dto.UserProductBuyLogDto;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.mapper.ItemMapper;
import com.challabros.birdletter.admin.util.BirdAdminUtil;
import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.StringUtil;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.Value;

@Service
public class ItemService {

	final static Logger logger = LoggerFactory.getLogger(ItemService.class);
	
	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 아이템 구매 내역 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param sPage
	 * @param phoneNumber
	 * @param storeType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserItemBuyListDto> itemBuyList(int sPage, String phoneNumber, String storeType) {
		List<UserItemBuyListDto> Arr = new ArrayList<UserItemBuyListDto>();
		
		try {
			PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
			UserItemBuyListDto userItemBuyListDto = new UserItemBuyListDto();
			userItemBuyListDto.setStart(pagingDto.getStart());
			userItemBuyListDto.setEnd(pagingDto.getEnd());
			userItemBuyListDto.setPhoneNumber(phoneNumber);
			userItemBuyListDto.setStoreType(storeType);
			
			Arr = itemMapper.itemBuyList(userItemBuyListDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 아이템 구매 내역 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param phoneNumber
	 * @param storeType
	 * @return
	 */
	@Transactional(readOnly=true)
	public int itemBuyListCnt(String phoneNumber, String storeType) {
		int cnt=0;
		
		try {
			cnt = itemMapper.itemBuyListCnt(phoneNumber, storeType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cnt;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 아이템 구매 전체 내역 리스트 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 03 .11
	 * </pre>
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserProductBuyLogDto> findUserItemBuyListAll() {
		List<UserProductBuyLogDto> Arr = itemMapper.itemBuyListAll();
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 사용자의 상품구매 로그 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param userId
	 * @param productCode
	 * @param productName
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public int userProductBuyLogListCnt(Long userId, String productCode, String productName, String searchType) {
		if (userId == null) userId = Long.valueOf(0L);
		int cnt = itemMapper.userProductBuyLogListCnt(userId, productCode, productName);
		return cnt;
	}
	
	/**
	 * <PRE>
	 *  * 1. Comment : 사용자의 상품구매 로그 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </PRE> 
	 * @param sPage
	 * @param userId
	 * @param productCode
	 * @param productName
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<UserProductBuyLogDto> userProductBuyLogList(int sPage, Long userId, String productCode, String productName, String searchType) {
		if (userId == null) userId = Long.valueOf(0L);
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		
		UserProductBuyLogDto dto = new UserProductBuyLogDto();
		dto.setStart(pagingDto.getStart());
		dto.setEnd(pagingDto.getEnd());
		dto.setUserId(userId);
		dto.setProductCode(productCode);
		dto.setProductName(productName);
		
		return itemMapper.userProductBuyLogList(dto);
	}
	
	/**
	 * <pre>
	 * 1. Comment : 상품리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>
	 * @param productCode
	 * @param itemCode
	 * @param productName
	 * @param storeType
	 * @return
	 */
	@Transactional(readOnly=true)
	public int productListCnt(String productCode, String itemCode, String productName, String storeType) {
		ProductListDto dto = new ProductListDto();
		dto.setProductCode(productCode);
		dto.setItemCode(itemCode);
		dto.setProductName(productName);
		dto.setStoreType(storeType);
		return itemMapper.productListCnt(dto);
	}
	
	/**
	 * <pre>
     * 1. Comment : 상품리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param listCount
	 * @param sPage
	 * @param productCode
	 * @param itemCode
	 * @param productName
	 * @param storeType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<ProductListDto> productList(int pagingCnt, int sPage, String productCode, String itemCode, String productName, String storeType) {
		PagingDto pagingDto = Util.getPaging(sPage, pagingCnt);
		ProductListDto dto = new ProductListDto();
		dto.setStart(pagingDto.getStart());
		dto.setEnd(pagingDto.getEnd());
		dto.setProductCode(productCode);
		dto.setItemCode(itemCode);
		dto.setProductName(productName);
		dto.setStoreType(storeType);
		return itemMapper.productList(dto);
	}
	
	/**
	 * <pre>
     * 1. Comment : 상품 상세내용
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param productIdx
	 * @return
	 */
	@Transactional(readOnly=true)
	public ProductListDto productListDetail(int productIdx) {
		if (productIdx < 0) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.productListDetail(productIdx);
	}
	
	/**
	 * <pre>
     * 1. Comment : 상품 내용 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param productListDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean productModify(ProductListDto productListDto) {
		boolean bl = true;
		if (productListDto != null) {
			ProductListDto dto = new ProductListDto();
			dto.setIdx(productListDto.getIdx());
			dto.setProductName(productListDto.getProductName());
            dto.setProductDesc(productListDto.getProductDesc());
            dto.setPopcornPrice(productListDto.getPopcornPrice());
            dto.setCornPrice(productListDto.getCornPrice());
            dto.setItemCode(productListDto.getItemCode());
            dto.setItemCategory(productListDto.getItemCategory());
            dto.setItemType(productListDto.getItemType());
            dto.setStoreMainMenuIdx(productListDto.getStoreMainMenuIdx());
            dto.setStoreSubMenuIdx(productListDto.getStoreSubMenuIdx());
            dto.setDiscountPercent(productListDto.getDiscountPercent());
            dto.setStoreCondition(productListDto.getStoreCondition());
            dto.setViewYn(productListDto.getViewYn());
            int result = itemMapper.productModify(dto);
            
            if (result < 0) bl = false;
		}
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 상풍의 마지막 아이템코드, 상품코드 만들기 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param storeType
	 * @return
	 */
	@Transactional(readOnly=true)
	public HashMap<String, Object> makeProductLastCode(String storeType) {
		HashMap<String, Object>resultMap = new HashMap<String, Object>();
		if (!"".equals(storeType)) {
			ProductListDto productListDto = itemMapper.makeProductLastCode(storeType);
			if (productListDto != null) {
				String plusProductCode = Util.underBarSplitAndPlus(productListDto.getProductCode());
                String plusItemCode = Util.underBarSplitAndPlus(productListDto.getItemCode());
                resultMap.put("productCode", plusProductCode);
                resultMap.put("itemCode", plusItemCode);
			}
		}
		return resultMap;
	}
	
	@Transactional(readOnly=true)
	public String findLastCollectionCode() {
		String lastProductCode = "";
		String productCode = itemMapper.findLastCollectionCode();
		if (!"".equals(productCode)) {
			lastProductCode = Util.underBarSplitAndPlus(productCode);
		}
		return lastProductCode;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 새로운 코드값 만들기 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05. 26
	 * </pre>
	 * @param itemType
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getNewItemCode(String itemType) {
		if ("".equals(itemType)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		String newItemCode = "";
		String itemCode = itemMapper.getNewItemCode(itemType.toUpperCase());
		if (!"".equals(itemCode)) {
			newItemCode = Util.underBarSplitAndPlus(itemCode);
		}
		return newItemCode;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 상세 가져오기 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05. 26
	 * </pre> 
	 * @param itemIdx
	 * @return
	 */
	@Transactional(readOnly=true)
	public ItemInfoDto getItemInfoDetail(int itemIdx) {
		if (itemIdx == 0) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		ItemInfoDto itemInfoDto = itemMapper.getItemInfoDetail(itemIdx);
		return itemInfoDto;
	}
	
	/**
	 * <pre>
     * 1. Comment : 상품 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param productListDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean productInsert(ProductListDto productListDto) {
		if (productListDto == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		boolean bl = false;
		int productCount = itemMapper.exitsProduct(productListDto.getProductCode(), productListDto.getItemCode());
		if (productCount == 0) {
			int result = itemMapper.insertProduct(productListDto);
			if (result > 0) bl = true;
		}
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 등록
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05 .26
	 * </pre> 
	 * @param itemCode
	 * @param itemName
	 * @param itemDesc
	 * @param itemType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int insertItemInfo(String itemCode, String itemName, String itemDesc, String itemType) {
		ItemInfoDto dto = new ItemInfoDto();
		dto.setItemCode(itemCode);
		dto.setItemName(itemName);
		dto.setItemDesc(itemDesc);
		dto.setItemType(itemType);
		int result = itemMapper.insertItemInfo(dto);
		return result;
	}
	
	/**
	 * <pre>
     * 1. Comment : 상품 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param productIdx
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean productDelete(int productIdx) {
		if (productIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		boolean bl = false;
		int result = itemMapper.productDelete(productIdx);
		if (result > 0) bl = true;
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05 .26
	 * </pre> 
	 * @param itemIdx
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteItemInfo(int itemIdx) {
		if (itemIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.deleteItemInfo(itemIdx);
	}
	
	/**
	 * <pre>
     * 1. Comment : 콜렉션 아이템 정보 가져오기
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param productCode
	 * @return
	 */
	@Transactional(readOnly=true)
	public CollectionItemInfoDto collectionItemInfo(String productCode) {
		if ("".equals(productCode)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		CollectionItemInfoDto collectionItemInfoDto = itemMapper.collectionItemInfo(productCode);
		return collectionItemInfoDto;
	}
	
	@Transactional(readOnly=true)
	public String getItemName(String itemCode) {
		if ("".equals(itemCode)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.getItemName(itemCode);
	}
	
	@Transactional(readOnly=true)
	public List<ProductListDto> getItemList(String itemType) {
		if ("".equals(itemType)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.getItemList(itemType);
	}
	
	/**
	 * <pre>
     * 1. Comment : 콜렉션 아이템 정보 입력
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre> 
	 * @param collectionItemInfoDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean collectionItemInfoInsert(CollectionItemInfoDto collectionItemInfoDto) {
		if (collectionItemInfoDto == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		boolean bl = false;
		int result = itemMapper.collectionItemInfoInsert(collectionItemInfoDto);
		if (result > 0) bl = true;
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 콜렉션 아이템 정보 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param sPage
	 * @param productCode
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CollectionItemInfoDto> collectionItemInfoList(int sPage, String productCode, String searchType) {
		if (sPage < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		List<CollectionItemInfoDto> Arr = new ArrayList<>();
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		if ("NORMAL".equals(searchType.toUpperCase())) {
			Arr = itemMapper.collectionItemInfoList(pagingDto.getStart(), pagingDto.getEnd(), productCode);
		} else if ("REG".equals(searchType.toUpperCase())) {
			Arr = itemMapper.regAvailableCollectionItemInfoList(pagingDto.getStart(), pagingDto.getEnd());
		}
		return Arr;
	}
	
	/**
	 * <pre>
	 * 1. Comment : 콜렉션 아이템 정보 리스트 갯수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param productCode
	 * @param searchType
	 * @return
	 */
	@Transactional(readOnly=true)
	public int collectionItemInfoListCnt(String productCode, String searchType) {
		int cnt=0;
		if ("NORMAL".equals(searchType.toUpperCase()))  cnt =itemMapper.collectionItemInfoListCnt(productCode);
		else if ("REG".equals(searchType.toUpperCase())) cnt = itemMapper.regAvailableCollectionItemInfoListCnt();
		return cnt;
	}
	
	/**
	 * <pre>
     * 1. Comment : 콜렉션 아이템 정보 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param collectionItemInfoDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean collectionItemInfoModify(CollectionItemInfoDto collectionItemInfoDto) {
		if (collectionItemInfoDto == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		boolean bl = false;
		int result = itemMapper.updateCollectionItemInfo(collectionItemInfoDto);
		if (result > 0) bl = true;
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 콜렉션 아이템 정보 삭제
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .11
	 * </pre>  
	 * @param productCode
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean collectionItemDelete(String productCode) {
		if ("".equals(productCode)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		boolean bl = false;
		int result = itemMapper.deleteCollectionItem(productCode);
		if (result > 0) bl = true;
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 사용자의 콘, 팝콘의 총합 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .15
	 * </pre> 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly=true)
	public HashMap<String, Object> finfUserCornPopcornPayTotal(Long userId) {
		if (userId == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.finfUserCornPopcornPayTotal(userId);
	}
	
	/**
	 * <pre>
     * 1. Comment : 콘 정보 리스트 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18
	 * </pre> 
	 * @param sPage
	 * @param cornDesc
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CornInfoDto> cornInfoList(int sPage) {
		if (sPage < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		List<CornInfoDto> Arr = itemMapper.cornInfoList(pagingDto.getStart(), pagingDto.getEnd());
		return Arr;
	}
	
	/**
	 * <pre>
     * 1. Comment : 콘 정보 리스트 갯수 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18
	 * </pre> 
	 * @param cornDesc
	 * @return
	 */
	@Transactional(readOnly=true)
	public int cornInfoListCnt() {
		return itemMapper.cornInfoListCnt();
	}
	
	/**
	 * <pre>
     * 1. Comment : 콘 상세정보 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18
     * </pre> 
	 * @param cornIdx
	 * @return
	 */
	@Transactional(readOnly=true)
	public CornInfoDto cornInfoDetail(int cornIdx) {
		if (cornIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		CornInfoDto cornInfoDto = itemMapper.cornInfoDetail(cornIdx);
		return cornInfoDto;
	}
	
	/**
	 * <pre>
     * 1. Comment : 콘 정보 수정 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .18 
	 * </pre> 
	 * @param cornInfoDto
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean cornInfoModify(CornInfoDto cornInfoDto) {
		if (cornInfoDto == null) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		boolean bl = false;
		int result = itemMapper.modifyCornInfo(cornInfoDto);
		if (result > 0) bl = true;
		return bl;
	}
	
	/**
	 * <pre>
     * 1. Comment : 팝콘 정보 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .20
	 * </pre> 
	 * @param sPage
	 * @param popcornName
	 * @return
	 */
	@Transactional(readOnly=true, propagation = Propagation.SUPPORTS)
	public List<PopcornInfoDto> popcornInfoList(int sPage) {
		if (sPage < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageInList10);
		List<PopcornInfoDto> Arr = itemMapper.popcornInfoList(pagingDto.getStart(), pagingDto.getEnd());
		return Arr;
	}
	
	/**
	 * <pre>
     * 1. Comment : 팝콘 정보 리스트 갯수 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 01 .20 
	 * </pre> 
	 * @param popcornName
	 * @return
	 */
	@Transactional(readOnly=true, propagation = Propagation.SUPPORTS)
	public int popcornInfoListCnt() {
		return itemMapper.popcornInfoListCnt();
	}
	
	@Transactional(readOnly=true, propagation = Propagation.SUPPORTS)
	public PopcornInfoDto popcornInfoDetail(int popcornIdx) {
		if (popcornIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		PopcornInfoDto popcornInfoDto = itemMapper.popcornInfoDetail(popcornIdx);
		return popcornInfoDto;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 수정
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05 .26
	 * </pre> 
	 * @param itemIdx
	 * @param itemName
	 * @param itemDesc
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int modifyItemInfo(int itemIdx, String itemName, String itemDesc) {
		if (itemIdx < 1) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		return itemMapper.modifyItemInfo(itemIdx, itemName, itemDesc);
	}
	
	
	
	@Transactional(readOnly=true)
	public int itemListCnt(String itemType, String searchType) {
		int cnt=0;
		if ("NORMAL".equals(searchType.toUpperCase())) {
			cnt = itemMapper.itemListCnt(itemType);
		} else if ("REG".equals(searchType.toUpperCase())) {
			cnt = itemMapper.regAvailableItemListCnt(itemType);
		}
		return cnt;
	}
	
	@Transactional(readOnly=true)
	public List<ItemInfoDto> itemList(int sPage, String itemType, String searchType) {
		List<ItemInfoDto> Arr = new ArrayList<>();
		PagingDto pagingDto = Util.getPaging(sPage, Value.pageIntLis15);
		if ("NORMAL".equals(searchType.toUpperCase())) {
			Arr = itemMapper.itemList(pagingDto.getStart(), pagingDto.getEnd(), itemType);
		} else if ("REG".equals(searchType.toUpperCase())) {
			Arr = itemMapper.regAvailableItemList(pagingDto.getStart(), pagingDto.getEnd(), itemType);
		}
		return Arr;
	}
	
	/**
	 * <pre>
     * 1. Comment : 아이템 코드값 목록 가져오기(body1,body2, .....)
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05 .27 
	 * </pre> 
	 * @param itemIdxs
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String getItemNames(String itemIdxs) {
		if ("".equals(itemIdxs)) throw new BirdAdminException(BirdAdminErrorCode.BAD_REQUEST);
		
		String[] splitItemIdxs = StringUtil.stringSplit(itemIdxs, ",");
		List<String>Arr = itemMapper.getItemNames(StringUtil.stringArrayToList(splitItemIdxs));
		String[] itemNames = StringUtil.listToStringArray(Arr);
		return StringUtil.stringJoin(itemNames, ",");
 	}
	
	/**
	 * <pre>
     * 1. Comment : 스토어 목록 등록 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 05 .27 
	 * </pre> 
	 * @param itemType
	 * @param itemIdxs
	 * @param storeType
	 * @param cornPrice
	 * @param popcornPrice
	 * @param topMenuId
	 * @param subMenuId
	 * @param disCountPercent
	 * @param saleYn
	 * @param storeCondition
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean insertStore(String itemType, String itemIdxs, String storeType, int cornPrice, int popcornPrice,
    		int topMenuId, int subMenuId, int disCountPercent, int saleYn, int storeCondition) {
		ProductListDto productListDto = new ProductListDto();
		//아이템 값, 스토어타입 값이 있을때
		if (!"".equals(itemIdxs) && !"".equals(storeType)) {
			String[] itemIdx = StringUtil.stringSplit(itemIdxs, ",");	//{1,2,....} 형식 값 자르기
			if (itemIdx.length == 1) {	//등록할 아이템이 하나일 때
				
				String productCodeType = BirdAdminUtil.getProductCodeType(storeType);	//스토어에 등록 될 상품코드 타입가져오기
				HashMap<String, Object>returnMap = new HashMap<>();
				returnMap = this.makeProductLastCode(productCodeType);	//스토어의 등록될 코드값 계산 후 가저오기
				String lastProductCode = (String) returnMap.get("productCode");	// 등록될 상품 코드 값
				String lastProductItemCode = (String) returnMap.get("itemCode");
				
				if (lastProductCode != null) {
					ItemInfoDto itemInfoDto = null;
					if ("NORMAL".equals(itemType)) {
						itemInfoDto = this.getItemInfoDetail(Integer.parseInt(itemIdx[0]));	//아이템에서 스토어에 등록할 아이템 정보 가져오기
						if (itemInfoDto != null) {
							productListDto = new ProductListDto(
									lastProductCode, itemInfoDto.getItemName(), itemInfoDto.getItemDesc(), 
									popcornPrice, cornPrice, itemInfoDto.getItemCode(), topMenuId, subMenuId, 
									topMenuId, subMenuId, disCountPercent, saleYn, storeCondition
							);
						}
					} else if (Value.COLLECTION.equals(itemType)) {	//콜렉션 아이템일때
						CollectionItemInfoDto collectionItemInfoDto = null;
						collectionItemInfoDto = this.collectionItemInfo(itemIdx[0]);
						if (collectionItemInfoDto != null) {
							productListDto = new ProductListDto(
									itemIdx[0], collectionItemInfoDto.getCollectionName(), 
									collectionItemInfoDto.getCollectionDesc(), popcornPrice, cornPrice, 
									lastProductItemCode, topMenuId, subMenuId, topMenuId, subMenuId, 
									disCountPercent, saleYn, storeCondition
							);
						}
					}
					if (productListDto != null) this.productInsert(productListDto);
				}
			} else if (itemIdx.length > 1) {	//등록할 아이템 한개 이상일 때
				for (String itemIds : itemIdx) {
					String productCodeType = BirdAdminUtil.getProductCodeType(storeType);
					HashMap<String, Object>returnMap = new HashMap<>();
					returnMap = this.makeProductLastCode(productCodeType);
					String lastProductCode = (String) returnMap.get("productCode");	// 등록될 상품 코드 값
					String lastProductItemCode = (String) returnMap.get("itemCode");
					
					if (!"".equals(lastProductCode)) {
						ItemInfoDto itemInfoDto = null;
						if ("NORMAL".equals(itemType)) {
							itemInfoDto = this.getItemInfoDetail(Integer.parseInt(itemIds));
							if (itemInfoDto != null) {
								productListDto = new ProductListDto(
										lastProductCode, itemInfoDto.getItemName(), itemInfoDto.getItemDesc(), 
										popcornPrice, cornPrice, itemInfoDto.getItemCode(), topMenuId, subMenuId, 
										topMenuId, subMenuId, disCountPercent, saleYn, storeCondition
								);
							}
						} else if (Value.COLLECTION.equals(itemType)) {	//콜렉션 아이템일때
							CollectionItemInfoDto collectionItemInfoDto = null;
							collectionItemInfoDto = this.collectionItemInfo(itemIds);
							if (collectionItemInfoDto != null) {
								productListDto = new ProductListDto(
										itemIdx[0], collectionItemInfoDto.getCollectionName(), 
										collectionItemInfoDto.getCollectionDesc(), popcornPrice, cornPrice, 
										lastProductItemCode, topMenuId, subMenuId, topMenuId, subMenuId, 
										disCountPercent, saleYn, storeCondition
								);
							}
						}
						if (productListDto != null) this.productInsert(productListDto);
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * <pre>
     * 1. Comment : 스토어 목록 셀렉트 박스 리스트
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .22 
	 * </pre>  
	 * @return
	 */
	public List<ItemTypeDto> storeTypeList() {
        List<ItemTypeDto> Arr = new ArrayList<ItemTypeDto>();
        for (int i=0; i<StoreType.size(); i++) {
        	ItemTypeDto itemTypeDto = new ItemTypeDto(StoreType.codeOf(i).toString(), StoreType.storeName(i));
        	Arr.add(itemTypeDto);
        }
        return Arr;
    }
    
	/**
	 * <pre>
     * 1. Comment : 아이템 목록 셀렉트 박스 리스 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .22 
	 * </pre>  
	 * @return
	 */
    public List<ItemTypeDto> itemTypeList() {
    	List<ItemTypeDto> Arr = new ArrayList<ItemTypeDto>();
    	for (int i=0; i<ItemType.size(); i++) {
    		ItemTypeDto itemTypeDto = new ItemTypeDto(ItemType.codeOf(i).toString(), ItemType.itemNameOf(i));
    		Arr.add(itemTypeDto);
    	}
    	return Arr;
    }

    public List<ItemTypeDto> searchSelectList() {
        List<ItemTypeDto> Arr = new ArrayList<>();
        for (int i=0; i < ItemSerchType.size(); i++) {
        	ItemTypeDto dto = new ItemTypeDto(ItemSerchType.codeOf(i).toString(), ItemSerchType.searchNameOf(i));
        	Arr.add(dto);
        }
        return Arr;
    }

    public List<Integer> itemCategotySelectbox() {
        List<Integer> Arr = new ArrayList<>();
        for (int i=1; i < 6; i++) {
        	Arr.add(i);
        }
        return Arr;
    }

    public List<Integer> itemTypeSelectBox() {
        List<Integer> Arr = new ArrayList<>();
        for (int i=1; i < 5; i++) {
        	Arr.add(i);
        }
        return Arr;
    }

    public List<Object> viewYnSelectBox() {
        List<Object> Arr = new ArrayList<>();
        Arr.add(0, "false");
        Arr.add(1, "true");
        return Arr;
    }
    
    /**
     * <pre>
     * 1. Comment : 가차 사용 내역 리스트 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .22 
	 * </pre> 
     * @param sPage
     * @param pageCnt
     * @param userName
     * @param phoneNumber
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly=true)
    public List<GatchaUseLogDto> gatchaUseLogList(int sPage, int pageCnt,
    		String userName, String phoneNumber, String startDate, String endDate) {
    	List<GatchaUseLogDto> Arr = new ArrayList<GatchaUseLogDto>();
    	
    	PagingDto pagingDto = Util.getPaging(sPage, pageCnt);
    	String start = pagingDto.getStart();
    	String end = pagingDto.getEnd();
    	
    	HashMap<String, Object> paramMap = new HashMap<>();
    	paramMap.put("start", start);
    	paramMap.put("end", end);
    	paramMap.put("userName", Util.isNullValue(userName, ""));
    	paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
    	paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
    	paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
    	
    	Arr = itemMapper.gatchaUseLog(paramMap);
    	return Arr;
    }
    
    /**
     * <pre>
     * 1. Comment : 가차 사용 내역 리스트 개수 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .22 
	 * </pre>  
     * @param userName
     * @param phoneNumber
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly=true)
    public int gatchaUseLogListCnt(String userName, String phoneNumber, 
    		String startDate, String endDate) {
    	HashMap<String, Object> paramMap = new HashMap<>();
    	paramMap.put("userName", Util.isNullValue(userName, ""));
    	paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
    	paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
    	paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
    	
    	return itemMapper.gatchaUseLogCnt(paramMap);
    }
    
    /**
     * <pre>
     * 1. Comment : 둥지 아이템 구매 리스트 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .26 
	 * </pre> 
     * @param sPage
     * @param listCnt
     * @param userName
     * @param phoneNumber
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly=true)
    public List<NestItemBuyLogDto> nestItemBuyLogList(int sPage, int listCnt
    		,String userName, String phoneNumber, String startDate, String endDate) {
    	List<NestItemBuyLogDto> Arr = new ArrayList<>();
    	
    	PagingDto pagingDto = Util.getPaging(sPage, listCnt);
    	String start = pagingDto.getStart();
    	String end = pagingDto.getEnd();
    	
    	HashMap<String, Object> paramMap = new HashMap<>();
    	paramMap.put("start", start);
    	paramMap.put("end", end);
    	paramMap.put("userName", Util.isNullValue(userName, ""));
    	paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
    	paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
    	paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
    	
    	Arr = itemMapper.nestItemBuyLogList(paramMap);
    	return Arr;
    }
    
    /**
     * <pre>
     * 1. Comment : 둥지 아이템 구매 리스트 개수
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 12 .26
	 * </pre>  
     * @param userName
     * @param phoneNumber
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(readOnly=true)
    public int nestItemBuyLogListCnt(String userName, String phoneNumber, 
    			String startDate, String endDate) {
    	HashMap<String, Object> paramMap = new HashMap<>();
    	paramMap.put("userName", Util.isNullValue(userName, ""));
    	paramMap.put("phoneNumber", Util.isNullValue(phoneNumber, ""));
    	paramMap.put("startDate", Util.isNullValue(DateUtils.getOclock(startDate), ""));
    	paramMap.put("endDate", Util.isNullValue(DateUtils.getLastDayOclock(endDate), ""));
    	
    	return itemMapper.nestItemBuyLogListCnt(paramMap);
    }
}
