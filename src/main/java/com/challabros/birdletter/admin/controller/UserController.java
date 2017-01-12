package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class UserController {
	
	@RequestMapping(value={"/user", "/user/{page_gbn"})
	public ModelAndView user(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {

		// ModelAndView 초기화
		ModelAndView mvc = new ModelAndView();
		
        page_gbn = Util.isNullValue(page_gbn, "");
        if("list".equals(page_gbn)) {
            mvc.setViewName("/user/list");
        } else if("detail".equals(page_gbn)) {
            mvc.setViewName("/user/detail");
		} else if ("modify".equals(page_gbn)) {
            mvc.setViewName("/user/detail");
		} else if("userItemBuyList".equals(page_gbn)) { 
            mvc.setViewName("/user/userItemBuyList");
		} else if("userItemPresent".equals(page_gbn)) {
            mvc.setViewName("/user/userItemPresent");
		} else if("cornBuyLog".equals(page_gbn)) {
			mvc.setViewName("/user/cornBuyLog");
		} else if("popcornBuyLog".equals(page_gbn)) {
			mvc.setViewName("/user/popcornBuyLog");
		} else if("itemPresent".equals(page_gbn)) {
			mvc.setViewName("/user/itemPresent");
		} else if("letterBirdList".equals(page_gbn)) {
			mvc.setViewName("/user/letterBirdList");
		}
        return mvc;
	}

}
