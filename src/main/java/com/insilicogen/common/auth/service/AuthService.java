package com.insilicogen.common.auth.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.insilicogen.common.auth.mapper.AuthMapper;
import com.insilicogen.common.menu.mapper.MenuMapper;

@Service
public class AuthService {

	@Autowired
    private AuthMapper authMapper;

	@Autowired
	private MenuMapper menuMapper;

	public List<AuthVO> selectAuthList(AuthVO authVO)  {
        return authMapper.selectAuthList(authVO);
    }

	public AuthVO selectAuth(AuthVO authVO)  {
		return authMapper.selectAuth(authVO);
	}

	public int insertAuth(AuthVO authVO)  {
		String authGroupSeq = authVO.getAuthGroupSeq();

		int cnt = authMapper.insertAuth(authVO);

		menuMapper.deleteMenuAuth(authGroupSeq);
		menuMapper.updateMenuAuth(authVO.getMenuList());

		return cnt;
	}

	public int updateAuth(AuthVO authVO)  {
		int cnt = authMapper.updateAuth(authVO);

		menuMapper.deleteMenuAuth(authVO.getAuthGroupSeq());
		menuMapper.updateMenuAuth(authVO.getMenuList());

		return cnt;
	}

	public int deleteAuth(AuthVO[] list)  {
		int cnt = 0;
		for(AuthVO p : list) {
			cnt += authMapper.deleteAuth(p);
			menuMapper.deleteMenuAuth(p.getAuthGroupSeq());
		}
		return cnt;
	}
}
