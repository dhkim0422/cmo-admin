package com.insilicogen.common.jfile.service.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.insilicogen.common.jfile.GlobalVariables;
import com.insilicogen.common.jfile.JProperties;
import com.insilicogen.common.jfile.exception.JFileException;
import com.insilicogen.common.jfile.security.service.CipherService;
import com.insilicogen.common.jfile.service.FileUploadCompletedEventListener;
import com.insilicogen.common.jfile.service.JFile;
import com.insilicogen.common.jfile.service.JFileDetails;
import com.insilicogen.common.jfile.service.strategy.JFileUploadTypeFactory;
import com.insilicogen.common.jfile.utils.KeyHelper;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *   2013.12.19	표준프레임워크	공통컴포넌트 추가 적용 (패키지 변경)
 * </pre>
 */
public abstract class JFileUploadModeTemplate {

	/** 로거 */
	protected Log logger = LogFactory.getLog(getClass());

	/** 프로퍼티 파일에 정의된 uploadpath 키 */
	private String uploadPathKey;

	/** 암호화 서비스 객체 */
	private CipherService cipherService;

	/** DB모드 */
	protected static final String DB_MODE = "db";

	/** 파일업로드 이벤트 리스너 */
	private Collection<FileUploadCompletedEventListener> fileUploadCompletedEventListeners;

	/**
	 * 파일아이디가 없다면 새로 생성하여 주고 있으면 있던 값을 반환한다.
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @return String 파일 아이디
	 */
	public String getFileId(String fileId) {
		return null == fileId || "".equals(fileId) ? KeyHelper.getStringKey() : fileId;
	}

	/**
	 * 마스킹 된 파일명, 파일 모드, 파일 경로를 전달 받아 파일의 전체경로를 반환한다.
	 * @param maskingName 마스킹 된 파일명.
	 * @param uploadMode 업로드 모드.
	 * @param uploadPath 업로드 패스.
	 * @return String 서버에 저장된 전체파일경로.
	 */
	public String getUploadFilePullPath(String maskingName, String uploadMode, String uploadPath) {
		return getUploadDirectoryPath(uploadPath, uploadMode).concat(maskingName);
	}

	/**
	 * 파일명이 포함된 업로드 경로, 업로드 모드를 전달받아 디렉터리 경로를반환한다.
	 * @param uploadPath 업로드 경로
	 * @param uploadMode 업로드모드.
	 * @return String 디렉터리 경로.
	 */
	public String getUploadDirectoryPath(String uploadPath, String uploadMode) {
		StringBuilder uploadPathSb = new StringBuilder();
		uploadPathSb.append(getFileUploadDirectoryPath(uploadMode, uploadPath));
		mkdir(uploadPathSb.toString());
		return uploadPathSb.toString();
	}

	public abstract Object getFileUploadDirectoryPath(String uploadMode, String uploadPath);

	public abstract String getFileMask(String originalFilename, int i, String uploadMode, String string);

	public abstract String getFileDownloadDirectoryPath(String maskingFileNm, String uploadMode, String systemUploadPath);

	public abstract String getFileDownloadPullPath(String fileMask, String dbMode,	String string);

	public JFile getJFile(JFileDetails fileVo, String useSecurity, String uploadPath) {
		if(fileVo == null)
			throw new JFileException(" 첨부파일 이력 정보가 존재하지 않습니다. ");

		if(logger.isDebugEnabled()) {
			logger.debug("Server Repository Path : "+getFileDownloadPullPath(fileVo.getFileMask(), fileVo.getUploadMode(), uploadPath));
		}

		JFile jfile = new JFile(getFileDownloadPullPath(fileVo.getFileMask(), fileVo.getUploadMode(), uploadPath));
		jfile.setOriginalFileNm(fileVo.getFileNm());
//		jfile.setUseSecurity(fileVo.getUseSecurity());
		jfile.setUseSecurity(useSecurity);
		return jfile;
	}

	public JFile[] getFiles(List<JFileDetails> files, String uploadPath, String useSecurity, String uploadMode) {
		if(files == null)
			return null;

		JFile[] jfiles = new JFile[files.size()];
		int idx = 0;

		for(JFileDetails file : files) {
			if(file == null)
				throw new JFileException(" FileUpload 이력 정보가 존재하지 않습니다. ");


			if(logger.isDebugEnabled()) {
				logger.debug("Server Repository Path : "+getFileDownloadPullPath(file.getFileMask(), uploadMode, uploadPath == null ? 
						JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY) : uploadPath));
			}

			JFile jfile = new JFile(getFileDownloadPullPath(file.getFileMask(), uploadMode, uploadPath == null ?
					JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY) :
					uploadPath));
			jfile.setOriginalFileNm(file.getFileNm());
			jfile.setUseSecurity(useSecurity);
			jfiles[idx] = jfile;
			idx++;
		}
		return jfiles;
	}

	public void deleteJFiles(JFileDetails details, String uploadPath, String uploadMode) {
		if(details == null)
			return;

		/** 파일을 삭제한다. */
		deleteFileByFullPath(getFileUploadDirectoryPath(uploadMode, uploadPath)+details.getFileMask());
	}

	/**
	 * 파일업로드 이벤트 리스너를 반환한다.
	 * @return FileUploadEventListener 파일업로드 이벤트 리스너.
	 */
	public Collection<FileUploadCompletedEventListener> getFileUploadCompletedEventListener() {
		return fileUploadCompletedEventListeners;
	}

	/**
	 * 파일업로드 이벤트 리스너를 세팅한다.
	 * @param fileUploadEventListener 파일업로드 이벤트 리스너.
	 */
	public void setFileUploadCompletedEventListener(
			FileUploadCompletedEventListener fileUploadListener) {
		if( fileUploadCompletedEventListeners==null ) {
			fileUploadCompletedEventListeners = new ArrayList<FileUploadCompletedEventListener>();
		}
		fileUploadCompletedEventListeners.add(fileUploadListener);
	}

	public void upload(MultipartFile multipartFile, JFileDetails fileVo) {
		fileVo.setFileMask(getFileMask(multipartFile.getOriginalFilename(), 0, fileVo.getUploadMode(), JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY)));
		fileVo.setFileNm(multipartFile.getOriginalFilename());
		fileVo.setFileSize(multipartFile.getSize());
		String uploadDirectoryPath = getUploadDirectoryPath(JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), fileVo.getUploadMode());
		String uploadPullPath = getUploadFilePullPath(fileVo.getFileMask(), fileVo.getUploadMode(),
				(getUploadPathKey() != null && !"".equals(getUploadPathKey())) ? JProperties.getString(getUploadPathKey()) : JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY));
		try {
			JFileUploadTypeFactory.INSTANCE.getUploadType(fileVo.getUseSecurity()).getHandler().handle(multipartFile.getInputStream(), new FileOutputStream(uploadPullPath));
			if(getFileUploadCompletedEventListener() != null) {
				for(FileUploadCompletedEventListener listener : getFileUploadCompletedEventListener()) {
					listener.uploadCompleted(fileVo.getFileId(), uploadDirectoryPath, fileVo.getFileMask(), multipartFile.getOriginalFilename());
				}
			}
		}catch(IOException e) {
			throw new JFileException(e);
		}catch(Exception e) {
			throw new JFileException(e);
		}
		if(logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n================Upload Completed====================\n");
			sb.append("UPLOAD PATH : "+uploadDirectoryPath+"\n");
			sb.append("UPLOAD File : "+multipartFile.getOriginalFilename()+"\n");
			logger.debug(sb.toString());
		}
	}

	/**
	 * 프로퍼티 파일에 정의된 uploadpath 키를 반환
	 * @return String 프로퍼티 파일에 정의된 uploadpath 키
	 */
	public String getUploadPathKey() {
		return uploadPathKey;
	}

	/**
	 * 프로퍼티 파일에 정의된 uploadpath 키를 세팅
	 * @param uploadPathKey 프로퍼티 파일에 정의된 uploadpath 키
	 */
	public void setUploadPathKey(String uploadPathKey) {
		this.uploadPathKey = uploadPathKey;
	}

	/**
	 * 입력받은 디렉터리가 존재 하지 않았다면 경로를 생성 한후 파일을 생성한다.
	 * @param file 파일 객체.
	 */
	protected void mkdir(File file) {
		if (!file.exists())
			file.mkdirs();
	}

	/**
	 * 입력받은 디렉터리가 존재 하지 않았다면 경로를 생성 한후 파일을 생성한다.
	 * @param fullpath 파일 경로.
	 */
	protected void mkdir(String fullpath) {
		File file = new File(fullpath);
		if (!file.exists())
			file.mkdirs();
	}

	/**
	 * 파일경로에 파일이 존재하면 삭제한다.
	 * @param fullPath 파일경로.
	 */
	public void deleteFileByFullPath(String fullPath) {
		File f = null;
		try {
			f = new File(fullPath);
			if (f.exists())
				f.delete();
		} catch (Exception e) {
			logger.error("File Delete error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 암호화 서비스 객체를 반환한다.
	 * @return CipherService 암호화 서비스 객체.
	 */
	public CipherService getCipherService() {
		return cipherService;
	}

	/**
	 * 암호화 서비스 객체를 반환한다.
	 * @param cipherService 암호화 서비스 객체.
	 */
	public void setCipherService(CipherService cipherService) {
		this.cipherService = cipherService;
	}
}
