package com.challabros.birdletter.admin.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challabros.birdletter.admin.dto.AdminDto;

public class CreateSESSION {
	
	final static Logger logger = LoggerFactory.getLogger(CreateSESSION.class);
	
	public static AdminDto sessionCF(HttpServletRequest request) throws Exception {
		String param = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		
		logger.info("admin_id : " + param);
		logger.info("admin_pass : " + param2);
	
		AdminDto adminInfo = new AdminDto();
		adminInfo.setAdminId(param);
		adminInfo.setAdminPass(param2);
		request.setAttribute("adminInfo", adminInfo);
		return adminInfo;
	}

}
