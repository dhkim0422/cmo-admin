package com.insilicogen.common.log.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insilicogen.common.log.service.LogService;
import com.insilicogen.common.page.service.PageVO;
import com.insilicogen.common.variable.Layout;

@Controller
public class LogController {

	/** LogService */
	@Resource(name = "logService")
	private LogService logService;

	@RequestMapping(value = "/common/log/selectLogList.do")
	public String selectPageList(@ModelAttribute PageVO pageVO, ModelMap model, HttpServletRequest request)  {
		return Layout.ADMIN_LAYOUT+"/common/log/selectLogList";
	}
}
