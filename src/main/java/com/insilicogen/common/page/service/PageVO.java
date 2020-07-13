package com.insilicogen.common.page.service;

import com.insilicogen.common.comm.service.CommonVO;
import lombok.Data;

@Data
public class PageVO extends CommonVO{

	private int pageSeq;
	private String pageNm;
	private String pageNmEn;
	private String url;
	private String btnId;
	private String pageUse;
}
