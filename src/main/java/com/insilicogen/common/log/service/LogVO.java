package com.insilicogen.common.log.service;

import com.insilicogen.common.comm.service.CommonVO;
import lombok.Data;

@Data
public class LogVO extends CommonVO{

	private String logId;
	private String url;
	private String registIp;

	//TODO 정책에 따라 사용유무 결정할 필요가 있음 / 조일흠 / 2020-06-09
	private String classNm;
	private String methodNm;
	private String processTime;
	private String pageSeq;
	private String menuSeq;

}
