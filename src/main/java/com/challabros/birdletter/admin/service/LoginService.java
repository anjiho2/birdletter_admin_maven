package com.challabros.birdletter.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {
	
	final static Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	public int loginChk(String adminId, String adminPass) throws Exception {
		int chk = 0;
		
		try {
			if((adminId.equals("admin") && adminPass.equals("!qmfhtm01")) || 
            		(adminId.equals("playajin") && adminPass.equals("khatah"))) {
				chk = 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return chk;
	}

}
