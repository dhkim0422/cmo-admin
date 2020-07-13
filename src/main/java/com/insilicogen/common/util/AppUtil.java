package com.insilicogen.common.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;


public class AppUtil {

  public static String toJson(Object obj) {
    return new Gson().toJson(obj);
  }
  
  /**
   * Object type 변수가 비어있는지 체크
   * 
   * @param obj
   * @return Boolean : true / false
   */
  public static Boolean isEmpty(Object obj) {
    if (obj == null) {
      return true;
    }

    if (obj instanceof String) {
      return obj == null || "".equals(obj.toString().trim());
    } 
    else if (obj instanceof List<?>) {
      return obj == null || ((List<?>) obj).isEmpty();
    } 
    else if (obj instanceof Map) {
      return obj == null || ((Map<?, ?>) obj).isEmpty();
    } 
    else if (obj instanceof Object[]) {
      return obj == null || Array.getLength(obj) == 0;
    }

    return false;
  }
  /** 
   * @param obj
   * @param defaultValue
   * @return String
   */
  public static String nvl(Object obj, String defaultValue) {
    return isEmpty(obj) ? defaultValue : obj.toString();
  }


  /** 
   * @param obj
   * @return String
   */
  public static String nvl(Object obj) {
    return nvl(obj, "");
  }
  /**
   * Object type 변수가 비어있지 않은지 체크
   * 
   * @param obj
   * @return Boolean : true / false
   */
  public static Boolean isNotEmpty(Object obj) {
    return !isEmpty(obj);
  }

}
