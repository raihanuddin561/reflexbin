package com.reflexbin.reflexbin_api.service.impl;

import com.reflexbin.reflexbin_api.constant.ApplicationConstants;
import com.reflexbin.reflexbin_api.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
     * extract username
     * @param token String
     * @return String
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * extracting claims
     * @param token String
     * @param claimsResolver Function
     * @return T
     */
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * extract all claims method
     * @param token String
     * @return Claims
     */
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

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
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
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
