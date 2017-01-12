package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;


@Controller
public class StatisticsController {
	
	@RequestMapping(value={"/statistics", "/statistics/{page_gbn"})
	public ModelAndView bird(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {

		// ModelAndView 초기화
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
	      
		if("cornPopcornMonthStatistics".equals(page_gbn)) {
		    mvc.setViewName("/statistics/cornPopcornMonthStatistics");
		} else if ("cornPopcornYearStatistics".equals(page_gbn)) {
			mvc.setViewName("/statistics/cornPopcornYearStatistics");
		} else if ("userProductBuyStatistics".equals(page_gbn)) {
			mvc.setViewName("/statistics/userProductBuyStatistics");
		} else if ("userStatistics".equals(page_gbn)) {
			mvc.setViewName("/statistics/userStatistics");
		} else if ("userMessageSendStatistics".equals(page_gbn)) {
			mvc.setViewName("/statistics/userMessageSendStatistics");
		} else if ("userOpenMessageStatistics".equals(page_gbn)) {
			mvc.setViewName("/statistics/userOpenMessageStatistics");
		}
		return mvc;
	}

}
