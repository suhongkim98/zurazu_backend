package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.util.S3Uploader;
import com.zurazu.zurazu_backend.web.dto.CommonResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class WebController {
//aws s3 업로드 테스트용
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static"); // s3 의 static 폴더 안에 파일을 넣겠다
    }
    
    @PostMapping("/deleteTest")
    @ResponseBody
    public ResponseEntity<CommonResponse> delete(@RequestParam("filename") String name) throws IOException {
    	String key = "static" + "/" + name;
    	s3Uploader.deleteFileFromS3(key);
    	
    	CommonResponse response = CommonResponse.builder()
    			.status(HttpStatus.OK)
    			.message("ㅋㅋ")
    			.build();
    	return new ResponseEntity<CommonResponse>(response, HttpStatus.OK);
    }
}