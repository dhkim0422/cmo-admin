package com.insilicogen.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * @Class Name  : EgovFileMngUtil.java
 * @Description : 메시지 처리 관련 유틸리티
 * @Modification Information
 *
 *     수정일         수정자                   수정내용
 *     -------          --------        ---------------------------
 *   2009.02.13       이삼섭                  최초 생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 02. 13
 * @version 1.0
 * @see
 *
 */
@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

    private static final int BUFF_SIZE = 2048;

    @Resource(name = "propertiesService")
    private EgovPropertyService propertyService;

    /**
	 * [설명이 들어가는 부분]
     * @param stordFilePath
	 *
	 * @param
	 * @return
	 * @exception
	 * @see
	 */
	public static FileVO toFastaFile(String query, String stordFilePath) throws Exception {

		String filePath = EgovProperties.getProperty("file.path.analysis");
		String nanoTime = EgovDateUtil.getToday()+System.nanoTime();	
		String dirName  = nanoTime.substring(0, 6); //YYYYMM

		String analyticsExePath = filePath+dirName;
		File outExePath = new File(analyticsExePath);
		if (!outExePath.exists()) {outExePath.mkdirs();}
		
		String fastaFilePathNm = stordFilePath + EgovStringUtil.getTimeStamp() + ".fasta";
		//System.out.println("fastaFilePathNm : "+fastaFilePathNm);
		File fastaFile = new File(fastaFilePathNm);
		//System.out.println("fastaFile : "+fastaFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(fastaFile));			
		try{
			bw.write(query);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bw.close();
		}

		FileVO fvo = new FileVO();
		fvo.setFileExtsn(".fa");
	    fvo.setFileStreCours(fastaFile.getAbsolutePath());
	    fvo.setFileSize(Long.toString(fastaFile.length()));
	    fvo.setOrignlFileNm(fastaFile.getName());
	    fvo.setStreFileNm(fastaFile.getName());
	    fvo.setAtchFileId("fasta_seq");
	    fvo.setFileSn(String.valueOf("fasta_seq"));
	    //System.out.println("FileVO : "+fvo.toString());
		return fvo;
	}
	
	public static FileVO toFastaFile2(String query, String stordFilePath, int cmprGroupId) throws Exception {
		String filePath = stordFilePath;
		String nanoTime = EgovDateUtil.getToday()+System.nanoTime();
		String dirName = nanoTime.substring(0, 6); //YYYYMM;
		
		String analyticsExePath = filePath + cmprGroupId + "/" + dirName; //최종경로
		
		File outExePath = new File(analyticsExePath);
		if (!outExePath.exists()) {
			//디렉토리 생성.
			outExePath.mkdirs();
		}
		
		System.out.println("파스타 파일 진입");
			System.out.println("저장된 경로 : "+stordFilePath);
			File fastaFile = new File(analyticsExePath+ "/" +EgovStringUtil.getTimeStamp() + ".fasta" );
			System.out.println("파스타 파일 : "+fastaFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fastaFile));			
			System.out.println("파스타 bw : "+bw.toString());
			try{
				bw.write(query);
			}catch(Exception e){
				System.out.println("예외상황");
				e.printStackTrace();
			}finally{
				bw.close();
			}

			FileVO fvo = new FileVO();

			fvo.setFileExtsn(".fa");
		    fvo.setFileStreCours(fastaFile.getAbsolutePath());
		    fvo.setFileSize(Long.toString(fastaFile.length()));
		    fvo.setOrignlFileNm(fastaFile.getName());
		    fvo.setStreFileNm(fastaFile.getName());
		    fvo.setAtchFileId("fasta_seq");
		    fvo.setFileSn(String.valueOf("fasta_seq"));
		    System.out.println("파스타 vo : "+fvo.toString());
			return fvo;

	}
	
	public static FileVO toFastaFile3(String query, String stordFilePath, int cmprGroupId) throws Exception {
		String filePath = stordFilePath;
		String nanoTime = EgovDateUtil.getToday()+System.nanoTime();
		String dirName = nanoTime.substring(0, 6); //YYYYMM;
		
		String analyticsExePath = filePath + cmprGroupId + "/" + dirName; //최종경로
		
		File outExePath = new File(analyticsExePath);
		if (!outExePath.exists()) {
			//디렉토리 생성.
			outExePath.mkdirs();
		}
		
		System.out.println("파스타 파일 진입");
			System.out.println("저장된 경로 : "+stordFilePath);
			File fastaFile = new File(analyticsExePath+ "/" +EgovStringUtil.getTimeStamp() + ".fasta" );
			System.out.println("파스타 파일 : "+fastaFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fastaFile));			
			System.out.println("파스타 bw : "+bw.toString());
			try{
				bw.write(query);
			}catch(Exception e){
				System.out.println("예외상황");
				e.printStackTrace();
			}finally{
				bw.close();
			}

			FileVO fvo = new FileVO();

			fvo.setFileExtsn(".fa");
		    fvo.setFileStreCours(fastaFile.getAbsolutePath());
		    fvo.setFileSize(Long.toString(fastaFile.length()));
		    fvo.setOrignlFileNm(fastaFile.getName());
		    fvo.setStreFileNm(fastaFile.getName());
		    fvo.setAtchFileId("fasta_seq");
		    fvo.setFileSn(String.valueOf("fasta_seq"));
		    System.out.println("파스타 vo : "+fvo.toString());
			return fvo;

	}
	
	public static FileVO toFastaFile3(String query, String stordFilePath) throws Exception {
		String filePath = EgovProperties.getProperty("blast.out.path");
		String nanoTime = EgovDateUtil.getToday()+System.nanoTime();
		String dirName = nanoTime.substring(0, 6); //YYYYMM
		
		String analyticsExePath = filePath+dirName; //최종경로
		
		File outExePath = new File(analyticsExePath);
		if (!outExePath.exists()) {
			//디렉토리 생성.
			outExePath.mkdirs();
		}
		
		System.out.println("파스타 파일 진입");
			System.out.println("저장된 경로 : "+stordFilePath);
			File fastaFile = new File(stordFilePath+EgovStringUtil.getTimeStamp() + ".fasta" );
			System.out.println("파스타 파일 : "+fastaFile);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fastaFile));			
			System.out.println("파스타 bw : "+bw.toString());
			try{
				bw.write(query);
			}catch(Exception e){
				System.out.println("예외상황");
				e.printStackTrace();
			}finally{
				bw.close();
			}

			FileVO fvo = new FileVO();

			fvo.setFileExtsn(".fa");
		    fvo.setFileStreCours(fastaFile.getAbsolutePath());
		    fvo.setFileSize(Long.toString(fastaFile.length()));
		    fvo.setOrignlFileNm(fastaFile.getName());
		    fvo.setStreFileNm(fastaFile.getName());
		    fvo.setAtchFileId("fasta_seq");
		    fvo.setFileSn(String.valueOf("fasta_seq"));
		    System.out.println("파스타 vo : "+fvo.toString());
			return fvo;

	}

    /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr,
    		int fileKeyParam, String atchFileId, String storePath) throws Exception {

		int fileKey = fileKeyParam;
		String storePathString = "";
		String atchFileIdString = "";

		if ("".equals(storePath) || storePath == null) {
		    storePathString = propertyService.getString("tempDir");
		} else {
		    storePathString = storePath;
		}

		if ("".equals(atchFileId) || atchFileId == null) {
		    atchFileIdString = "IMG ";
		} else {
		    atchFileIdString = atchFileId;
		}

		File saveFolder = new File(storePathString);

		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs();
		}
		if(files==null) throw new FileNotFoundException("FileNotFoundException");
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result  = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();

		    file = entry.getValue();

		    String orginFileName = file.getOriginalFilename();

		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if(!EgovStringUtil.nullConvert(KeyStr).equals("communityDB_")){
		    	if ("".equals(orginFileName)) {
		    		continue;
		    	}
			}
		    ////------------------------------------

		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileExt = orginFileName.substring(index + 1);
		    String newName = KeyStr + EgovStringUtil.getTimeStamp() + fileKey+"."+fileExt.toLowerCase();
		    long _size = file.getSize();

		    if (!"".equals(orginFileName)) {
			filePath = storePathString + File.separator + newName;
			file.transferTo(new File(filePath));
		    }

		    fvo = new FileVO();
		    fvo.setFileExtsn(fileExt);
		    fvo.setFileStreCours(filePath);
		    fvo.setFileSize(Long.toString(_size));
		    fvo.setOrignlFileNm(orginFileName);
		    fvo.setStreFileNm(newName);
		    fvo.setAtchFileId(entry.getKey());
		    fvo.setFileSn(String.valueOf(fileKey));
		    fvo.setFileContentType(file.getContentType());

		    writeFile(file, newName, storePathString);
		    result.add(fvo);

		    fileKey++;
		}

		return result;
    }

    /**
     * 첨부파일을 서버에 저장한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws Exception
     */
    protected void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
	InputStream stream = null;
	OutputStream bos = null;

	if(file==null) throw new FileNotFoundException("FileNotFoundException");
	try {
	    stream = file.getInputStream();
	    File cFile = new File(stordFilePath);

	    if (!cFile.isDirectory()) {
		boolean _flag = cFile.mkdir();
		if (!_flag) {
		    throw new IOException("Directory creation Failed ");
		}
	    }

	    bos = new FileOutputStream(stordFilePath + File.separator + newName);

	    int bytesRead = 0;
	    byte[] buffer = new byte[BUFF_SIZE];

	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
		bos.write(buffer, 0, bytesRead);
	    }
	} catch (FileNotFoundException fnfe) {
	    System.err.println("FileNotFoundException");
	} catch (IOException ioe) {
		System.err.println("IOException");
	} catch (Exception e) {
		System.err.println("Exception");
	} finally {
	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception ignore) {
			//ignore.printStackTrace();
			//log.debug("IGNORED: " + ignore.getMessage());
		}
	    }
	    if (stream != null) {
		try {
		    stream.close();
		} catch (Exception ignore) {
			System.err.println("Exception");
			//ignore.printStackTrace();
			//log.debug("IGNORED: " + ignore.getMessage());
		}
	    }
	}
    }

    /**
     * 서버의 파일을 다운로드한다.
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String downFileName = "";
		String orgFileName = "";
		String contentType = "application/x-msdownload";   //binary/octet-stream

		if((String)request.getAttribute("fileType") !=null){
			contentType = request.getAttribute("fileType").toString();
		}
		if ((String)request.getAttribute("downFile") == null) {
		    downFileName = "";
		} else {
		    downFileName = (String)request.getAttribute("downFile");
		}

		if ((String)request.getAttribute("orginFile") == null) {
		    orgFileName = "";
		} else {
		    orgFileName = (String)request.getAttribute("orginFile");
		}

		File file = new File(downFileName);

		if (!file.exists()) {
		    throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
		    throw new FileNotFoundException(downFileName);
		}
		//system.out.println(contentType);
		byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.

		response.setContentType(contentType);
		response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(), "UTF-8"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
		int read = 0;

		while ((read = fin.read(b)) != -1) {
		    outs.write(b, 0, read);
		}

		outs.close();
		fin.close();
		//system.out.println("download end");
    }

    /**
     * 첨부로 등록된 파일을 서버에 업로드한다.
     *
     * @param file
     * @param stordFilePath
     * @return
     * @throws Exception
     */
    public HashMap<String, String> uploadFile(MultipartFile file, String stordFilePath) throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		//Write File 이후 Move File????
		if(file==null) throw new FileNotFoundException("FileNotFoundException");
		String newName = "";
		String orginFileName = file.getOriginalFilename();
		int index = orginFileName.lastIndexOf(".");
		String fileExt = orginFileName.substring(index + 1);
		long size = file.getSize();
		newName = EgovStringUtil.getTimeStamp() + "." + fileExt;
		writeFile(file, newName, stordFilePath);
		//storedFilePath는 지정
		map.put(Globals.ORIGIN_FILE_NM, orginFileName);
		map.put(Globals.UPLOAD_FILE_NM, newName);
		map.put(Globals.FILE_EXT, fileExt);
		map.put(Globals.FILE_PATH, stordFilePath);
		map.put(Globals.FILE_SIZE, String.valueOf(size));

		return map;
    }

    /**
     * 파일을 실제 물리적인 경로에 생성한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws Exception
     */
    protected  void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
		InputStream stream = null;
		OutputStream bos = null;
		if(file==null) throw new FileNotFoundException("FileNotFoundException");
		try {
		    stream = file.getInputStream();
		    File cFile = new File(stordFilePath);

		    if (!cFile.isDirectory())
			cFile.mkdir();

		    bos = new FileOutputStream(stordFilePath + File.separator + newName);

		    int bytesRead = 0;
		    byte[] buffer = new byte[BUFF_SIZE];

		    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
			bos.write(buffer, 0, bytesRead);
		    }
		} catch (FileNotFoundException fnfe) {
			System.err.println("에러 발생");
		} catch (IOException ioe) {
			System.err.println("에러 발생");
		} catch (Exception e) {
			System.err.println("에러 발생");
		} finally {
		    if (bos != null) {
			try {
			    bos.close();
			} catch (Exception ignore) {
				System.err.println("에러 발생");
			}
		    }
		    if (stream != null) {
			try {
			    stream.close();
			} catch (Exception ignore) {
			    System.err.println("에러 발생");
			}
		    }
		}
    }

    /**
     * 서버 파일에 대하여 다운로드를 처리한다.
     *
     * @param response
     * @param streFileNm
     *            : 파일저장 경로가 포함된 형태
     * @param orignFileNm
     * @throws Exception
     */
    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws Exception {
		String downFileName = streFileNm;
		String orgFileName = orignFileNm;

		File file = new File(downFileName);

		if (!file.exists()) {
		    throw new FileNotFoundException(downFileName);
		}

		if (!file.isFile()) {
		    throw new FileNotFoundException(downFileName);
		}

		//byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
		int fSize = (int)file.length();
		if (fSize > 0) {
		    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		    String mimetype = "text/html"; //"application/x-msdownload"

		    response.setBufferSize(fSize);
		    response.setContentType(mimetype);
		    response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);
		    response.setContentLength(fSize);

		    FileCopyUtils.copy(in, response.getOutputStream());
		    in.close();
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
    }
}
