package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class SaleController {
	
	@RequestMapping(value={"/sale", "sale{page_gbn}"})
	public ModelAndView sale(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
	
		if ("cornSaleInfo".equals(page_gbn)) {
			mvc.setViewName("/sale/cornSaleInfo");
		} else if ("popcornSaleInfo".equals(page_gbn)) {
			mvc.setViewName("/sale/popcornSaleInfo");
		} else if ("itemSaleRank".equals(page_gbn)) {
			mvc.setViewName("/sale/itemSaleRank");
		} else if ("cornSaleStatistics".equals(page_gbn)) {
			mvc.setViewName("/sale/cornSaleStatistics");
		}
		return mvc;
	}

}
