package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class GiftController {
	
	@RequestMapping(value={"/gift", "/gift/{page_gbn"})
	public ModelAndView present(@RequestParam(value="page_gbn", required=false) String page_gbn)
			throws Exception {
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("giftPresent".equals(page_gbn)) {
			mvc.setViewName("/gift/giftPresent");
		} else if ("presentList".equals(page_gbn)) {
			mvc.setViewName("/gift/presentList");
		} else if ("userPresentList".equals(page_gbn)) {
			mvc.setViewName("/gift/userPresentList");
		} else if ("userCoinPresentList".equals(page_gbn)) {
			mvc.setViewName("/gift/userCoinPresentList");
		} else if ("userBirdPresentList".equals(page_gbn)) {
			mvc.setViewName("/gift/userBirdPresentList");
		}
		return mvc;
	}

}
