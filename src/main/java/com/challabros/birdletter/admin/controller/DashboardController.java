package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class DashboardController {

	@RequestMapping(value={"/dashboard", "/dashboard/{page_gbn"})
	public ModelAndView dashboard(@RequestParam(value="page_gbn", required=false)String page_gbn) throws Exception {
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("dashboardList".equals(page_gbn)) {
			mvc.setViewName("/dashboard/dashboardList");
		}
		return mvc;
	}
}
