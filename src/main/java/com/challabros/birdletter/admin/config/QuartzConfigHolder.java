package com.challabros.birdletter.admin.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

public class QuartzConfigHolder {
	
	private static QuartzConfigHolder config;
	
	@Value("#{config['service.isQuartzUseCheck']}")
	private boolean isQuartzUseCheck;
	
	@PostConstruct
	private QuartzConfigHolder init() {
		config = this;
		return this;
	}
	
	public static boolean isQuartzUseCheck() {
		return config.isQuartzUseCheck;
	}

}
