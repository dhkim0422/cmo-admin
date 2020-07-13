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

import com.insilicogen.common.jfile.service.JFileDetails;

import lombok.Data;

@Data
public class JFileVO implements JFileDetails {

	private String fileId ;
	private int fileSeq ;
	private String delYn ;
	private String fileNm ;
	private long fileSize = -1;
	private String fileMask;
	private String dwldCnt ;
	private String dwldExpireDate ;
	private String dwldLimitCnt ;
	private String useSecurity ;
	private String uploadMode = "db";
	private String registId ;
	private String registDt ;
	private String filePath ;
	private String reprsntAt ;
	private String status ;
    private String[] fileIdArr;
    private int[] fileSeqArr;
    private String imageTy ;

    private int key;
    private String caption;
    private long size = -1;
    private String type;

	public void setKey(int key) {
		this.fileSeq = key;
		this.key = key;
	}

	public String getType() {
		return isImage() ? "image" : "other";
	}

    public boolean isImage() {
		return 	("bmp". equals(getExtension()) ||
			     "gif". equals(getExtension()) ||
		         "jpg". equals(getExtension()) ||
		         "jpeg".equals(getExtension()) ||
		         "png". equals(getExtension())
		) ;
	}

	public String getExtension() {
		if(fileNm == null)
			return null;
		return  fileNm.lastIndexOf(".") > -1 ? fileNm.substring(fileNm.lastIndexOf(".")+1) : null;
	}
}
