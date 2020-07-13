package com.insilicogen.common.aspect;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.comm.service.CommonVO;
import com.insilicogen.common.util.ExceptionUtil;
import com.insilicogen.common.util.HttpUtil;
import com.insilicogen.common.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * parameter injection을 한다. (id)
 */
@Slf4j

public class MapperDefaultInjectionAspect {

	//@Around("@within(org.apache.ibatis.annotations.Mapper)")
	@SuppressWarnings("unchecked")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

		try {
			Object[] args = joinPoint.getArgs();

			Signature signature = joinPoint.getSignature();
			//Class<?> returnType = ((MethodSignature) signature).getReturnType();

			//check
			if(((MethodSignature) signature).getMethod().getAnnotation(SessionLoginId.class) == null) {
				return joinPoint.proceed();
			}

			// no argument skip
			if (args == null || args.length <= 0) {
				return joinPoint.proceed();
			}

			HttpServletRequest request = null;
			try {
				request = HttpUtil.getCurrentRequest();
			}
			catch (Exception e) {
				log.debug(e.getMessage(), e);
				return joinPoint.proceed();
			}

			CommonVO vo = null;
			List<CommonVO> list = null;
			int i = 0;

			for (; i < args.length; i++) {
				Object obj = args[i];
				if (obj == null) {
					continue;
				}
				if (obj instanceof CommonVO) {
				    vo = (CommonVO) obj;
				    break;
                }
				if (obj instanceof List) {
				  list = (List<CommonVO>) obj;
                  break;
              }
			}

			if(vo == null && list == null) {
				return joinPoint.proceed();
			}
			//String clientIp = HttpUtil.getRemoteAddr(request);
			String userId = SessionUtils.getLoginId(request);
			if(vo != null) {
			  vo.setRegistId(userId);
			  vo.setUpdateId(userId);
			  args[i] = vo; //replace parameter
			}
			if(list != null) {
			  list.forEach(r->{
			    r.setRegistId(userId);
	            r.setUpdateId(userId);
			  });
			  args[i] = list; //replace parameter
			}
		}
		catch(Exception e) {
		    ExceptionUtil.log(e);
		}
		return joinPoint.proceed();
    }
}
