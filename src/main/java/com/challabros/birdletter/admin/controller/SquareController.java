package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class SquareController {
	
	@RequestMapping(value={"/square", "/square/{page_gbn}"})
	public ModelAndView square(@RequestParam(value = "page_gbn", required = false)String page_gbn) throws Exception {
		ModelAndView mvc = new ModelAndView();
		
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("squareList".equals(page_gbn)) {
			mvc.setViewName("/square/squareList");
		} else if ("squareListDetail".equals(page_gbn)) {
			mvc.setViewName("/square/squareListDetail");
		}
		return mvc;
	}

}
