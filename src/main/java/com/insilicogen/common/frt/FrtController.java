package com.insilicogen.common.frt;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.insilicogen.common.frt.service.FrtVO;

@Controller
public class FrtController {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value="/frt/index.do")
    public String index(@ModelAttribute("searchVO") FrtVO searchVO,
    		ModelMap model,HttpServletRequest request)throws Exception {
		//TODO 매개변수등 필요한지 체크필요, 조일흠, 2020-05-07
		//TODO FrtVO는 무엇을 위한 것임?, 조일흠, 2020-05-07
		//TODO 추후 경로 바꾸어주어야 함, 조일흠, 2020-05-08
		return "/adminStandardLayout/frt/index";
    }
}
