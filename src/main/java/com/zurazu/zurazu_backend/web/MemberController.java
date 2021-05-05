package com.zurazu.zurazu_backend.web;

import com.zurazu.zurazu_backend.web.dto.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerMember() {

    }
}
