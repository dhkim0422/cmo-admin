package com.insilicogen.common.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import com.insilicogen.common.exception.BaseRuntimeException;
import com.insilicogen.common.user.service.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUtils {

  /**
   * set session info
   */
  public static void setSessionInfo( UserVO user) {
    HttpServletRequest request = HttpUtil.getCurrentRequest();
    setSessionInfo(request, user);
  }
  /**
   * set session info
   */
  public static void setSessionInfo(HttpServletRequest request , UserVO user) {
      request.getSession().setAttribute("sessionUserInfo", user);
  }
  /**
   * get session info
   */
  public static UserVO getSessionInfo(HttpServletRequest request) {
    Object sessionUserInfo = request.getSession().getAttribute("sessionUserInfo");
    if(sessionUserInfo == null) {
        throw new BaseRuntimeException("로그인 정보가 없습니다.") ;
    }
    return (UserVO)sessionUserInfo;
  }
  /**
   * login 여부
   * @param request
   * @return
   */
  public static boolean isLogin(HttpServletRequest request) {
    Object sessionUserInfo = request.getSession().getAttribute("sessionUserInfo");
    if(sessionUserInfo == null) {
        return false;
    }
    return true;
  }

  /**
   * url  여부
   * @param request
   * @return
   */
  public static boolean isRoleUrl(HttpServletRequest request) {
    UserVO sessionUserInfo = (UserVO)request.getSession().getAttribute("sessionUserInfo");
    String url = request.getServletPath();
    log.debug("url : {}" , url);
    AntPathMatcher pathMatcher = new AntPathMatcher();
    return sessionUserInfo.getRoleUrlList().stream().anyMatch(r -> pathMatcher.match(AppUtil.nvl(r.get("url")), url) );
  }

  /**
   * auth url
   * @param request
   * @param menuSeq
   * @param btnId
   * @return
   */
  public static String getBtnUrl(HttpServletRequest request ,String menuSeq ,String btnId) {
    UserVO sessionUserInfo = (UserVO)request.getSession().getAttribute("sessionUserInfo");
    Optional<Map> map = sessionUserInfo.getRoleUrlList().stream().filter(r -> menuSeq.equals(AppUtil.nvl(r.get("menuSeq"))) && btnId.equals(AppUtil.nvl(r.get("btnId"))) ).findFirst();
    String url = AppUtil.nvl(map.orElse(new HashMap()).get("url"));
    if(AppUtil.isNotEmpty(url)) {
      url = request.getContextPath() + url;
    }
    return url;
  }

  /**
   *
   * id 조회
   */
  public static String getLoginId(HttpServletRequest request) {
    return getSessionInfo(request).getUserId();
  }
  /**
   * 세션 invalidate
   * @param request
   */
  public static void invalidate(HttpServletRequest request) {
    request.getSession().invalidate();
  }
}
