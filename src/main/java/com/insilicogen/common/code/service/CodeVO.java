package com.insilicogen.common.code.service;

import com.insilicogen.common.comm.service.CommonVO;
import com.insilicogen.common.datatable.DataTableFilterCode;
import lombok.Data;

@Data
public class CodeVO extends CommonVO implements DataTableFilterCode{

	private String groupCd;
	private String code;
	private String codeNm;
	private String codeNmEn;
	private String colOrd;
	private String useAt;
	private String codeDc;
	private String codeIcon;

	private String oriCode;

  @Override
  public String getFilterCode() {
    return getCode();
  }

  @Override
  public String getFilterCodeNm() {
    return getCodeNm();
  }

  @Override
  public String getFilterCodeEnNm() {
    return getCodeNmEn();
  }

}
