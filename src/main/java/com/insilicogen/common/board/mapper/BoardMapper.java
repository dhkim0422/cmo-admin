package com.insilicogen.common.board.mapper;

import java.util.List;

import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.board.service.BoardVO;

public interface BoardMapper {

	public List<BoardVO> selectBoardList(BoardVO boardVO) ;
	public Integer selectBoardListTotCnt(BoardVO boardVO) ;

	public BoardVO selectBoard(BoardVO boardVO) ;
	@SessionLoginId
	public int insertBoard(BoardVO boardVO) ;
	@SessionLoginId
	public int updateBoard(BoardVO boardVO) ;
	public int deleteBoard(BoardVO boardVO) ;
	public int updateReadCnt(BoardVO boardVO) ;

}
