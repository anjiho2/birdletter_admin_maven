package com.challabros.birdletter.admin.util;

import org.json.simple.JSONObject;

/**
 * <pre>
 *  JSON String 빌더.
 * </pre>
 * @author anjiho
 *
 */
public class JsonBuilder {
	
	private JSONObject jsonObject;
	
	public JsonBuilder add(String name, Object value) {
		if (jsonObject == null) 
			jsonObject = new JSONObject();
		jsonObject.put(name, value);
		return this;
	}
	
	public String build() {
		return this.jsonObject.toJSONString();
	}

}
