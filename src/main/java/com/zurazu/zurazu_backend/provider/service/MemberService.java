package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.core.service.MemberServiceInterface;
import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.provider.repository.MemberDAO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.util.SHA256Util;
import com.zurazu.zurazu_backend.web.dto.MemberWebDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceInterface {
    private final MemberDAO memberDAO;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public void registerMember(MemberWebDTO memberWebDTO) {
        String refreshToken = createRefreshToken(memberWebDTO.getEmail());
        String salt = SHA256Util.generateSalt(); // salt
        String encryptedPassword = SHA256Util.getEncrypt(memberWebDTO.getPassword(), salt); // 암호화

        MemberDTO member = new MemberDTO();
        member.setEmail(memberWebDTO.getEmail());
        member.setPassword(encryptedPassword);
        member.setSalt(salt);
        member.setRefreshToken(refreshToken);

        memberDAO.registerMember(member);
    }

    @Override
    public Optional<MemberDTO> loginMember(MemberWebDTO memberWebDTO) {
            MemberDTO member = memberDAO.findMember(memberWebDTO.getEmail()); // 아이디가 없다.
            if(member == null) {
                return Optional.empty();
            }

            //솔트 꺼내서 평문을 암호화한 후
            //비교
            String salt = member.getSalt();
            String encryptedPassword = SHA256Util.getEncrypt(memberWebDTO.getPassword(), salt);

            member.setPassword((encryptedPassword));
            member = memberDAO.validMember(member); // 비밀번호가 틀렸다.
            if(member == null) {
                return Optional.empty();
            }

            String accessToken = createAccessToken(member.getEmail());

            // db에서 꺼낸 refresh토큰이 만료된 경우 재발급
            JwtAuthToken jwtAuthRefreshToken = jwtAuthTokenProvider.convertAuthToken(member.getRefreshToken());
            String refreshToken = jwtAuthRefreshToken.getToken();
            if(!jwtAuthRefreshToken.validate()) {
                refreshToken = createRefreshToken(member.getEmail());
                // 재발급 받았으니 디비에 삽입
                memberDAO.updateRefreshToken(member.getEmail(), refreshToken);
            }

            member.setAccessToken(accessToken);
            member.setRefreshToken(refreshToken);

            return Optional.ofNullable(member);
    }

    @Override
    public Optional<String> validMemberRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(refreshTokenDTO.getRefreshToken());
        if(!jwtAuthToken.validate()) {
            return Optional.empty();
        }

        String email = String.valueOf(jwtAuthToken.getData().get("id"));

        String accessToken = createAccessToken(email);
        return Optional.ofNullable(accessToken);
    }

    @Override
    public String createAccessToken(String email) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 후 refresh token
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(email, Role.USER.getCode(), expiredDate);  //토큰 발급

        return accessToken.getToken();
    }

    @Override
    public String createRefreshToken(String email) {
        Date expiredDate = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant()); // refresh토큰은 유효기간이 1년
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(email, Role.USER.getCode(), expiredDate);  //토큰 발급
        return refreshToken.getToken();
    }
}
