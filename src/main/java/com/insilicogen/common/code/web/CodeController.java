package com.insilicogen.common.code.web;

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
import com.insilicogen.common.code.service.GroupCodeVO;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.variable.Layout;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CodeController {

	/** CodeService */
	@Resource(name = "codeService")
	private CodeService codeService;

	@RequestMapping(value = "/common/code/selectCodeList.do")
	public String selectCodeList(@ModelAttribute GroupCodeVO groupCodeVO, ModelMap model, HttpServletRequest request) {
		return Layout.ADMIN_LAYOUT+"/common/code/selectCodeList";
	}

	@ResponseBody
	@RequestMapping(value = "/common/code/getCodeList.json")
	public ResultData<List<GroupCodeVO>> getCodeList(@ModelAttribute GroupCodeVO groupCodeVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		return ResponseUtil.response(codeService.selectCodeList(groupCodeVO) , groupCodeVO.getDraw());
	}

	@RequestMapping(value = "/common/code/insertCode.do")
	public String insertCode(@ModelAttribute("data") GroupCodeVO groupCodeVO, ModelMap model, HttpServletRequest request) {
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		return Layout.SPA_LAYOUT+"/common/code/insertCode";
	}

	@ResponseBody
	@RequestMapping(value = "/common/code/insertCode.json")
	public ResultData<ResultCode> insertCode(@ModelAttribute("data") GroupCodeVO groupCodeVO, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		codeService.insertCode(groupCodeVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/common/code/updateCode.do")
	public String updateCode(@ModelAttribute("data") GroupCodeVO groupCodeVO, ModelMap model, HttpServletRequest request) {
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		model.addAttribute("data", codeService.selectCode(groupCodeVO));
		return Layout.SPA_LAYOUT+"/common/code/updateCode";
	}

	@ResponseBody
	@RequestMapping(value = "/common/code/updateCode.json")
	public ResultData<ResultCode> updateCode(@ModelAttribute("data") GroupCodeVO groupCodeVO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		codeService.updateCode(groupCodeVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/code/deleteCode.json", method = RequestMethod.POST)
	public ResultData<ResultCode> deleteCode(@RequestBody GroupCodeVO[] list, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		codeService.deleteGroupCode(list);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

}
