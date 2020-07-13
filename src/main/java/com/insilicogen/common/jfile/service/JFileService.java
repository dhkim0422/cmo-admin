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
package com.insilicogen.common.jfile.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.insilicogen.common.jfile.GlobalVariables;
import com.insilicogen.common.jfile.JProperties;
import com.insilicogen.common.jfile.mapper.JFileMapper;
import com.insilicogen.common.jfile.service.template.JFileUploadModeFactory;
import com.insilicogen.common.jfile.service.template.JFileUploadModeTemplate;
@Service
public class JFileService {

	/** 로거 */
	protected Log logger = LogFactory.getLog(getClass());

	@Autowired
	private  JFileMapper dao;

	private Object EMPTY_OBJECT = "";

	public String getFileId(String fileId, Object fileSeq) {
		initializeAttachFileStatus(fileId, fileSeq);
		return JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler().getFileId(fileId);
	}

	private void initializeAttachFileStatus(String fileId, Object fileSeq) {
		if(StringUtils.hasText(fileId))
			updateAttachFileDeleteYnByFileId(fileId, "N");

		if(fileSeq != null && !EMPTY_OBJECT.equals(fileSeq))
			updateAttachFileDeleteYn(fileId, fileSeq.getClass().isArray() ? (Object[])fileSeq : new Object[]{fileSeq}, "Y");
	}
	//파일 등록
	public int addAttachFile(JFileDetails fileVo) {
		int aa = dao.addAttachFile(fileVo);
		System.out.println(">>>>>>>>> : " + aa);
		return aa;
	}

	//삭제 한다고 표시
	public void updateAttachFileDeleteYn(String fileId, Object[] fileSeqs, String yn) {
		dao.updateAttachFileDeleteYn(fileId, fileSeqs, yn);
	}
	// 전체 삭제
	public void updateAttachFileDeleteYnByFileId(String fileId, String delYn) {
		dao.updateAttachFileDeleteYnByFileId(fileId, delYn);
	}
	//다운로드 카운트
	public void updateAttachFileDwldCnt(String fileId) {
		dao.updateAttachFileDwldCnt(fileId);
	}
	//다운로드 카운트
	public void updateAttachFileDwldCnt(String fileId, int fileSeq) {
		dao.updateAttachFileDwldCnt(fileId, fileSeq);
	}
	//다운로드 카운트
	public void updateAttachFileDwldCntBySequence(String fileId, int fileSeq) {
		JFileVO filevo = new JFileVO();
		filevo.setFileId(fileId);
		filevo.setFileSeq(fileSeq);
		dao.updateAttachFileDwldCntBySequence(filevo);
	}

	public void updateAttachFileDwldCntByFileId(String fileId) {
		dao.updateAttachFileDwldCntByFileId(fileId);
	}
	//파일 삭제
	public void removeAttachFile(String fileId, List<Object> fileSeqs) {
		dao.removeAttachFile(fileId, fileSeqs);
	}

	public void removeAttachFile(JFileVO fileVO) {
		if(fileVO.getFileSeq() != 0) {
			JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
			JFileDetails fileInfo = getAttachFile(fileVO.getFileId(),fileVO.getFileSeq());
			upload.deleteJFiles(fileInfo, JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), JFileMapper.DB_MODE);
			dao.removeAttachFileInfo(fileVO);
		}else{
			executeAfterUploadCompleted(fileVO.getFileId());
		}
	}

	public void removeAttachFile(String fileId) {
			String[] fileDelete = fileId.split(",");
			for (int i = 0; i < fileDelete.length; i++) {
				executeAfterUploadCompleted(fileDelete[i]);
			}
	}
	public void removeAllAttachFile(String fileId, int fileSeq) {
		if(fileSeq != 0) {
			JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
			JFileDetails fileInfo = getAttachFile(fileId,fileSeq);
			upload.deleteJFiles(fileInfo, JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), JFileMapper.DB_MODE);
			dao.removeAttachFile(fileId, fileSeq);
		}else{
			executeAfterUploadCompleted(fileId);
		}
	}

	public boolean isExistingAttachFileInfo(String fileId, List<Object> fileSeqs) {
		if(fileId == null || fileSeqs == null)
			return false;
		return dao.isExistingAttachFileInfo(fileId, fileSeqs);
	}

	public JFileVO getAttachFile(String fileId, int fileSeq) {
		JFileVO filevo = new JFileVO();
		filevo.setFileId(fileId);
		filevo.setFileSeq(fileSeq);
		return dao.selectAttachFile(filevo);
	}
	public Object[] getAttacheFileSeqs(String fileId) {
		return dao.getAttachFileSeqs(fileId);
	}

	public List<JFileDetails> getAttachFiles(String fileId) {
		JFileVO filevo = new JFileVO();
		filevo.setFileId(fileId);
		return dao.selectAttachFiles(filevo);
	}

	public List<JFileDetails> getAttachFiles(String fileId, int fileSeq) {
		return dao.selectAttachFiles(fileId, fileSeq);
	}

	public List<JFileDetails> getAttachFilesArr(String fileIdArr[], int fileSeqArr[]) {
		JFileVO filevo = new JFileVO();
		filevo.setFileIdArr(fileIdArr);
		filevo.setFileSeqArr(fileSeqArr);
		return dao.selectAttachFiles(filevo);
	}





	public List<JFileVO> upload(Collection<List<MultipartFile>> multipartFiles, JFileVO fileVo) {
		if(multipartFiles == null){
			return null;
		}else{
			List<JFileVO> list = new ArrayList<JFileVO>();
			JFileVO vo = new JFileVO();
			for (List<MultipartFile> fileList : multipartFiles) {
				for (final MultipartFile file : fileList) {
					vo.setFileMask(fileVo.getFileMask());
					vo.setFileId(fileVo.getFileId());
					vo.setFileSeq( upload(file, fileVo));
					list.add(list.size(), vo);
				}
	        }

			return list;
		}
	}

	private int upload(MultipartFile multipartFile, JFileVO fileVo) {
		JFileUploadModeFactory.INSTANCE.getUploadType(fileVo.getUploadMode()).getHandler().upload(multipartFile, fileVo);
		if(JFileMapper.DB_MODE.equalsIgnoreCase(fileVo.getUploadMode())) {
			return addAttachFile(fileVo);
		}else{
			return 0;
		}
	}

	public void executeAfterUploadCompleted(String fileId) {
		Object[] fileSeqs = getAttacheFileSeqs(fileId);
		if(fileSeqs == null || fileSeqs.length == 0)
			return;

		/** 파일을 삭제한다. */
		if(fileSeqs != null) {
			JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
			for(Object fileSeq : fileSeqs) {
				JFileDetails fileInfo = getAttachFile(fileId, (int)fileSeq);
				upload.deleteJFiles(fileInfo, JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), JFileMapper.DB_MODE);
			}
		}
		/** 파일업로드 이력테이블(T_CODA_ATTCH_FILE) 에서 삭제할 파일 목록을 삭제한다. */
		@SuppressWarnings("unchecked")
		List<Object> list = CollectionUtils.arrayToList(fileSeqs);
		dao.removeAttachFile(fileId);
	}

	public JFile getFile(String fileId, int fileSeq, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
		return upload.getJFile(getAttachFile(fileId, fileSeq), useSecurity, JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY));
	}

	public JFile[] getFiles(String fileId, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
		return upload.getFiles(getAttachFiles(fileId), JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), useSecurity, JFileMapper.DB_MODE);
	}

	public JFile getFileBySequence(String fileId, int fileSeq, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
		return upload.getJFile(getAttachFile(fileId, fileSeq), useSecurity, JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY));
	}

	public JFile[] getFiles(String fileId, int fileSeq, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
		return upload.getFiles(getAttachFiles(fileId, fileSeq), JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY), useSecurity, JFileMapper.DB_MODE);


	}

	public JFile[] getFilesArr(String[] fileIdArr, int[] fileSeqArr, String useSecurity) {
		JFileUploadModeTemplate upload = JFileUploadModeFactory.INSTANCE.getUploadType(JFileMapper.DB_MODE).getHandler();
		return upload.getFiles(getAttachFilesArr(fileIdArr, fileSeqArr), JProperties.getString(GlobalVariables.DEFAULT_FILE_UPLOAD_PATH_KEY),
				useSecurity, JFileMapper.DB_MODE);


	}

	public void saveReprsnt(JFileVO jfileVO) {
		dao.updateAttachFileReprsntAllN(jfileVO);
		dao.updateAttachFileReprsntY(jfileVO);
	}


}
