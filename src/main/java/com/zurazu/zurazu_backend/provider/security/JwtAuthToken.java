package com.zurazu.zurazu_backend.provider.security;

import com.zurazu.zurazu_backend.core.security.AuthToken;
import com.zurazu.zurazu_backend.exception.errors.CustomJwtRuntimeException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims> {
// 토큰 발급, 검증

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    JwtAuthToken(String token, Key key) {
        this.token = token;
        this.key = key;
    }

    JwtAuthToken(String id, String role, Date expiredDate, Key key) {
        this.key = key;
        this.token = createJwtAuthToken(id, role, expiredDate).get();
    }

    @Override
    public boolean validate() {
        return getData() != null;
    }

    @Override
    public Claims getData() {

        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            throw new CustomJwtRuntimeException();
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new CustomJwtRuntimeException();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new CustomJwtRuntimeException();
        }
    }

    private Optional<String> createJwtAuthToken(String id, String role, Date expiredDate) {

        var token = Jwts.builder()
                .claim("id", id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();

        return Optional.ofNullable(token);
    }
}
