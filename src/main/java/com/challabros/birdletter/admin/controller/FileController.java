package com.challabros.birdletter.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.challabros.birdletter.admin.util.DateUtils;
import com.challabros.birdletter.admin.util.Util;
import com.challabros.birdletter.admin.util.JsonBuilder;
import com.challabros.birdletter.admin.util.StringUtil;
import com.challabros.birdletter.admin.util.Value;
import com.challabros.birdletter.admin.error.BirdAdminErrorCode;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.service.FileService;
import com.challabros.birdletter.admin.service.NoticeService;

@Controller
public class FileController {
	
	final static Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * <pre>
	 * 1. Comment : 일반파일 저장
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 18
	 * </pre> 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/file.do", method = RequestMethod.POST)
	public Object fileSubmit(MultipartHttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();

		String uploadPath = Value.NOTICE_UPLOADFILE_PATH;
		//String uploadPath = "/Users/anjiho/Downloads/"; 
		
		File dir = new File(uploadPath);

		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		Iterator<String> iter = request.getFileNames();
		while(iter.hasNext()) {

			String uploadFileName = iter.next();
			MultipartFile mFile = request.getFile(uploadFileName);
			
			String originalFileName = mFile.getOriginalFilename();
			String saveFileName = originalFileName;
			
			/** 파일명이 한글이면 에러 처리 시작 **/
			if (StringUtil.isKorean(originalFileName) == true) {
				return new JsonBuilder().add("result", "korean").build();
			}
			/** 파일명이 한글이면 에러 처리  **/
			
			String makeFileName = saveFileName.substring(0, saveFileName.lastIndexOf("."));	//업로드할 파일 이름
			int pos = saveFileName.lastIndexOf( "." );
			String extension = saveFileName.substring( pos + 1 );	//업로드할 파일 확장자
			String finalFile = makeFileName + "_birdletter." + extension;
			
			
			if(saveFileName != null && !saveFileName.equals("")) {
				if(new File(uploadPath + saveFileName).exists()) {
					//String finalFile = makeFileName + "_birdletter."+extension; 
				}
				try {
					//mFile.transferTo(new File(uploadPath + finalFile));
					fileService.S3FileUpload(Value.BSM_NOTICE_IMAGE_UPLOAD_PATH, finalFile, mFile.getBytes());
					map.put("fileName", saveFileName);				
				} catch (IllegalStateException e) {
					e.printStackTrace();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} // if end

		} // while end
		logger.info("data : " + map.get("fileName"));
		return map;

	} // fileUpload end
	
	/**
	 * <pre>
	 * 1. Comment : 팝업공지 등록(이미지 업로드 포함)
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 04. 06
	 * </pre> 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/insertPopupNotice.do", method=RequestMethod.POST)
	public @ResponseBody String insertPopupNotice(MultipartHttpServletRequest request) {
		int cnt = 0;
		String type = request.getParameter(Util.isNullValue("type", ""));
		String title = request.getParameter(Util.isNullValue("title", ""));
		String content = request.getParameter(Util.isNullValue("content", ""));
		String url = request.getParameter(Util.isNullValue("url", ""));
		String useYn = request.getParameter("useYn");
		String startDate = request.getParameter(Util.isNullValue("startDate", ""));
		String endDate = request.getParameter(Util.isNullValue("endDate", ""));
		String finalFileName = ""; 
		
		if (!"".equals(startDate)) startDate = DateUtils.getOclock(startDate);
		if (!"".equals(endDate)) endDate = DateUtils.getLastDayOclock(endDate);
		
		Iterator<String> it = request.getFileNames();
		//파일 추출
		while (it.hasNext()) {
			String uploadFileName = it.next();
			if (uploadFileName != null) {
				MultipartFile mFile = request.getFile(uploadFileName);

				String orifinalFileName = mFile.getOriginalFilename();
				/** 파일명이 한글이면 에러 처리 시작 **/
				if (StringUtil.isKorean(orifinalFileName) == true) {
					return new JsonBuilder().add("result", "korean").build();
				}
				/** 파일명이 한글이면 에러 처리  **/
				String makeFileName = orifinalFileName.substring(0, orifinalFileName.lastIndexOf("."));
				int filePos = orifinalFileName.lastIndexOf(".");
				String fileExtension = orifinalFileName.substring(filePos+1);
				
				finalFileName = makeFileName + "_" + Util.returnNowDateByYyyymmddhhmmss() + "." + fileExtension;
				
				if (orifinalFileName != null || !"".equals(orifinalFileName)) {
					try {
						//S3파일 업로드
						fileService.S3FileUpload(Value.BSM_NOTICE_IMAGE_UPLOAD_PATH, finalFileName, mFile.getBytes());	
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		try {
			if ("insert".equals(type)) {
				//팝업공지 목록 등록
				cnt = noticeService.insertPopupNotice(title, content, finalFileName, Integer.valueOf(useYn), startDate, endDate, url);
			} else if ("modify".equals(type)) {
				String idx = request.getParameter("idx");
				//팝업공지 수정
				cnt = noticeService.modifyPopupNotice(Integer.valueOf(idx), title, content, finalFileName, Integer.valueOf(useYn), startDate, endDate, url);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (cnt < 1)
			throw new BirdAdminException(BirdAdminErrorCode.INTERNAL_ERROR, "UPDATE ERROR..");
		return new JsonBuilder().add("result", cnt).build();
	}
	
	@RequestMapping(value="/s3upload.do", method = RequestMethod.POST)
	public void upload(@RequestParam("gif_File") MultipartFile file) throws IOException {
		logger.info("s3upload.do");
		logger.info("fileName : " +file.getOriginalFilename());
		byte[] data = file.getBytes();
		logger.info("byte : " + data);
		
		fileService.S3FileUpload(Value.BSM_FILE_UPLOAD_PATH, file.getOriginalFilename(), data);
	}
	
	public static File byteArrayToFile(byte[] bytearray, String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bytearray);
		fos.close();
		logger.info("file : " + file);
		return file;
	}

}
