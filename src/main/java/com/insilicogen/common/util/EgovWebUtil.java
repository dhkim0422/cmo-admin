package com.insilicogen.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 교차접속 스크립트 공격 취약성 방지(파라미터 문자열 교체)
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    	--------    ---------------------------
 *   2011.10.10  한성곤          최초 생성
 *
 * </pre>
 */

public class EgovWebUtil {
	private static Logger logger = LoggerFactory.getLogger(EgovWebUtil.class);
	public static String clearXSSMinimum(String value) {
		if (value == null || value.trim().equals("")) {
			return "";
		}

		String returnValue = value;

		returnValue = returnValue.replaceAll("&", "&amp;");
		returnValue = returnValue.replaceAll("<", "&lt;");
		returnValue = returnValue.replaceAll(">", "&gt;");
		returnValue = returnValue.replaceAll("\"", "&#34;");
		returnValue = returnValue.replaceAll("\'", "&#39;");
		return returnValue;
	}

	public static String clearXSSMaximum(String value) {
		String returnValue = value;
		returnValue = clearXSSMinimum(returnValue);

		returnValue = returnValue.replaceAll("%00", null);

		returnValue = returnValue.replaceAll("%", "&#37;");

		// \\. => .

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\
		returnValue = returnValue.replaceAll("\\./", ""); // ./
		returnValue = returnValue.replaceAll("%2F", "");

		return returnValue;
	}

	public static String filePathBlackList(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("\\.\\./", ""); // ../
		returnValue = returnValue.replaceAll("\\.\\.\\\\", ""); // ..\

		return returnValue;
	}

	/**
	 * 행안부 보안취약점 점검 조치 방안.
	 *
	 * @param value
	 * @return
	 */
	public static String filePathReplaceAll(String value) {
		String returnValue = value;
		if (returnValue == null || returnValue.trim().equals("")) {
			return "";
		}

		returnValue = returnValue.replaceAll("/", "");
		returnValue = returnValue.replaceAll("\\", "");
		returnValue = returnValue.replaceAll("\\.\\.", ""); // ..
		returnValue = returnValue.replaceAll("&", "");

		return returnValue;
	}

	public static String filePathWhiteList(String value) {
		return value; // TODO
	}

	 public static boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");

		return ipPattern.matcher(str).matches();
    }


	public static void jsonGridForm(int pageNo,int pageCount,int listCount, List<?> list,String msg,HttpServletResponse response) {
		JSONObject  jsonObj = new JSONObject();
		JSONObject  jsonPage = new JSONObject();
		try {
	    	response.setContentType( "text/json" );
	    	response.setCharacterEncoding("UTF-8");
	    	response.getOutputStream().write( jsonToGrid(pageNo,pageCount,listCount,list,msg).toString().getBytes("UTF-8"));

	    	for(int i=0; i<list.size(); i++){
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject jsonToGrid (int pageNo,int pageCount,int listCount, List<?> list,String msg) throws JSONException {
		JSONObject  jsonObj = new JSONObject();
		JSONObject  jsonPage = new JSONObject();

		jsonPage.put("pageNo", pageNo);
    	if(listCount != 0){


    		jsonPage.put("pageCount", ((listCount-1) / pageCount) + 1);
    	}else{
    		jsonPage.put("pageCount", 0);
    	}
    	jsonPage.put("listCount", listCount);

    	jsonObj.put("list", JSONArray.fromObject((Object) list)   );
    	jsonObj.put("page",jsonPage);

    	return jsonObj;
	}

	/*
	 * 레피던트 업로드 공통로직 처리
	 *
	 
	public static void rapidantUploadCall( ModelMap model, HttpServletRequest request ,String table){

		SessionVO session = (SessionVO) request.getSession().getAttribute("userSession");
        String ip = request.getRemoteAddr();
		String id = EgovStringUtil.nullConvert(request.getParameter("rsltRegistRceptId"));
		String fileOption = (EgovStringUtil.nullConvert(request.getParameter("fileType")).equals("raw")?" -r":"");
		String fileType = EgovStringUtil.nullConvert(request.getParameter("fileType"));
		String dataVal = EgovStringUtil.nullConvert(request.getParameter("dataVal"));
		String deleteFileId = EgovStringUtil.nullConvert(request.getParameter("deleteFileId"));
		String allowedExtensions = EgovStringUtil.nullConvert(request.getParameter("allowedExtensions"));


		String path = "/"+id+"/";

		if(dataVal.equals("Data Set")){
			allowedExtensions = "xlsx xls";
		}else{
			if(allowedExtensions.equals("")){
				allowedExtensions = "zip";
			}
		}
		System.out.println(allowedExtensions);

		System.out.println("clientIP");
		System.out.println("id");
		System.out.println("table");
		System.out.println("path");
		System.out.println("fileType");
		System.out.println("fileOption");
		System.out.println("userId");
		System.out.println("dataVal");
		System.out.println("deleteFileId");
		System.out.println("allowedExtensions");

		model.addAttribute("clientIP", ip);  						//클라이언트 아이피
		model.addAttribute("id", id);  								//성과물등록접수번호
		model.addAttribute("table", table);  						//데이터 구분 ( ngs , ge (DNA) , clinical.... )
		model.addAttribute("path", path);  							//저장될장소에 새롭게 생길 폴어 이
		model.addAttribute("fileType",fileType);  					//파일종류
		model.addAttribute("fileOption", fileOption);				//파일 옵션
		model.addAttribute("userId", session.getsUserId());			//사용자 아이디
		model.addAttribute("dataVal", dataVal);						//데이터 타입 ( fastq bam 등....)
		model.addAttribute("deleteFileId", deleteFileId);						//삭제할 파일아이디
		model.addAttribute("allowedExtensions", allowedExtensions);	//레피던트 허용 확장자
	}
	*/

	public static void responseAlertLoginScropt(HttpServletRequest request,HttpServletResponse response) throws IOException {

			response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("<script>alert('로그인 후 이용이 가능합니다.');location.href='"+request.getContextPath()+"/common/user/userPage.do'; </script>");
	    	out.flush();
	    	out.close();
	 }
}