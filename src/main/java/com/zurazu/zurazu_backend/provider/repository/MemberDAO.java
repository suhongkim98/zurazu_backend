package com.zurazu.zurazu_backend.provider.repository;

import com.zurazu.zurazu_backend.core.repository.MemberDAOInterface;
import com.zurazu.zurazu_backend.exception.errors.RegisterFailException;
import com.zurazu.zurazu_backend.provider.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDAO implements MemberDAOInterface {
    private final SqlSessionTemplate sqlSession;
    @Override
    public void registerMember(MemberDTO memberDTO) {
        MemberDTO isExistMember = findMember(memberDTO.getEmail());
        if(isExistMember != null) {
            // 이미 이메일이 존재
            throw new RegisterFailException();
        }
        sqlSession.insert("memberTable.insertMember", memberDTO);
    }

    @Override
    public MemberDTO findMember(String email) {
        MemberDTO member = sqlSession.selectOne("memberTable.searchMember", email);
        return member;
    }

    @Override
    public MemberDTO validMember(MemberDTO memberDTO) {
        return sqlSession.selectOne("memberTable.validMember", memberDTO);
    }

    @Override
    public void updateRefreshToken(String email, String token) {
        MemberDTO member = new MemberDTO();
        member.setEmail(email);
        member.setRefreshToken(token);
        sqlSession.update("memberTable.updateRefreshToken", member);
    }
}
