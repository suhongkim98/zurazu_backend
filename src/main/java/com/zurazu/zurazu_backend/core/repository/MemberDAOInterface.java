package com.zurazu.zurazu_backend.core.repository;

import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.provider.dto.PersonalInfoDTO;

public interface MemberDAOInterface {
    void registerMemberByEmail(MemberDTO memberDTO);
    void registerMemberPersonalInformation(PersonalInfoDTO personalInfoDTO);
    MemberDTO findMemberByEmail(String email);
    MemberDTO validMemberByEmail(MemberDTO memberDTO); // 아이디 비밀번호 검증
    void updateRefreshToken(String idx, String token);
}
