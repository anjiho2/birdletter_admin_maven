package com.challabros.birdletter.admin.dto;

import lombok.Data;

@Data
public class ProductListDto {
	private int rowNum;
	private String start;
	private String end;
	
	private String storeType;
	
	private int idx;
	private String productCode;
	private String productName;
	private String productDesc;
	private int popcornPrice;
	private int cornPrice;
	private String itemCode;
	private int itemCategory;
	private int itemType;
	private int storeSubMenuIdx;
	private int storeMainMenuIdx;
	private int discountPercent;
	private int viewYn;
	private int storeCondition;
	private String createDate;
	
	public ProductListDto() {}
	
	public ProductListDto(String productCode, String productName, String productDesc,
			int popcornPrice, int cornPrice, String itemCode, int itemCategory, int itemType, 
			int storeMainMenuIdx, int storeSubMenuIdx, int discountPercent, int viewYn, int storeCondition) {
		this.productCode = productCode;
		this.productName = productName;
		this.productDesc = productDesc;
		this.popcornPrice = popcornPrice;
		this.cornPrice = cornPrice;
		this.itemCode = itemCode;
		this.itemCategory = itemCategory;
		this.itemType = itemType;
		this.storeMainMenuIdx = storeMainMenuIdx;
		this.storeSubMenuIdx = storeSubMenuIdx;
		this.discountPercent = discountPercent;
		this.viewYn = viewYn;
		this.storeCondition = storeCondition;
	}
	/*
	public static ProductListDto insertConsume(String productCode, String productName, String productDesc,
			int popcornPrice, int cornPrice, String itemCode, int itemCategory, int itemType, 
			int storeMainMenuIdx, int storeSubMenuIdx, int discountPercent, int viewYn, int storeCondition
			) {
		ProductListDto productListDto = new ProductListDto();
		productListDto.setProductCode(productCode);
		productListDto.setProductName(productName);
		productListDto.setProductDesc(productDesc);
		productListDto.setPopcornPrice(popcornPrice);
		productListDto.setCornPrice(cornPrice);
		productListDto.setItemCode(itemCode);
		productListDto.setItemCategory(itemCategory);
		productListDto.setItemType(itemType);
		productListDto.setStoreMainMenuIdx(storeMainMenuIdx);
		productListDto.setStoreSubMenuIdx(storeSubMenuIdx);
		productListDto.setDiscountPercent(discountPercent);
		productListDto.setViewYn(viewYn);
		productListDto.setStoreCondition(storeCondition);
		return productListDto;
	}
	*/
}
