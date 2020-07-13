package com.insilicogen.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.variable.Layout;
import lombok.extern.slf4j.Slf4j;

/**
 * global 예외처리
 * json / html
 *
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler {
	

	/**
	 * Exception handler
	 */
	@ExceptionHandler(Exception.class)
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception ex)  {
		ResultData<String> apiResultData = new ResultData<>(HttpStatus.INTERNAL_SERVER_ERROR);
		apiResultData.setMessage("에러가 발생하였습니다.");
		return this.process(request, response, apiResultData, ex);
    }
	/**
	 * BaseException 처리
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
    public Object baseException(HttpServletRequest request, HttpServletResponse response, BaseException ex) {
	  ResultData<String> apiResultData = new ResultData<>(HttpStatus.INTERNAL_SERVER_ERROR);
      apiResultData.setMessage(ex.getMessage());
      return this.process(request, response, apiResultData, ex);
    };
    /**
     * BaseException 처리
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public Object baseException(HttpServletRequest request, HttpServletResponse response, BaseRuntimeException ex) {
      ResultData<String> apiResultData = new ResultData<>(HttpStatus.INTERNAL_SERVER_ERROR);
      apiResultData.setMessage(ex.getMessage());
      return this.process(request, response, apiResultData, ex);
    };
    /**
     * BizException 처리
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Object bizException(HttpServletRequest request, HttpServletResponse response, BizException ex) {
      ResultData<String> apiResultData = new ResultData<>(HttpStatus.INTERNAL_SERVER_ERROR);
      apiResultData.setMessage(ex.getMessage());
      return this.process(request, response, apiResultData , ex);
    };

	/**
	 * 예외 처리
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
    public Object process(HttpServletRequest request, HttpServletResponse response, ResultData<String> apiResultData , Exception e) {
      logging(request, e);
      MediaType resultMediaType = MediaType.APPLICATION_JSON_UTF8;
      String accept = request.getHeader("Accept");
      if (accept.toString().toLowerCase().startsWith("text/html")) {
          return new ModelAndView(Layout.LOGIN_LAYOUT+"/error/error");
      }
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .contentType(resultMediaType)
              .body(apiResultData);
    }
    /**
	 * Logging.
	 */
	protected void logging(HttpServletRequest request, Exception e) {
        log.error("error : {}" , e);
    }

}