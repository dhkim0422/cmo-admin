package com.insilicogen.common.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpUtil {

  //request 객체 조회
  public static HttpServletRequest getCurrentRequest() {
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      return request;
  }
  public static String getURLWithContextPath(HttpServletRequest request) {
      return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
  }
  
  /**
   * get request ip
   * @param request
   * @return
   */
  public static String getRemoteAddr(HttpServletRequest request){
      String ip = request.getHeader("X-Forwarded-For");
      
      if (ip == null) {
          ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null) {
          ip = request.getHeader("WL-Proxy-Client-IP"); // WebLogic
      }
      if (ip == null) {
          ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (ip == null) {
          ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (ip == null) {
          ip = request.getRemoteAddr();
      }
      return ip;
  }

}
