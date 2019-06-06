package com.dream.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *  自定义json工具
 * </p>
 *
 * @author lw
 * @since 2018-10-24
 */
public class MmlJsonUtil {

	/**
	 * 对象转json
	 * @param obj
	 * @return
	 */
	public static String objToJson(Object obj){
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonToObj(String json,Class<T> clazz){
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			return objectMapper.readValue(json.getBytes(StandardCharsets.UTF_8),clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
