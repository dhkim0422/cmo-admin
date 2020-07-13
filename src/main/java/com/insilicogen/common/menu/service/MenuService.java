package com.insilicogen.common.menu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.insilicogen.common.menu.mapper.MenuMapper;

import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service
public class MenuService {
    @Value("#{prop['nologin.auth.seq']}")
    private int noLoginAuthSeq;
  
	@Autowired
    private MenuMapper menuMapper;

	public List<MenuVO> selectMenuList(MenuVO menuVO)  {
        return menuMapper.selectMenuList(menuVO);
    }

	public List<EgovMap> getSelectMenuPageList(MenuVO menuVO)  {
		return menuMapper.getSelectMenuPageList(menuVO);
	}

	public int saveMenu(MenuVO menuVO)  {
		return menuMapper.saveMenu(menuVO);
	}

	public int updateMenuPage(MenuVO menuVO)  {
		return menuMapper.updateMenuPage(menuVO);
	}

	public int insertMenuPage(MenuVO[] list)  {
		int cnt = 0;
		for(MenuVO p : list) {
			p.setRegistId("administrator");
			p.setUpdateId("administrator");
			cnt += menuMapper.insertMenuPage(p);
		}
		return cnt;
	}

	public int deleteMenuPage(MenuVO menuVO)  {
		return menuMapper.deleteMenuPage(menuVO);
	}

	public int deleteMenu(MenuVO[] list)  {
		int cnt = 0;
		for(MenuVO p : list) {
			cnt += menuMapper.deleteMenu(p);
		}
		return cnt;
	}

	public List<Map> selectMyMenuList( int myAuthGroupSeq ) {
	  Map<String, Integer> param = getAuthParam(myAuthGroupSeq);
	  return menuMapper.selectMyMenuList(param);
	}

	public List<Map> selectMyMenuPageUrlList( int myAuthGroupSeq ) {
	  Map<String, Integer> param = getAuthParam(myAuthGroupSeq);
      return menuMapper.selectMyMenuPageUrlList(param);
	}
	
	private Map<String, Integer> getAuthParam(int myAuthGroupSeq) {
	    Map<String,Integer> param = new HashMap<String, Integer>(){{
	        put("noLoginAuthSeq", noLoginAuthSeq);
	        put("myAuthGroupSeq", myAuthGroupSeq);
	      }};
	    return param;
	}
}
