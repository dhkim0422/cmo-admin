package com.insilicogen.common.inst.service;

import com.insilicogen.common.comm.service.CommonVO;
import lombok.Data;

@Data
public class InstVO extends CommonVO{

	private String instId;
	private String instNm;
	private String instNmEn;
	private String instAbrvNm;
	private String instAbrvNmEn;
	private String instCd;
	private String instClCd;
	private String instIcon;
	private String instLink;
	private String instDc;
	private String sortOrdr;
	private String useAt;
}
