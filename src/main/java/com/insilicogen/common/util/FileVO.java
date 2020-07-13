package com.insilicogen.common.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

//@SuppressWarnings("serial")
public class FileVO {

    private String[] fileIdArr;
    private String[] fileSeqArr;



    public String[] getFileIdArr() {
		return fileIdArr;
	}

	public void setFileIdArr(String[] fileIdArr) {
		this.fileIdArr = fileIdArr;
	}

	public String[] getFileSeqArr() {
		return fileSeqArr;
	}

	public void setFileSeqArr(String[] fileSeqArr) {
		this.fileSeqArr = fileSeqArr;
	}

	/**
     * 첨부파일 아이디
     */
	private String atchFileId = "";
    /**
     * 생성일자
     */
    private String creatDt = "";
    /**
     * 파일내용
     */
    private String fileCn = "";
    /**
     * 파일확장자
     */
    private String fileExtsn = "";
    /**
     * 파일크기
     */
    private String fileSize = "";
    /**
     * 파일연번
     */
    private String fileSn = "";
    /**
     * 파일저장경로
     */
    private String fileStreCours = "";
    /**
     * 원파일명
     */
    private String orignlFileNm = "";
    /**
     * 저장파일명
     */
    private String streFileNm = "";

    private String fileContentType;

    private String fileSaveDirectory;

    /**
     * atchFileId attribute를 리턴한다.
     *
     * @return the atchFileId
     */
    public String getAtchFileId() {
	return atchFileId;
    }

    /**
     * atchFileId attribute 값을 설정한다.
     *
     * @param atchFileId
     *            the atchFileId to set
     */
    public void setAtchFileId(String atchFileId) {
	this.atchFileId = atchFileId;
    }

    /**
     * creatDt attribute를 리턴한다.
     *
     * @return the creatDt
     */
    public String getCreatDt() {
	return creatDt;
    }

    /**
     * creatDt attribute 값을 설정한다.
     *
     * @param creatDt
     *            the creatDt to set
     */
    public void setCreatDt(String creatDt) {
	this.creatDt = creatDt;
    }

    /**
     * fileCn attribute를 리턴한다.
     *
     * @return the fileCn
     */
    public String getFileCn() {
	return fileCn;
    }

    /**
     * fileCn attribute 값을 설정한다.
     *
     * @param fileCn
     *            the fileCn to set
     */
    public void setFileCn(String fileCn) {
	this.fileCn = fileCn;
    }

    /**
     * fileExtsn attribute를 리턴한다.
     *
     * @return the fileExtsn
     */
    public String getFileExtsn() {
	return fileExtsn;
    }

    /**
     * fileExtsn attribute 값을 설정한다.
     *
     * @param fileExtsn
     *            the fileExtsn to set
     */
    public void setFileExtsn(String fileExtsn) {
	this.fileExtsn = fileExtsn;
    }

    public String getFileSize() {
	return fileSize;
    }

    public void setFileSize(String fileSize) {
	this.fileSize = fileSize;
    }

    /**
     * fileSn attribute를 리턴한다.
     *
     * @return the fileSn
     */
    public String getFileSn() {
	return fileSn;
    }

    /**
     * fileSn attribute 값을 설정한다.
     *
     * @param fileSn
     *            the fileSn to set
     */
    public void setFileSn(String fileSn) {
	this.fileSn = fileSn;
    }

    /**
     * fileStreCours attribute를 리턴한다.
     *
     * @return the fileStreCours
     */
    public String getFileStreCours() {
	return fileStreCours;
    }

    /**
     * fileStreCours attribute 값을 설정한다.
     *
     * @param fileStreCours
     *            the fileStreCours to set
     */
    public void setFileStreCours(String fileStreCours) {
	this.fileStreCours = fileStreCours;
    }

    /**
     * orignlFileNm attribute를 리턴한다.
     *
     * @return the orignlFileNm
     */
    public String getOrignlFileNm() {
	return orignlFileNm;
    }

    /**
     * orignlFileNm attribute 값을 설정한다.
     *
     * @param orignlFileNm
     *            the orignlFileNm to set
     */
    public void setOrignlFileNm(String orignlFileNm) {
	this.orignlFileNm = orignlFileNm;
    }

    /**
     * streFileNm attribute를 리턴한다.
     *
     * @return the streFileNm
     */
    public String getStreFileNm() {
	return streFileNm;
    }

    /**
     * streFileNm attribute 값을 설정한다.
     *
     * @param streFileNm
     *            the streFileNm to set
     */
    public void setStreFileNm(String streFileNm) {
	this.streFileNm = streFileNm;
    }

    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileSaveDirectory() {
		return fileSaveDirectory;
	}

	public void setFileSaveDirectory(String fileSaveDirectory) {
		this.fileSaveDirectory = fileSaveDirectory;
	}



}
