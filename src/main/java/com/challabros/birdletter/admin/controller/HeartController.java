package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class HeartController {
	
	@RequestMapping(value={"/heart", "/heart/page_gbn"})
	public ModelAndView heart(@RequestParam(value="page_gbn", required=false)String page_gbn)
		throws Exception {
		
		ModelAndView mvc = new ModelAndView();
		
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if("todayPresent".equals(page_gbn)) {
			mvc.setViewName("/heart/todayPresent");
		}
		return mvc;
	}
}
