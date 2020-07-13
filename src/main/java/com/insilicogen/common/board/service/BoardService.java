package com.insilicogen.common.board.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import com.insilicogen.common.board.mapper.BoardMapper;

@Service
public class BoardService {

	@Autowired
    private BoardMapper boardMapper;

	public List<BoardVO> selectBoardList(BoardVO boardVO)  {
        return boardMapper.selectBoardList(boardVO);
    }

	public int selectBoardListTotCnt(BoardVO boardVO) {
		return boardMapper.selectBoardListTotCnt(boardVO);
	}

	public BoardVO selectBoard(BoardVO boardVO)  {
		return boardMapper.selectBoard(boardVO);
	}

	public int insertBoard(BoardVO boardVO)  {
		return boardMapper.insertBoard(boardVO);
	}

	public int updateBoard(BoardVO boardVO)  {
		return boardMapper.updateBoard(boardVO);
	}

	public int deleteBoard(BoardVO[] list)  {
		int cnt = 0;
		for(BoardVO p : list) {
			cnt += boardMapper.deleteBoard(p);
		}
		return cnt;
	}

	public int updateReadCnt(BoardVO boardVO)  {
		return boardMapper.updateReadCnt(boardVO);
	}

}
