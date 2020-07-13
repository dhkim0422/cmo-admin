package com.insilicogen.common.board.service;

import java.util.Objects;

import com.insilicogen.common.comm.service.CommonVO;

import lombok.Data;

@Data
public class BoardVO extends CommonVO{

	private String bbsId;
	private String upperBbsId;
	private String bbsSeq;
	private String bbsSe;
	private String bbsSj;
	private String bbsCn;
	private String frtAt;
	private String othbcAt;
	private String useAt;
	private String readCnt;
	private String ipAddr;
	private String answerSttus;
	private String passwd;

	public String getFrtAt() {
		return Objects.isNull(frtAt) ? "N" : frtAt;
	}

}
