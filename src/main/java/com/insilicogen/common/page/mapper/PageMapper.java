package com.insilicogen.common.page.mapper;

import java.util.List;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.page.service.PageVO;

public interface PageMapper {

	public List<PageVO> selectPageList(PageVO pageVO) ;
	public PageVO selectPage(PageVO pageVO) ;
	@SessionLoginId
	public int insertPage(PageVO pageVO) ;
	@SessionLoginId
	public int updatePage(PageVO pageVO) ;
	public int deletePage(PageVO pageVO) ;

}
