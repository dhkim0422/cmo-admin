package com.insilicogen.common.code.service;

import java.util.List;

import com.insilicogen.common.comm.service.CommonVO;

import lombok.Data;

@Data
public class GroupCodeVO extends CommonVO{

	private String groupCd;
	private String groupNm;
	private String groupNmEn;
	private String groupAbr;
	private String useAt;

	private List<CodeVO> codeList;
	private List<String> delList;

}
