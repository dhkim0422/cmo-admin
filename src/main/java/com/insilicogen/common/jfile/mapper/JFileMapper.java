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
package com.insilicogen.common.jfile.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.insilicogen.common.jfile.service.JFile;
import com.insilicogen.common.jfile.service.JFileDetails;
import com.insilicogen.common.jfile.service.JFileVO;

public interface JFileMapper {

	public static final String DB_MODE = "db";

	/**
	 * 파일 아이디를 반환
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @return String 파일 아이디
	 */
	public String getFileId(String fileId, Object fileSeq);

	/**
	 * 첨부파일 정보를 저장한다.
	 * @param fileId 파일 아이디.
	 * @param fileNm 파일 명.
	 * @param fileSize 파일 사이즈.
	 * @param maskingFileNm 마스킹 파일명.
	 * @param expireDate 폐기 일자.
	 * @param limitCount 첨부파일 제한 갯수.
	 * @return
	 */
	public int addAttachFile(JFileDetails fileVo);

	/**
	 * 파일아이디로 첨부파일 정보를 조회한다.
	 * @param fileId 파일 아이디.
	 * @return List<Map<String, Object>> 첨부파일 목록 정보.
	 */
	public List<JFileDetails> getAttachFiles(String fileId) ;

	/**
	 * 파일아이디와 파일 시퀀스로 첨부 파일 정보를 조회한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeq 파일 시퀀스.
	 * @return Map<String, Object> 첨부파일 정보.
	 */
	public JFileDetails getAttachFile(String fileId, int fileSeq);

	/**
	 * 파일 아이디로 파일 시퀀스 목록을 조회한다.
	 * @param fileId 파일아이디.
	 * @return Object[] 파일 시퀀스 목록.
	 */
	public Object[] getAttacheFileSeqs(String fileId);

	/**
	 * 파일 아이디와 파일 시퀀스 목록으로 첨부파일 목록이 존재하는 지 여부를 조회한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeqs 파일 시퀀스 목록.
	 * @return isExistingAttachFileInfo 파일목록 존재 여부.
	 */
	public boolean isExistingAttachFileInfo(String fileId, List<Object> arrayToList);

	/**
	 * 파일아이디로 삭제여부 컬럼을 변경한다.
	 * @param fileId 파일 아이디.
	 * @param delYn 삭제 여부.
	 */
	public void updateAttachFileDeleteYnByFileId(String fileId, String delYn);

	/**
	 * 파일 아이디로 파일 다운로드시 다운로드 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 */
	public void updateAttachFileDwldCnt(String fileId);

	/**
	 * 파일 아이디로 파일 다운로드시 다운로드 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 */
	public void updateAttachFileDwldCnt(String fileId, int fileSeq);

	/**
	 * 파일아이디로 삭제여부 컬럼을 변경한다.
	 * @param fileId 파일 아이디.
	 * @param delYn 삭제 여부.
	 */
	public void updateAttachFileDeleteYn(String fileId, Object[] fileSeqs, String yn);

	/**
	 * 파일 아이디와 파일 시퀀스로 다운로드한 건수를 변경한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeq 파일 시퀀스.
	 */
	public void updateAttachFileDwldCntBySequence(JFileVO filevo);

	/**
	 * 파일아이디에 대한 대표이미지를 초기화 한다.
	 * @param fileId 파일 아이디.
	 *
	 */
	public void updateAttachFileReprsntAllN(JFileVO jfileVO);

	/**
	 * 파일아이디와 파일 시퀀스를 기준으로 대표이미지 설정을 업데이트 한다.
	 * @param fileId 파일 아이디.
	 *
	 */
	public void updateAttachFileReprsntY(JFileVO jfileVO);




	/**
	 * 파일아이디로 다운로드 한 건수를 변경한다.
	 * @param fileId 파일아이디.
	 */
	public void updateAttachFileDwldCntByFileId(String fileId);

	/**
	 * 파일 아이디와 파일 시퀀스로 첨부파일 정보를 삭제한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeqs 파일 시퀀스 목록.
	 */
	public void removeAttachFile(String fileId, List<Object> arrayToList);
	public void removeAttachFile(String fileId, int fileSeq);
	public void removeAttachFile(String fileId);
	public void removeAttachFileInfo(JFileVO fileVO );
	/**
	 * 업로드를 수행한다.
	 * @param values 멀티 파일
	 * @param fileVo 파일 정보를 담고 객체
	 * @return
	 */
	public JFileVO upload(Collection<MultipartFile> values, JFileVO fileVo);

	/**
	 * 업로드 완료 후 처리 작업을 수행한다.
	 * @param fileId 파일 아이디
	 */
	public void executeAfterUploadCompleted(String fileId);

	/**
	 * 파일 아이디와 파일 시퀀스 암호화 사용여부를 검색 조건으로 파일을 찾는다.
	 * @param fileId 파일 아이디
	 * @param fileSeq 파일 시퀀스
	 * @param useSecurity 암호화 모드
	 * @return File 파일 객체
	 */
	public JFile getFile(String fileId, int fileSeq, String useSecurity);

	/**
	 * 파일 아이디와 암호화 여부를 입력 받아 파일들을 찾는다.
	 * @param fileId 파일 아이디
	 * @param useSecurity 암호화 사용여부
	 * @param string
	 * @return JFile[] 파일 객체
	 */
	public JFile[] getFiles(String fileId,int fileSeq, String useSecurity );

	/**
	 * 파일아이디와 파일 시퀀스 암호화 여부를 검색 조건으로 파일을 찾는다.
	 * insilicogen
	 *
	 * @param fileId 파일아이디
	 * @param fileSeq 파일 시퀀스
	 * @param useSecurity 암호화 여부
	 * @return File 파일 객체
	 */
	public JFile getFileBySequence(String fileId, int fileSeq, String useSecurity);


	public JFile[] getFilesArr(String[] fileIdArr, String[] fileSeqArr, String useSecurity);

	/**
	 * 파일아이디와 파일 시퀀스로 첨부 파일 정보를 조회한다.
	 * @param fileId 파일 아이디.
	 * @param fileSeq 파일 시퀀스.
	 * @return JFileVO 첨부파일 정보.
	 */

	@SuppressWarnings({ "serial" })
	public JFileVO selectAttachFile( JFileVO filevo) ;
	public Object[] getAttachFileSeqs(String fileId);
	public List<JFileDetails> selectAttachFiles( String fileId,  int fileSeq);
	public List<JFileDetails> selectAttachFiles( JFileVO filevo) ;
	/*public List<JFileDetails> selectAttachFiles( String[] fileIdArr,  String[] fileSeqArr);*/
}
