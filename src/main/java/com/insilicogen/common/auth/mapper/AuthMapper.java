package com.insilicogen.common.auth.mapper;

import java.util.List;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.auth.service.AuthVO;

public interface AuthMapper {

	public List<AuthVO> selectAuthList(AuthVO authVO) ;
	public AuthVO selectAuth(AuthVO authVO) ;
	@SessionLoginId
	public int insertAuth(AuthVO authVO) ;
	@SessionLoginId
	public int updateAuth(AuthVO authVO) ;
	public int deleteAuth(AuthVO authVO) ;

}
