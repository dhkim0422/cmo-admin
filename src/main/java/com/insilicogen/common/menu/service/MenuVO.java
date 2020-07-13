package com.insilicogen.common.menu.service;

import com.insilicogen.common.comm.service.CommonVO;
import lombok.Data;

@Data
public class MenuVO extends CommonVO{

	private String menuSeq;
	private String upperMenuSeq;
	private String menuNm;
	private String menuNmEn;
	private String param;
	private String pageSeq;
	private String colOrd;
	private String useAt;
	private String menuIcon;

	private String authGroupSeq;
}
