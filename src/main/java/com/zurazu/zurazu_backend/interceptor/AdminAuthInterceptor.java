package com.zurazu.zurazu_backend.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.zurazu.zurazu_backend.core.security.Role.Role;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import com.zurazu.zurazu_backend.provider.security.JwtAuthToken;
import com.zurazu.zurazu_backend.provider.security.JwtAuthTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AdminAuthInterceptor implements HandlerInterceptor
{
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	if(request.getMethod().equals("OPTIONS")) {
    		//request method가 OPTIONS일 경우 JWT를 체크하지 않는 방향으로
    		return true;
    	}
        Optional<String> token = jwtAuthTokenProvider.resolveToken(request);
        if(token.isPresent()) {
            //토큰이 존재한다면 해당 string을 객체로 변환해서 검증한다
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            if(jwtAuthToken.validate() && jwtAuthToken.getData().get("role").equals(Role.ADMIN.getCode())) {
                return true;
            }
            throw new CustomJwtRuntimeException();
        }
        throw new CustomJwtRuntimeException();
    }


}