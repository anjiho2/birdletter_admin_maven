package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;


@Controller
public class ItemController {
	
	/**
	 * <PRE>
	 * 3. Comment    : 아이템관련
	 * 4. 작성자     : 안지호
	 * 5. 작성일     : 2015. 12. 09.
	 * </PRE>
	 *   @return ModelAndView
	 *   @param req			화면정보 객체
	 *   @param page_gbn	페이지 구분자
	 *   @throws Exception
	 */
	@RequestMapping(value={"/item", "/item/{page_gbn"})
	public ModelAndView item(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {

		// ModelAndView 초기화
		ModelAndView mvc = new ModelAndView();
        page_gbn = Util.isNullValue(page_gbn, "");
        
        if("buyList".equals(page_gbn)) {
            mvc.setViewName("/item/buyList");
        } else if("buyListInsert".equals(page_gbn)) {
            mvc.setViewName("/item/buyListInsert");
        } else if("productListInsert".equals(page_gbn)) {
            mvc.setViewName("/item/productListInsert");
        } else if("productList".equals(page_gbn)) {
            mvc.setViewName("/item/productList");
        } else if("productInsert".equals(page_gbn)) {
            mvc.setViewName("/item/productInsert");
        } else if("collectionList".equals(page_gbn)) {
            mvc.setViewName("/item/collectionList");
        } else if("cornInfoList".equals(page_gbn)) {
        	mvc.setViewName("/item/cornInfoList");
        } else if("popcornInfoList".equals(page_gbn)) {
        	mvc.setViewName("/item/popcornInfoList");
        } else if("defaultItemList".equals(page_gbn)) {
        	mvc.setViewName("/item/defaultItemList");
        } else if ("itemList".equals(page_gbn)) {
        	mvc.setViewName("/item/itemList");
        } else if ("insertItemInfo".equals(page_gbn)) {
        	mvc.setViewName("/item/insertItemInfo");
        } else if ("storeInsert".equals(page_gbn)) {
        	mvc.setViewName("/item/storeInsert");
        } else if ("gatchaUseLogList".equals(page_gbn)) {
        	mvc.setViewName("/item/gatchaUseLogList");
        } else if ("nestItemBuyLogList".equals(page_gbn)) {
        	mvc.setViewName("/item/nestItemBuyLogList");
        }
        return mvc;
	}

}
