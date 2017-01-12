package com.challabros.birdletter.admin.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.challabros.birdletter.admin.dto.CollectionItemInfoDto;
import com.challabros.birdletter.admin.dto.CornInfoDto;
import com.challabros.birdletter.admin.dto.GatchaUseLogDto;
import com.challabros.birdletter.admin.dto.ItemInfoDto;
import com.challabros.birdletter.admin.dto.NestItemBuyLogDto;
import com.challabros.birdletter.admin.dto.PopcornInfoDto;
import com.challabros.birdletter.admin.dto.ProductListDto;
import com.challabros.birdletter.admin.dto.UserItemBuyListDto;
import com.challabros.birdletter.admin.dto.UserProductBuyLogDto;

public interface ItemMapper {
	
	List<UserItemBuyListDto> itemBuyList(UserItemBuyListDto userItemBuyListDto);
	
	int itemBuyListCnt(@Param("phoneNumber")String phoneNumber, @Param("storeType")String storeType);
	
	List<UserProductBuyLogDto> itemBuyListAll();
	
	int userProductBuyLogListCnt(@Param("userId")Long userId, @Param("productCode")String productCode, @Param("productName")String productName);
	
	List<UserProductBuyLogDto> userProductBuyLogList(UserProductBuyLogDto userProductBuyLogDto);
	
	int productListCnt(ProductListDto productListDto);
	
	List<ProductListDto> productList(ProductListDto productListDto);
	
	ProductListDto productListDetail(@Param("productIdx")int produdctId);
	
	ProductListDto makeProductLastCode(@Param("storeType")String storeType);
	
	String findLastCollectionCode();
	
	String getNewItemCode(@Param("itemType")String itemType);
	
	ItemInfoDto getItemInfoDetail(@Param("itemIdx")int itemIdx);
	
	int exitsProduct(@Param("productCode")String productCode, @Param("itemCode")String itemCode);
	
	int itemListCnt(@Param("itemType")String itemType);
	
	int regAvailableItemListCnt(@Param("itemType")String itemType);
	
	List<ItemInfoDto> itemList(@Param("start")String start, @Param("end")String end, @Param("itemType")String itemType);
	
	List<ItemInfoDto> regAvailableItemList(@Param("start")String start, @Param("end")String end, @Param("itemType")String itemType);
	
	CollectionItemInfoDto collectionItemInfo(@Param("productCode")String productCode);
	
	String getItemName(@Param("itemCode")String itemCode);
	
	List<ProductListDto> getItemList(@Param("itemType")String itemType);
	
	List<PopcornInfoDto> popcornInfoList(@Param("start")String start, @Param("end")String end);
	
	int popcornInfoListCnt();
	
	PopcornInfoDto popcornInfoDetail(@Param("popcornIdx")int popcornIdx);
	
	List<GatchaUseLogDto> gatchaUseLog(HashMap<String, Object>paramMap);
	
	int gatchaUseLogCnt(HashMap<String, Object>paramMap);
	
	List<NestItemBuyLogDto>nestItemBuyLogList(HashMap<String, Object>paramMap);
	
	int nestItemBuyLogListCnt(HashMap<String, Object>paramMap);
	
	/** INSERT **/
	int insertProduct(ProductListDto productListDto);
	
	int insertItemInfo(ItemInfoDto itemInfoDto);
	
	int collectionItemInfoInsert(CollectionItemInfoDto collectionItemInfoDto);
	
	List<CollectionItemInfoDto> collectionItemInfoList(@Param("start")String start, @Param("end")String end, @Param("productCode")String productCode);
	
	List<CollectionItemInfoDto> regAvailableCollectionItemInfoList(@Param("start")String start, @Param("end")String end);
	
	int collectionItemInfoListCnt(@Param("productCode")String productCode);
	
	int regAvailableCollectionItemInfoListCnt();
	
	HashMap<String, Object> finfUserCornPopcornPayTotal(@Param("userId")Long userId);
	
	List<CornInfoDto> cornInfoList(@Param("start")String start, @Param("end")String end);
	
	int cornInfoListCnt();
	
	CornInfoDto cornInfoDetail(@Param("cornIdx")int cornIdx);
	
	List<String> getItemNames(@Param("idxs")List<String>arrayList);
	
	/** DELETE **/
	int productDelete(@Param("productIdx")int productIdx);
	
	int deleteItemInfo(@Param("itemIdx")int itemIdx);
	
	int deleteCollectionItem(@Param("productCode")String productCode);
	
	/** UPDATE **/
	int productModify(ProductListDto productListDto);
	
	int modifyItemInfo(@Param("itemIdx")int itemIdx, @Param("itemName")String itemName, @Param("itemDesc")String itemDesc);
	
	int updateCollectionItemInfo(CollectionItemInfoDto collectionItemInfoDto);
	
	int modifyCornInfo(CornInfoDto cornInfoDto);
	

}
