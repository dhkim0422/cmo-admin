package com.insilicogen.common.board.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insilicogen.common.board.service.BoardService;
import com.insilicogen.common.board.service.BoardVO;
import com.insilicogen.common.board.type.BoardColumn;
import com.insilicogen.common.code.service.CodeService;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.datatable.ColumnType;
import com.insilicogen.common.datatable.DataTableRequest;
import com.insilicogen.common.datatable.FilterType;
import com.insilicogen.common.datatable.PaginationData;
import com.insilicogen.common.jfile.service.JFileService;
import com.insilicogen.common.util.ResponseUtil;

@Controller
public class BoardController {

	/** BoardService */
	@Resource(name = "boardService")
	private BoardService boardService;

	/** CodeService */
	@Resource(name = "codeService")
	private CodeService codeService;

	/** codeService */
	@Resource(name = "JFileService")
	private JFileService jfileService ;

	@RequestMapping(value = "/common/{codeTy}/{codeId}/selectBoardList.do")
	public String selectBoardList(@ModelAttribute BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("codeTy", codeTy);
		model.addAttribute("codeId", codeId);
		return "/adminStandardLayout/common/"+codeTy+"/selectBoardList";
	}

	@ResponseBody
	@RequestMapping(value = "/common/{codeTy}/{codeId}/getBoardList.json")
	public ResultData<List<BoardVO>> getBoardList(@ModelAttribute BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
		DataTableRequest dataTableInRQ = new DataTableRequest(request, new ColumnType() {
			@Override
			public String getColumn(String dataTableHead) {
				return BoardColumn.getColumn(dataTableHead);
			}
			@Override
	        public FilterType getFilterType(String dataTableHead) {
	          return FilterType.LIKE;
	        }
		});
		PaginationData pagination = dataTableInRQ.getPaginationRequest();
		boardVO.setFilterByClause(pagination.getFilterByClause());
		boardVO.setOrderByClause(pagination.getOrderByClause());

		int totalCnt = boardService.selectBoardListTotCnt(boardVO);
		boardVO.setTotalCnt(totalCnt);
        List<BoardVO> selectBoardList = boardService.selectBoardList(boardVO);
        return ResponseUtil.response(selectBoardList, boardVO);
	}

	@RequestMapping(value = "/common/{codeTy}/{codeId}/selectBoard.do")
	public String selectBoard(@ModelAttribute("data") BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model, HttpServletRequest request)  {
		boardService.updateReadCnt(boardVO);
		model.addAttribute("codeTy", codeTy);
		model.addAttribute("codeId", codeId);
		model.addAttribute("data", boardService.selectBoard(boardVO));
		return "/spaStandardLayout/common/"+codeTy+"/selectBoard";
	}

	@RequestMapping(value = "/common/{codeTy}/{codeId}/insertBoard.do")
	public String insertBoard(@ModelAttribute("data") BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("codeTy", codeTy);
		model.addAttribute("codeId", codeId);
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		return "/spaStandardLayout/common/"+codeTy+"/insertBoard";
	}

	@ResponseBody
	@RequestMapping(value = "/common/{codeTy}/{codeId}/insertBoard.json")
	public ResultData<BoardVO> insertBoard(@ModelAttribute BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		boardService.insertBoard(boardVO);
		return ResponseUtil.response(boardVO);
	}

	@RequestMapping(value = "/common/{codeTy}/{codeId}/updateBoard.do")
	public String updateBoard(@ModelAttribute("data") BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("codeTy", codeTy);
		model.addAttribute("codeId", codeId);
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		model.addAttribute("data", boardService.selectBoard(boardVO));
		return "/spaStandardLayout/common/board/updateBoard";
	}

	@ResponseBody
	@RequestMapping(value = "/common/{codeTy}/{codeId}/updateBoard.json")
	public ResultData<BoardVO> updateBoard(@ModelAttribute BoardVO boardVO, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		boardService.updateBoard(boardVO);
		return ResponseUtil.response(boardVO);
	}

	@ResponseBody
	@RequestMapping(value = "/common/{codeTy}/{codeId}/deleteBoard.json", method = RequestMethod.POST)
	public ResultData<ResultCode>  deleteBoard(@RequestBody BoardVO[] list, @PathVariable("codeTy") String codeTy, @PathVariable("codeId") String codeId, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		boardService.deleteBoard(list);
		//파일 삭제
		for(BoardVO p : list) {
			jfileService.removeAttachFile(p.getBbsId());
		};
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

}
