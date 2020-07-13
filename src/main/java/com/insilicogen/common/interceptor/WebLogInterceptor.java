package com.insilicogen.common.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.insilicogen.common.log.service.LogService;
import com.insilicogen.common.log.service.LogVO;
import com.insilicogen.common.util.SessionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebLogInterceptor extends HandlerInterceptorAdapter{

	/** LogService */
	@Resource(name = "logService")
	private LogService logService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		LogVO logVO = new LogVO();

		// TODO 권한이 있는지 확인하는 부분 추가 필요 / 조일흠 / 2020-06-09
		Boolean isAuthenticated = true;

		// TODO 웹 로그정보를 생성 필요 / 조일흠 / 2020-06-09
		logVO.setUrl(request.getRequestURI());
		logVO.setRegistId(isAuthenticated && SessionUtils.isLogin(request) ?  SessionUtils.getLoginId(request) : "");
		logVO.setRegistIp(request.getRemoteAddr());
		logVO.setClassNm(( handler instanceof HandlerMethod ) ? ((HandlerMethod) handler).getBeanType().getName() : "");
		logVO.setMethodNm(( handler instanceof HandlerMethod ) ? ((HandlerMethod) handler).getMethod().getName() : "");

		logService.insertLog(logVO);
	}

}
