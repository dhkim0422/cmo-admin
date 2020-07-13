package com.insilicogen.common.inst.web;

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

import com.insilicogen.common.code.service.CodeService;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.inst.service.InstService;
import com.insilicogen.common.inst.service.InstVO;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.variable.Layout;

@Controller
public class InstController {

	/** InstService */
	@Resource(name = "instService")
	private InstService instService;

	/** CodeService */
	@Resource(name = "codeService")
	private CodeService codeService;

	@RequestMapping(value = "/common/inst/selectInstList.do")
	public String selectInstList(@ModelAttribute InstVO instVO, ModelMap model, HttpServletRequest request)  {
		return Layout.ADMIN_LAYOUT+"/common/inst/selectInstList";
	}

	@RequestMapping(value = "/common/inst/selectInstListMulti.do")
	public String selectInstListMulti(@RequestParam String menuSeq, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("menuSeq", menuSeq);
		return Layout.SPA_LAYOUT+"/common/inst/selectInstListMulti";
	}

	@ResponseBody
	@RequestMapping(value = "/common/inst/getInstList.json")
	public ResultData<List<InstVO>> getInstList(@ModelAttribute InstVO instVO, ModelMap model,
			HttpServletRequest request, HttpServletResponse response)  {
		return ResponseUtil.response(instService.selectInstList(instVO) , instVO.getDraw());
	}

	@RequestMapping(value = "/common/inst/insertInst.do")
	public String insertInst(@ModelAttribute("data") InstVO instVO, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("INST_CL_CD", codeService.selectCodeListByGroup("INSTCLCD", "Y"));
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		return Layout.SPA_LAYOUT+"/common/inst/insertInst";
	}

	@ResponseBody
	@RequestMapping(value = "/common/inst/insertInst.json")
	public ResultData<ResultCode> insertInst(@ModelAttribute InstVO instVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		instService.insertInst(instVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@RequestMapping(value = "/common/inst/updateInst.do")
	public String updateInst(@ModelAttribute("data") InstVO instVO, ModelMap model, HttpServletRequest request)  {
		model.addAttribute("INST_CL_CD", codeService.selectCodeListByGroup("INSTCLCD", "Y"));
		model.addAttribute("USE_AT", codeService.selectCodeListByGroup("USEYN", "Y"));
		model.addAttribute("data", instService.selectInst(instVO));
		return Layout.SPA_LAYOUT+"/common/inst/updateInst";
	}

	@ResponseBody
	@RequestMapping(value = "/common/inst/updateInst.json")
	public ResultData<ResultCode> updateInst(@ModelAttribute InstVO instVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
		instService.updateInst(instVO);
		return ResponseUtil.response(ResultCode.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/common/inst/deleteInst.json", method = RequestMethod.POST)
	public ResultData<ResultCode>  deleteInst(@RequestBody InstVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
	  instService.deleteInst(list);
	  return ResponseUtil.response(ResultCode.SUCCESS);
	}

}
