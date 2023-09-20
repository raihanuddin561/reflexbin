package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Service class
 *
 * @author raihan
 */
@Service
public class JWTServiceImpl implements JWTService {
    /**
     * generateToken method
     *
     * @param userName String
     * @return String
     */
    @Override
    public String generateToken(String userName) {
        return generateToken(new HashMap<>(), userName);
    }

    /**
     * generateToken method
     *
     * @param claims   Map
     * @param userName String
     * @return String
     */
    private String generateToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    /**
     * getSigninKey for BASE64 bytes
     *
     * @return Key
     */
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ApplicationConstants.SECURITY_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
