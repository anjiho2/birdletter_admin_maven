package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class VersionController {
	
	@RequestMapping(value={"/version", "/version/{page_gbn"})
	public ModelAndView version(@RequestParam(value = "page_gbn", required=false) 
			String page_gbn) throws Exception {
		
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("versionInfo".equals(page_gbn)) {
			mvc.setViewName("/version/versionInfo");
		} else if ("versionInfoTest".equals(page_gbn)) {
			mvc.setViewName("/version/versionInfoTest");
		}
		
		return mvc;
	}

}
