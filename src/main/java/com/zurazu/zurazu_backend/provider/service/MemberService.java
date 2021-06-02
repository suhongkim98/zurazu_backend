package com.zurazu.zurazu_backend.provider.service;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.core.service.MemberServiceInterface;
import com.zurazu.zurazu_backend.exception.errors.RegisterFailException;
import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.provider.dto.PersonalInfoDTO;
import com.zurazu.zurazu_backend.provider.repository.MemberDAO;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;
import com.zurazu.zurazu_backend.util.SHA256Util;
import com.zurazu.zurazu_backend.provider.dto.MemberProfileDTO;
import com.zurazu.zurazu_backend.web.dto.MemberWebDTO;
import com.zurazu.zurazu_backend.web.dto.RefreshTokenDTO;
import com.zurazu.zurazu_backend.web.dto.RegisterMemberDTO;
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
    public void registerMemberByEmail(RegisterMemberDTO registerMemberDTO) {
        MemberDTO isExistMember = memberDAO.findMemberByEmail(registerMemberDTO.getEmail());
        if(isExistMember != null) {
            // 이미 이메일이 존재
            throw new RegisterFailException();
        }
        //개인정보 등록
        PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
        personalInfoDTO.setRealName(registerMemberDTO.getRealName());
        personalInfoDTO.setGender(registerMemberDTO.getGender());
        personalInfoDTO.setBirth(registerMemberDTO.getBirth());
        personalInfoDTO.setPhoneNumber(registerMemberDTO.getPhoneNumber());
        personalInfoDTO.setAgreeTermsOfService(registerMemberDTO.getAgreeTermsOfService());
        personalInfoDTO.setAgreeCollectionPersonalInfo(registerMemberDTO.getAgreeCollectionPersonalInfo());
        personalInfoDTO.setAgreeReceiveEmail(registerMemberDTO.getAgreeReceiveEmail());
        personalInfoDTO.setAgreePushNotification(registerMemberDTO.getAgreePushNotification());
        personalInfoDTO.setAgreeReceiveSMS(registerMemberDTO.getAgreeReceiveSMS());
        personalInfoDTO.setAgreeReceiveKAKAO(registerMemberDTO.getAgreeReceiveKAKAO());
        personalInfoDTO.setAgreeUpperFourteen(registerMemberDTO.getAgreeUpperFourteen());

        memberDAO.registerMemberPersonalInformation(personalInfoDTO);
        //회원 등록
        String salt = SHA256Util.generateSalt(); // salt
        String encryptedPassword = SHA256Util.getEncrypt(registerMemberDTO.getPassword(), salt); // 암호화

        MemberDTO member = new MemberDTO();
        member.setPersonalInfoIdx(personalInfoDTO.getIdx());
        member.setEmail(registerMemberDTO.getEmail());
        member.setPassword(encryptedPassword);
        member.setSalt(salt);

        memberDAO.registerMemberByEmail(member);
    }

    @Override
    public Optional<MemberDTO> loginMemberByEmail(MemberWebDTO memberWebDTO) {
            MemberDTO member = memberDAO.findMemberByEmail(memberWebDTO.getEmail()); // 아이디가 없다.
            if(member == null) {
                return Optional.empty();
            }

            //솔트 꺼내서 평문을 암호화한 후
            //비교
            String salt = member.getSalt();
            String encryptedPassword = SHA256Util.getEncrypt(memberWebDTO.getPassword(), salt);

            member.setPassword(encryptedPassword);
            member = memberDAO.validMemberByEmail(member); // 비밀번호가 틀렸다.
            if(member == null) {
                return Optional.empty();
            }

            String accessToken = createAccessToken(String.valueOf(member.getIdx()));

            String refreshToken;
            // db에서 꺼낸 refresh토큰이 만료된 경우 재발급
            if(member.getRefreshToken() == null) {
                refreshToken = createRefreshToken(String.valueOf(member.getIdx()));
                // 재발급 받았으니 디비에 삽입
                memberDAO.updateRefreshToken(String.valueOf(member.getIdx()), refreshToken);
            } else {
                JwtAuthToken jwtAuthRefreshToken = jwtAuthTokenProvider.convertAuthToken(member.getRefreshToken());
                refreshToken = jwtAuthRefreshToken.getToken();
                if(!jwtAuthRefreshToken.validate()) {
                    refreshToken = createRefreshToken(String.valueOf(member.getIdx()));
                    // 재발급 받았으니 디비에 삽입
                    memberDAO.updateRefreshToken(String.valueOf(member.getIdx()), refreshToken);
                }
            }

            member.setAccessToken(accessToken);
            member.setRefreshToken(refreshToken);

            return Optional.ofNullable(member);
    }

    @Override
    public Optional<String> validMemberRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(refreshTokenDTO.getRefreshToken());
        if(!jwtAuthToken.validate() || !jwtAuthToken.getData().get("role").equals(Role.USER.getCode())) {
            return Optional.empty();
        }

        String idx = String.valueOf(jwtAuthToken.getData().get("id"));

        String accessToken = createAccessToken(idx);
        return Optional.ofNullable(accessToken);
    }

    @Override
    public String createAccessToken(String idx) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 후 refresh token
        JwtAuthToken accessToken = jwtAuthTokenProvider.createAuthToken(idx, Role.USER.getCode(), expiredDate);  //토큰 발급

        return accessToken.getToken();
    }

    @Override
    public String createRefreshToken(String idx) {
        Date expiredDate = Date.from(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant()); // refresh토큰은 유효기간이 1년
        JwtAuthToken refreshToken = jwtAuthTokenProvider.createAuthToken(idx, Role.USER.getCode(), expiredDate);  //토큰 발급
        return refreshToken.getToken();
    }

    @Override
    public Optional<MemberProfileDTO> getProfileInfo(int memberIdx) {
        return Optional.ofNullable(memberDAO.memberProfileInfo(memberIdx));
    }
}
