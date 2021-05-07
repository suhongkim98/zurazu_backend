package com.zurazu.zurazu_backend.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.PageAttributes.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 MultipartFile을 전달 받고 경로를 포함한 파일이름 변수를 만든다.

 메타데이터 생성 후 파일이름으로 S3에 public 읽기 권한으로 put
 외부에서 정적 파일을 읽을 수 있도록 하기 위함입니다.

 업로드된 파일의 S3 URL 주소를 반환
 */


@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String upload(MultipartFile uploadFile, String dirName) {
    	String fileType = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
		String randomName = UUID.randomUUID().toString() + fileType; // 파일 중복되지 않게 고유식별자 생성
		
        String fileName = dirName + "/" + randomName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        return uploadImageUrl;
    }
    public void deleteFileFromS3(String key) {
    	//key는 경로, 파일이름 풀로 ex) static/test.txt
    	deleteFile(key);
    }

    private String putS3(MultipartFile uploadFile, String fileName) {
    	ObjectMetadata metadata = new ObjectMetadata();
    	metadata.setContentType(uploadFile.getContentType());
    	metadata.setContentLength(uploadFile.getSize());
    	try {
    	       amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));
   		
    	}catch (IOException e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
         return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    
    private void deleteFile(String key) {
    	DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
    	try {
    		amazonS3Client.deleteObject(deleteObjectRequest);
    	}catch(AmazonServiceException e) {
    		e.printStackTrace();
    	}catch (SdkClientException e) {
    		e.printStackTrace();
		}
    }

}