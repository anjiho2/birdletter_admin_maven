package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class LoginController {
	
	@RequestMapping(value={"/login", "/login/{page_gbn"})
	public ModelAndView login(@RequestParam(value="page_gbn", required=false)String page_gbn) throws Exception {
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("session".equals(page_gbn)) {
			mvc.setViewName("/login/session");
		} else if ("logout".equals(page_gbn)) {
			mvc.setViewName("/login/logout");
		}
		return mvc;
	}

}
