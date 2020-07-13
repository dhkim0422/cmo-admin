package com.insilicogen.common.code.mapper;

import java.util.List;

import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.code.service.CodeVO;
import com.insilicogen.common.code.service.GroupCodeVO;

public interface CodeMapper {

	public List<GroupCodeVO> selectCodeList(GroupCodeVO groupCodeVO) ;
	public GroupCodeVO selectCode(GroupCodeVO groupCodeVO) ;
	@SessionLoginId
	public int insertCode(CodeVO codeVO) ;
	@SessionLoginId
	public int insertGroupCode(GroupCodeVO groupCodeVO) ;
	@SessionLoginId
	public int updateCode(CodeVO codeVO) ;
	@SessionLoginId
	public int updateGroupCode(GroupCodeVO groupCodeVO) ;
	public int deleteCode(CodeVO codeVO) ;
	public int deleteGroupCode(GroupCodeVO groupCodeVO) ;

	public List<CodeVO> selectCodeListByGroup(CodeVO codeVO) ;
}
