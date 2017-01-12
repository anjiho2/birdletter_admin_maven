package com.challabros.birdletter.admin.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.challabros.birdletter.admin.error.BirdAdminException;
import com.challabros.birdletter.admin.util.FileUtil;
import com.challabros.birdletter.admin.util.Value;


@Service
public class FileService {

	private String BUCKET = Value.AWS_BUCKET;
	private String ACCESSKEY = Value.AWS_S3_ACCESSKEY;
	private String SECRETKEY = Value.AWS_S3_SECRETKEY;
	
	/**
	 * <pre>
	 * 1. Comment : AMAZON S3 파일업로드 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 22
	 * </pre>  
	 * @param saveDir
	 * @param fileName
	 * @param data
	 */
	public void S3FileUpload(String saveDir, String fileName, byte[] data) {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESSKEY, SECRETKEY);
		AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials);
		
		saveDir = saveDir.replaceFirst("/", "");
		fileName = fileName.replaceFirst("/", "");
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
		PutObjectRequest putObjRequest = new PutObjectRequest(
				BUCKET, 
				FileUtil.concatPath(saveDir, fileName).replaceFirst("/", ""), 
				byteInputStream,
				new ObjectMetadata()
		);
		amazonS3Client.putObject(putObjRequest);
		
		try {
			byteInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BirdAdminException(500, e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * <pre>
	 * 1. Comment : AMAZON S3 파일삭제 
	 * 2. 작성자 : 안지호
	 * 3. 작성일 : 2016. 02. 22
	 * </pre> 
	 * @param saveDir
	 * @param fileName
	 * @return
	 */
	public int S3FileRemove(String saveDir, String fileName) {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(ACCESSKEY, SECRETKEY);
		AmazonS3Client amazonS3Client = new AmazonS3Client(basicAWSCredentials);
		
		String filePath = FileUtil.concatPath(saveDir, fileName).replaceFirst("/", "");
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(BUCKET, filePath);
		amazonS3Client.deleteObject(deleteObjectRequest);
		return 1;
	}
}
