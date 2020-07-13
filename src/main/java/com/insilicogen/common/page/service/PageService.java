package com.insilicogen.common.page.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insilicogen.common.page.mapper.PageMapper;

@Service
public class PageService {

	@Autowired
    private PageMapper pageMapper;

	public List<PageVO> selectPageList(PageVO pageVO)  {
        return pageMapper.selectPageList(pageVO);
    }

	public PageVO selectPage(PageVO pageVO)  {
		return pageMapper.selectPage(pageVO);
	}

	public int insertPage(PageVO pageVO)  {
		return pageMapper.insertPage(pageVO);
	}

	public int updatePage(PageVO pageVO)  {
		return pageMapper.updatePage(pageVO);
	}

	public int deletePage(PageVO[] list)  {
		int cnt = 0;
		for(PageVO p : list) {
			cnt += pageMapper.deletePage(p);
		}
		return cnt;
	}
}
