package com.challabros.birdletter.admin.util;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.challabros.birdletter.admin.error.BirdAdminException;

/**
 * 
 * @author anjiho
 * 아마존(AWS) S3파일 저장 클래스
 */
public class S3FileTransfer {
	
//	private AmazonS3Client amazonS3Client;
	
	private String bucket = "birdletter-test";
	    
	public boolean save(String saveDir, String fileName, byte[] data) {
		boolean bl = true;
		BasicAWSCredentials bw = new BasicAWSCredentials("AKIAIGH3JVO7LQZHFZQQ", "z/WKJawXXkxtWZ4aYBd97/N1pLkjePYa7AvIiD7R");
		AmazonS3Client cl = new AmazonS3Client(bw);
		
		saveDir = saveDir.replaceFirst("/", "");
		fileName = fileName.replaceFirst("/", "");
		System.out.println("saveDir : " + saveDir);
		System.out.println("fileName : " + fileName);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
		PutObjectRequest putObjectRequest = new PutObjectRequest(
				bucket,
				FileUtil.concatPath(saveDir,fileName).replaceFirst("/", ""),
				inputStream,
				new ObjectMetadata()
		);
		cl.putObject(putObjectRequest);
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BirdAdminException(500, e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			bl = false;
		}
		return true;
	}
}


