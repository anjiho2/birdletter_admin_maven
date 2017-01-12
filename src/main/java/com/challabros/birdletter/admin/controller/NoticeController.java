package com.challabros.birdletter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.challabros.birdletter.admin.util.Util;


@Controller
public class NoticeController {

	@RequestMapping(value={"/noti", "/notice/{page_gbn"})
	public ModelAndView notice(@RequestParam(value = "page_gbn", required = false) String page_gbn) throws Exception {
		
		ModelAndView mvc = new ModelAndView();
		page_gbn = Util.isNullValue(page_gbn, "");
		
		if ("noticeInsert".equals(page_gbn)) {
			mvc.setViewName("notice/noticeInsert");
		} else if ("noticeList".equals(page_gbn)) {
			mvc.setViewName("notice/noticeList");
		} else if ("noticeModify".equals(page_gbn)) {
			mvc.setViewName("notice/noticeModify");
		} else if ("popupNoticeList".equals(page_gbn)) {
			mvc.setViewName("notice/popupNoticeList");
		} else if ("insertPopupNotice".equals(page_gbn)) {
			mvc.setViewName("notice/insertPopupNotice");
		} else if ("detailPopupNotice".equals(page_gbn)) {
			mvc.setViewName("notice/detailPopupNotice");
		} else if ("birdTipList".equals(page_gbn)) {
			mvc.setViewName("notice/birdTipList");
		}
		return mvc;
	}
}
