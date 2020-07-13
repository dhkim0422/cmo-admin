package com.insilicogen.common.page.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.page.service.PageService;
import com.insilicogen.common.page.service.PageVO;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.variable.Layout;

@Controller
public class PageController {

	/** PageService */
	@Resource(name = "pageService")
	private PageService pageService;

	@RequestMapping(value = "/common/page/selectPageList.do")
	public String selectPageList(@ModelAttribute PageVO pageVO, ModelMap model, HttpServletRequest request)  {
		return Layout.ADMIN_LAYOUT+"/common/page/selectPageList";
	}

	@RequestMapping(value = "/common/page/selectPageListMulti.do")
	public String selectPageListMulti(@RequestParam String menuSeq, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("menuSeq", menuSeq);
		return Layout.SPA_LAYOUT+"/common/page/selectPageListMulti";
	}

	@ResponseBody
	@RequestMapping(value = "/common/page/getPageList.json")
	public ResultData<List<PageVO>> getPageList(@ModelAttribute PageVO pageVO, ModelMap model,
			HttpServletRequest request, HttpServletResponse response)  {
		return ResponseUtil.response(pageService.selectPageList(pageVO) , pageVO.getDraw());
	}

	@RequestMapping(value = "/common/page/insertPage.do")
	public String insertPage(@ModelAttribute("data") PageVO pageVO, ModelMap model, HttpServletRequest request)  {
		return Layout.SPA_LAYOUT+"/common/page/insertPage";
	}

	@ResponseBody
	@RequestMapping(value = "/common/page/insertPage.json")
	public ResultData<ResultCode> insertPage(@ModelAttribute PageVO pageVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		pageVO.setPageNmEn(pageVO.getPageNm());
		pageService.insertPage(pageVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/common/page/updatePage.do")
	public String updatePage(@ModelAttribute("data") PageVO pageVO, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("data", pageService.selectPage(pageVO));
		return Layout.SPA_LAYOUT+"/common/page/updatePage";
	}

	@ResponseBody
	@RequestMapping(value = "/common/page/updatePage.json")
	public ResultData<ResultCode> updatePage(@ModelAttribute PageVO pageVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		pageVO.setPageNmEn(pageVO.getPageNm());
		pageService.updatePage(pageVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/page/deletePage.json", method = RequestMethod.POST)
	public ResultData<ResultCode>  deletePage(@RequestBody PageVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
	  pageService.deletePage(list);
	  return ResponseUtil.response(ResultCode.SUCCESS);
	}

}
