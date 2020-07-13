package com.insilicogen.common.util;
import org.springframework.http.HttpStatus;
import com.insilicogen.common.comm.service.CommonVO;
import com.insilicogen.common.comm.service.ResultData;


public class ResponseUtil {
  public static <E> ResultData<E>  response(E data) {
    ResultData<E> apiResultData = new ResultData<>(HttpStatus.OK);
    apiResultData.setData(data);
    return apiResultData;
  }
  public static <E> ResultData<E>  response(E data , String message) {
    ResultData<E> apiResultData = new ResultData<>(HttpStatus.OK);
    apiResultData.setData(data);
    apiResultData.setMessage(message);
    return apiResultData;
  }
  
  public static <E> ResultData<E>  response(E data , CommonVO vo ) {
    ResultData<E> apiResultData = new ResultData<>(HttpStatus.OK);
    apiResultData.setData(data);
    apiResultData.setRecordsFiltered(vo.getTotalCnt());
    apiResultData.setRecordsTotal(vo.getTotalCnt());
    apiResultData.setDraw(vo.getDraw());
    return apiResultData;
  }
  public static <E> ResultData<E>  response(E data, int draw) {
    ResultData<E> apiResultData = new ResultData<>(HttpStatus.OK);
    apiResultData.setData(data);
    apiResultData.setDraw(draw);
    return apiResultData;
  }
}
