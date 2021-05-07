package com.zurazu.zurazu_backend.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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

/**
 MultipartFile을 전달 받고
 S3에 전달할 수 있도록 MultiPartFile을 File로 전환
 S3에 Multipartfile 타입은 전송이 안됩니다.

 전환된 File을 S3에 public 읽기 권한으로 put
 외부에서 정적 파일을 읽을 수 있도록 하기 위함입니다.

 로컬에 생성된 File 삭제
 Multipartfile -> File로 전환되면서 로컬에 파일 생성된것을 삭제합니다.
 업로드된 파일의 S3 URL 주소를 반환
 */


@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String upload(MultipartFile uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getOriginalFilename();
        String uploadImageUrl = putS3(uploadFile, fileName);
        return uploadImageUrl;
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

}