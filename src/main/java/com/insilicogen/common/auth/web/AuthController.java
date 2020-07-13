package com.insilicogen.common.auth.web;

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
import com.insilicogen.common.auth.service.AuthService;
import com.insilicogen.common.auth.service.AuthVO;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.menu.service.MenuService;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.variable.Layout;

@Controller
public class AuthController {

	/** AuthService */
	@Resource(name = "authService")
	private AuthService authService;

	/** MenuService */
	@Resource(name = "menuService")
	private MenuService menuService;


	@RequestMapping(value = "/common/auth/selectAuthList.do")
	public String selectAuthList(@ModelAttribute AuthVO authVO, ModelMap model, HttpServletRequest request)  {
		return Layout.ADMIN_LAYOUT+"/common/auth/selectAuthList";
	}

	@ResponseBody
	@RequestMapping(value = "/common/auth/getAuthList.json")
	public ResultData<List<AuthVO>> getAuthList(@ModelAttribute AuthVO authVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
	    return ResponseUtil.response(authService.selectAuthList(authVO) , authVO.getDraw());
	}
	
	@RequestMapping(value = "/common/auth/insertAuth.do")
	public String insertAuth(@ModelAttribute("data") AuthVO authVO, ModelMap model, HttpServletRequest request)  {
		return Layout.SPA_LAYOUT+"/common/auth/insertAuth";
	}

	@ResponseBody
	@RequestMapping(value = "/common/auth/insertAuth.json", method = RequestMethod.POST)
	public ResultData<ResultCode> insertAuth(@RequestBody AuthVO authVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		authService.insertAuth(authVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/common/auth/updateAuth.do")
	public String updateAuth(@ModelAttribute("data") AuthVO authVO, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("data", authService.selectAuth(authVO));
		return Layout.SPA_LAYOUT+"/common/auth/updateAuth";
	}

	@ResponseBody
	@RequestMapping(value = "/common/auth/updateAuth.json", method = RequestMethod.POST)
	public ResultData<ResultCode> updateAuth(@RequestBody AuthVO authVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
	    authService.updateAuth(authVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/auth/deleteAuth.json", method = RequestMethod.POST)
	public ResultData<ResultCode> deleteAuth(@RequestBody AuthVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
	    authService.deleteAuth(list);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

}
