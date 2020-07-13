package com.insilicogen.common.auth.service;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insilicogen.common.comm.service.CommonVO;
import com.insilicogen.common.datatable.DataTableFilterCode;
import com.insilicogen.common.menu.service.MenuVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthVO extends CommonVO implements DataTableFilterCode{
    final static public AuthVO EMPTY = new AuthVO(null);
    
    public AuthVO(String authGroupSeq) {
      setAuthGroupSeq(authGroupSeq);
    }
    
	private String authGroupSeq;
	private String authGroupNm;
	private String authGroupDc;
	private String authClassCd;
	private String authClassCdNm;
	private String useAt;
	
	private List<MenuVO> menuList = new ArrayList<>();

    @Override
    public String getFilterCode() {
      return getAuthGroupSeq();
    }
  
    @Override
    public String getFilterCodeNm() {
      return getAuthGroupNm();
    }
    @Override
    public String getFilterCodeEnNm() {
      return getAuthGroupNm();
    }
}
