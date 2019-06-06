package com.dream.chat.cache.dto;

import com.dream.chat.entity.Sms;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     批量短信传输对象
 * </p>
 * @author lw
 * @since 2018-10-23
 */
@Data
public class SmsBatchDTO {

	private List<String> singNames;
	private List<String> phones;
	private String templateCode;
	private List<String> templateParams;

	private List<Sms> smsList;

	public void init(){
		singNames=new ArrayList<>();
		phones=new ArrayList<>();
		templateParams=new ArrayList<>();
		smsList=new ArrayList<>();

	}

}
