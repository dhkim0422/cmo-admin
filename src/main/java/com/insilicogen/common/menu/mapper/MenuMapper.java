package com.insilicogen.common.menu.mapper;

import java.util.List;
import java.util.Map;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.menu.service.MenuVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MenuMapper {

	public List<MenuVO> selectMenuList(MenuVO menuVO) ;
	public List<EgovMap> getSelectMenuPageList(MenuVO menuVO) ;
	@SessionLoginId
	public int saveMenu(MenuVO menuVO) ;
	@SessionLoginId
	public int updateMenuPage(MenuVO menuVO) ;
	@SessionLoginId
	public int updateMenuAuth(List<MenuVO> list) ;
	@SessionLoginId
	public int insertMenuPage(MenuVO menuVO) ;
	public int deleteMenuPage(MenuVO menuVO) ;
	public int deleteMenuAuth(String authGroupSeq) ;
	public int deleteMenu(MenuVO menuVO) ;
	
	public List<Map> selectMyMenuList(Map<String,Integer> param) ;
	public List<Map> selectMyMenuPageUrlList(Map<String,Integer> param) ;
	
	
	
}
