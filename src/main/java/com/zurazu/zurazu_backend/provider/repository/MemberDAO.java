package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.MemberDAOInterface;
import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import com.zurazu.zurazu_backend.provider.dto.PersonalInfoDTO;
import com.zurazu.zurazu_backend.provider.dto.MemberProfileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDAO implements MemberDAOInterface {
    private final SqlSessionTemplate sqlSession;
    @Override
    public void registerMemberByEmail(MemberDTO memberDTO) {
        sqlSession.insert("memberTable.insertMemberByEmail", memberDTO);
    }

    @Override
    public void registerMemberPersonalInformation(PersonalInfoDTO personalInfoDTO) {
        sqlSession.insert("personalInfoTable.insertPersonalInfo", personalInfoDTO);
    }

    @Override
    public MemberDTO findMemberByEmail(String email) {
        MemberDTO member = sqlSession.selectOne("memberTable.searchMemberByEmail", email);
        return member;
    }

    @Override
    public MemberDTO validMemberByEmail(MemberDTO memberDTO) {
        return sqlSession.selectOne("memberTable.validMemberByEmail", memberDTO);
    }

    @Override
    public void updateRefreshToken(String idx, String token) {
        MemberDTO member = new MemberDTO();
        member.setIdx(Integer.valueOf(idx));
        member.setRefreshToken(token);
        sqlSession.update("memberTable.updateRefreshToken", member);
    }

    @Override
    public MemberProfileDTO memberProfileInfo(int memberIdx) {
        MemberProfileDTO memberProfile = sqlSession.selectOne("memberTable.selectOneProfile", memberIdx);
        memberProfile.setTradeCount(memberProfile.getApplyCount() + memberProfile.getPurchaseCount());
        return memberProfile;
    }
}
