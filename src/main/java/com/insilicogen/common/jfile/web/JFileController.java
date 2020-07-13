/*
 * eGovFrame JFile
 * Copyright The eGovFrame Open Community (http://open.egovframe.go.kr)).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author 정호열 커미터(표준프레임워크 오픈커뮤니티 리더)
 */
package com.insilicogen.common.jfile.web;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.jfile.GlobalVariables;
import com.insilicogen.common.jfile.JProperties;
import com.insilicogen.common.jfile.service.JFile;
import com.insilicogen.common.jfile.service.JFileDetails;
import com.insilicogen.common.jfile.service.JFileService;
import com.insilicogen.common.jfile.service.JFileVO;
import com.insilicogen.common.jfile.session.SessionUploadChecker;
import com.insilicogen.common.jfile.utils.DateUtils;
import com.insilicogen.common.jfile.view.JSonView;
import com.insilicogen.common.jfile.view.JfileDownloadView;
import com.insilicogen.common.util.EgovStringUtil;
import com.insilicogen.common.util.EgovWebUtil;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.util.SessionUtils;

@Controller
public class JFileController {

	private Log log = LogFactory.getLog(getClass());

	@Resource(name = "filterMultipartResolver")
	private MultipartResolver multipartResolver;

	@Autowired
	private JFileService service;

	/**
	 * 파일 ID를 읽어온다.
	 *
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readFileId.do")
	public ModelAndView readFileId(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		try {

			String[] fileSeq = request.getParameterValues("fileSeq");
			String fileId;
			System.out.println("fileSeq : " + fileSeq);
			if (fileSeq != null) {
				fileId = service.getFileId(request.getParameterValues("fileId")[0], fileSeq);
			} else {
				fileId = service.getFileId(request.getParameterValues("fileId")[0],
						request.getParameterValues("fileSeq[]"));
			}

			modelAndView.addObject("fileId", fileId);
			SessionUploadChecker.check(request, fileId);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("fileId", "errorFileId");
		}
		return modelAndView;
	}

	@RequestMapping("/jfile/uploadingCheck.do")
	public ModelAndView uploadingCheck(@ModelAttribute JFileVO fileVO, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		modelAndView.addObject("hasFileId", SessionUploadChecker.isContainsKey(request, fileVO.getFileId()));
		modelAndView.addObject("maxInactiveInterval", request.getSession().getMaxInactiveInterval());
		log.debug("\nlastAccessTime : "
				+ DateUtils.getDateString(request.getSession().getLastAccessedTime(), "yyyy.MM.dd HH:mm:ss"));
		return modelAndView;
	}

	/**
	 * 파일 업로드 실행
	 *
	 * @param fileVo
	 * @param request
	 * @return
	 */
	@RequestMapping("/jfile/processUpload.do")
	public ModelAndView processUpload(@ModelAttribute JFileVO fileVo, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		if (multipartResolver.isMultipart(request)) {
			final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			Collection<List<MultipartFile>> values = multiRequest.getMultiFileMap().values();
			boolean exceedFileNmLength = isExceedFileNmLength(values);
			if (exceedFileNmLength) {
				try {
					response.sendError(GlobalVariables.Error.FILE_LENGTH_ERROR, "");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					fileVo.setRegistId(SessionUtils.getLoginId(request));
					service.upload(values, fileVo);
				} finally {
					SessionUploadChecker.unCheck(request, fileVo.getFileId());
				}
			}

		}

		return modelAndView;
	}

	private boolean isExceedFileNmLength(Collection<List<MultipartFile>> values) {

		if (values != null) {
			for (List<MultipartFile> list : values) {
				for (MultipartFile mfile : list) {
					if (mfile.getOriginalFilename().length() > 99) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 파일업로드가 완료된 후 처리 작업을 수행한다.
	 *
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/afterProcessUploadCompleted.do")
	public ModelAndView afterProcessUploadCompleted(JFileVO fileVO, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);
		try {
			service.executeAfterUploadCompleted(fileVO.getFileId());
			SessionUploadChecker.unCheck(request, fileVO.getFileId());
		} catch (Exception e) {
			try {
				response.sendError(GlobalVariables.Error.SYSTEM_ERROR, "");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return modelAndView;
	}

	/**
	 * 파일 아이디로 첨부파일 목록을 읽어온다.
	 *
	 * @param fileVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jfile/readFiles.do")
	public ResultData<List<JFileDetails>> readFiles(JFileVO fileVO) {
		List<JFileDetails> fileList = service.getAttachFiles(fileVO.getFileId());
		return ResponseUtil.response(fileList);
	}

	@RequestMapping(value = "/jfile/readGridFiles.do")
	public void readGridFiles(JFileVO fileVO, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<JFileDetails> fileList = service.getAttachFiles(fileVO.getFileId());

		EgovWebUtil.jsonGridForm(0, 999999, fileList.size(), fileList, "", response);
	}

	/**
	 * 파일 아이디로 첨부파일 목록을 읽어온다.
	 *
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readDownloadFiles.do")
	public ModelAndView readDownloadFiles(JFileVO fileVO) {
		ModelAndView modelAndView = new ModelAndView(JSonView.NAME);

		modelAndView.addObject("fileList", service.getAttachFiles(fileVO.getFileId()));
		return modelAndView;
	}

	/**
	 * 파일을 다운로드 받는다.
	 *
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/readDownloadFile.do")
	public ModelAndView readDownloadFile(JFileVO fileVO) {
		JFile downloadFile = service.getFile(fileVO.getFileId(), fileVO.getFileSeq(), fileVO.getUseSecurity());
		// 다운로드 카운트를 증가 시킨다.
		service.updateAttachFileDwldCntBySequence(fileVO.getFileId(), fileVO.getFileSeq());
		return new ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME, downloadFile);
	}

	/**
	 * 멀티 업로드된 모든 파일을 zip로 압축하여 다운로드 받는다.
	 *
	 * @param fileVO
	 * @return
	 */
	/*
	 * @RequestMapping("/jfile/downloadAll.do") public ModelAndView
	 * downloadAll(@ModelAttribute("searchVO") JFileVO fileVO, HttpServletRequest
	 * request, IssueVO searchVO) {
	 *
	 * String[] fileIdArr = null; String[] fileSeqArr = null; for(int i = 0; i <
	 * searchVO.getFileSeqArr().length; i++){ fileIdArr =
	 * searchVO.getFileIdArr()[i].split(","); fileSeqArr =
	 * searchVO.getFileSeqArr()[i].split(","); } for(int i=0; i< fileIdArr.length;
	 * i++){ System.out.println(fileIdArr[i]); System.out.println(fileSeqArr[i]);
	 *
	 * }
	 *
	 * JFile[] downloadZipFile = service.getFilesArr(fileIdArr,fileSeqArr,
	 * fileVO.getUseSecurity()); System.out.println();
	 * //service.updateAttachFileDwldCntByFileId(fileVO.getFileId()); return new
	 * ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME,
	 * downloadZipFile); }
	 */
	// 원본
	@ResponseBody
	@RequestMapping("/jfile/downloadAllArr.do")
	public ResultData<JFile[]> downloadAllArr(JFileVO fileVO) {
		JFile[] downloadZipFile = service.getFiles(fileVO.getFileId(), fileVO.getUseSecurity());
		//service.updateAttachFileDwldCntByFileId(fileVO.getFileId());
		return ResponseUtil.response(downloadZipFile);
	}

	/**
	 * 이미지 파일일 경우 미리보기를 한다.
	 * @param fileVO
	 * @return
	 */
	@RequestMapping("/jfile/preview.do")
	public ModelAndView preview(JFileVO fileVO, HttpServletRequest request, HttpServletResponse response) {
		JFile previewFile;
		JFileDetails prevFileVO = service.getAttachFile(fileVO.getFileId(), fileVO.getFileSeq());
		if(prevFileVO != null) {
			previewFile = service.getFileBySequence(fileVO.getFileId(), fileVO.getFileSeq(), fileVO.getUseSecurity());
		}else {
			previewFile = new JFile(getNoImagePath(EgovStringUtil.nullConvert(fileVO.getImageTy())));
		}
		return new ModelAndView(JfileDownloadView.NAME, JfileDownloadView.MODELNAME, previewFile);
	}

	/**
	 * 이미지 경로를 읽어온다.
	 *
	 * @return
	 */
	private String getNoImagePath(String imageTy) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		if(EgovStringUtil.nullConvert(imageTy).equals("main")) {
			return request.getSession().getServletContext().getRealPath("/")+	JProperties.getString(GlobalVariables.DEFAULT_MAIN_IMAGE_APP_PATH_KEY);
		}else {
			return request.getSession().getServletContext().getRealPath("/")+	JProperties.getString(GlobalVariables.DEFAULT_NO_IMAGE_APP_PATH_KEY);
		}
	}

	@RequestMapping(value = "/jfile/upload.do", method = RequestMethod.POST)
	public void handleFileUpload(@ModelAttribute JFileVO fileVo, HttpServletRequest request, final MultipartHttpServletRequest multiRequest,
			HttpServletResponse response) throws IOException {
		if (multipartResolver.isMultipart(request)) {
			//final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Collection<List<MultipartFile>> values = multiRequest.getMultiFileMap().values();
			boolean exceedFileNmLength = isExceedFileNmLength(values);

			if (exceedFileNmLength) {
				try {
					response.sendError(GlobalVariables.Error.FILE_LENGTH_ERROR, "");
				} catch (IOException e) {
					// e.printStackTrace();
					response.sendError(GlobalVariables.Error.SYSTEM_ERROR);
					throw new IOException("업로드 중 오류가 발생했습니다.");
				}
			} else {

				if (!SessionUtils.isLogin(request)) {
					response.sendError(GlobalVariables.Error.SYSTEM_ERROR, "로그인 후 업로드가 가능합니다.");
				}

				try {
					fileVo.setFileId(fileVo.getFileId());
					fileVo.setRegistId(SessionUtils.getLoginId(request));

					List<JFileVO> list = service.upload(values, fileVo);
					Gson gson = new GsonBuilder().create();
					response.setContentType("text/json; charset=UTF-8");
					response.getOutputStream().write(gson.toJson(list).getBytes());
				} catch (IOException e) {
					// e.printStackTrace();
					throw new IOException("업로드 중 오류가 발생했습니다.");

				} finally {
					SessionUploadChecker.unCheck(request, fileVo.getFileId());
				}
			}

		}
	}

	/**
	 * 파일을 삭제한다.
	 *
	 * @param fileVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jfile/deleteFile.do")
	public JFileVO deleteFile(JFileVO fileVO, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		service.removeAttachFile(fileVO);
		return fileVO;
	}

	/*
	 * 대표 이미지 선택 팝업
	 *
	 */
	@RequestMapping(value = "/jfile/selectFileImageSelectView.do")
	public String selectFileImageSelectView(
			@ModelAttribute("searchVO") JFileVO searchVO,
			BindingResult bindingResult, ModelMap model, SessionStatus status,
			HttpServletRequest request) throws Exception {

		model.addAttribute("searchVO", searchVO);
		return "/adminPopupLayout/common/include/file/fileImageSelectView";
	}

	/*
	 * 대표 이미지 선택 저장
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/jfile/saveReprsnt.do")
	public JFileVO saveReprsnt(
			@ModelAttribute("searchVO") JFileVO searchVO,
			BindingResult bindingResult, ModelMap model, SessionStatus status,
			HttpServletRequest request) throws Exception {

		service.saveReprsnt(searchVO);

		searchVO.setStatus("UPDATE_OK");
		return searchVO;
	}


}
