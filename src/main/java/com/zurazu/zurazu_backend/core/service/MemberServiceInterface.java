package com.zurazu.zurazu_backend.core.service;

import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.web.dto.MemberWebDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenDTO;

import java.util.Optional;

public interface MemberServiceInterface {
    void registerMemberByEmail(MemberWebDTO memberWebDTO);
    Optional<MemberDTO> loginMemberByEmail(MemberWebDTO memberWebDTO);
    Optional<String> validMemberRefreshToken(RefreshTokenDTO refreshTokenDTO);
    String createAccessToken(String idx);
    String createRefreshToken(String idx);
}
