package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.MemberDTO;

public interface MemberDAOInterface {
    void registerMember(MemberDTO memberDTO);
    MemberDTO findMember(String email);
    MemberDTO validMember(MemberDTO memberDTO); // 아이디 비밀번호 검증
    void updateRefreshToken(String email, String token);
}
