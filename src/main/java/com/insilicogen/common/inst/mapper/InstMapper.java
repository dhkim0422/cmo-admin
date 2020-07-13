package com.insilicogen.common.inst.mapper;

import java.util.List;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.inst.service.InstVO;

public interface InstMapper {

	public List<InstVO> selectInstList(InstVO instVO) ;
	public InstVO selectInst(InstVO instVO) ;
	@SessionLoginId
	public int insertInst(InstVO instVO) ;
	@SessionLoginId
	public int updateInst(InstVO instVO) ;
	public int deleteInst(InstVO instVO) ;

}
