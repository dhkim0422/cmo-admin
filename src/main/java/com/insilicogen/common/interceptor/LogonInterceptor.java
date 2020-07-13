package com.insilicogen.common.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.insilicogen.common.util.SessionUtils;
import com.insilicogen.common.variable.Layout;

@Slf4j
public class LogonInterceptor extends HandlerInterceptorAdapter {

	private final String AJAX_HEADER = "AJAX";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ModelAndViewDefiningException {
		//세션정보
	     if(!SessionUtils.isLogin(request) ) {
		    if(isAjax(request)) {
		       response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session Expired");
               return false;
		    }
	    	loginPage();
		 }
	     //role 정보
	     if( !SessionUtils.isRoleUrl(request)) {
           if(isAjax(request)) {
              response.sendError(HttpServletResponse.SC_FORBIDDEN, "not page auth");
              return false;
           }
           loginPage();
       }
		return true;
	}
	
    private void loginPage() throws ModelAndViewDefiningException {
      ModelAndView modelAndView = new ModelAndView(Layout.LOGIN_LAYOUT+"/common/user/login");
      throw new ModelAndViewDefiningException(modelAndView);
    }
	/**
	 * ajax check
	 * @param request
	 * @return
	 */
	private boolean isAjax(HttpServletRequest request) {
	  return (request.getHeader(AJAX_HEADER) != null && request.getHeader(AJAX_HEADER).equals("TRUE"))
	      || ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")))
	      ;
	}
}
