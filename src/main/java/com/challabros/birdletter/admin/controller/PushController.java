package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;

@Controller
public class PushController {
	
	@RequestMapping(value={"/push", "/push/{page_bgn"})
	public ModelAndView push(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {
	
		ModelAndView mvc = new ModelAndView();
		page_gbn =  Util.isNullValue(page_gbn, "");
		
		if ("pushSetting".equals(page_gbn)) {
			mvc.setViewName("push/pushSetting");
		} else if ("pushList".equals(page_gbn)) {
			mvc.setViewName("push/pushList");
		} else if ("pushModify".equals(page_gbn)) {
			mvc.setViewName("push/pushModify");
		} else if ("pushSendLog".equals(page_gbn)) {
			mvc.setViewName("push/pushSendLog");
		} else if ("birthDayPushList".equals(page_gbn)) {
			mvc.setViewName("push/birthDayPushList");
		} else if ("pushSend".equals(page_gbn)) {
			mvc.setViewName("push/pushSend");
		}
		return mvc;
	}

}
