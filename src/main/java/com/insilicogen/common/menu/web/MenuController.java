package com.insilicogen.common.menu.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import com.insilicogen.common.code.service.CodeService;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.menu.service.MenuService;
import com.insilicogen.common.menu.service.MenuVO;
import com.insilicogen.common.page.service.PageService;
import com.insilicogen.common.page.service.PageVO;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.variable.Layout;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class MenuController {

	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;

	/** CodeService */
	@Resource(name = "codeService")
	private CodeService codeService;

	/** PageService */
	@Resource(name = "pageService")
	private PageService pageService;

	@RequestMapping(value = "/common/menu/selectMenuList.do")
	public String selectCodeList(@ModelAttribute("data") MenuVO menuVO, ModelMap model, HttpServletRequest request) {
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		model.addAttribute("MENU_LIST", menuService.selectMenuList(menuVO));
		PageVO pageVO = new PageVO();
		pageVO.setPageUse("N");
		List<PageVO> pageList = pageService.selectPageList(pageVO);
		model.addAttribute("PAGE_LIST", pageList);
		return Layout.ADMIN_LAYOUT+"/common/menu/selectMenuList";
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/getMenuList.json")
	public ResultData<List<MenuVO>> getCodeList(@ModelAttribute MenuVO menuVO, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		return ResponseUtil.response(menuService.selectMenuList(menuVO), menuVO.getDraw());
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/getSelectMenuPageList.json")
	public ResultData<List<EgovMap>> getSelectMenuPageList(@ModelAttribute MenuVO menuVO, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		return ResponseUtil.response(menuService.getSelectMenuPageList(menuVO), menuVO.getDraw());
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/saveMenu.json")
	public ResultData<ResultCode> saveMenu(@ModelAttribute MenuVO menuVO, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
		menuService.saveMenu(menuVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/updateMenuPage.json")
	public ResultData<ResultCode> updateMenuPage(@ModelAttribute MenuVO menuVO, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
	    menuService.updateMenuPage(menuVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/insertMenuPage.json")
	public ResultData<ResultCode> insertMenuPage(@RequestBody MenuVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
	    menuService.insertMenuPage(list);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/deleteMenuPage.json", method = RequestMethod.POST)
	public ResultData<ResultCode> deleteMenuPage(@RequestBody MenuVO menuVO, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
	    menuService.deleteMenuPage(menuVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/menu/deleteMenu.json", method = RequestMethod.POST)
	public ResultData<ResultCode> deleteMenu(@RequestBody MenuVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response) {
	    menuService.deleteMenu(list);
		return	ResponseUtil.response(ResultCode.SUCCESS);
	}

}
