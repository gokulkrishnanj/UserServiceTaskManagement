package com.example.userServiceTaskManagement.Service.Impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final String secretKey = "fdc9871ba2504803cc2cda1a54f005ada12b6c7aed326d3b2cf19075e08d21223799af4c121e141d8b8b46d24365f7371cf46fed86116399740b1c8b3d6a329385c9905a0a298994c4e26860cfa7165606f575a1eff0dcb9ea01ee7fe43ce83757a41641700d0d3a962d31e5b5a58ffbd6014bdc2c42ea19810e35167af81029f592386edb4aee54f8113d29a8e08faa274f108998f115c5542452ab1013d0c5abdce3178d181034abea846d56d0425aa1ec251badf3487db6ae44112e7cae4e46ec96ac9da8729c5d3b6772b5063f37dd5534fa8235583c04ddfc739ce4078562308de0c8e162352a2f6123d0f5f155d8ff0ddb00955015382e3ce12ad39db8";
    Map<String, Object> claims = new HashMap<>();

    public String generateToken(UserDetails userDetails) {
        System.out.println("vd:" + userDetails.getAuthorities());
        claims.put("roles", Arrays.asList(userDetails.getAuthorities()));
        return
                Jwts
                        .builder()
                        .addClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuer("Spring")
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000))
                        .signWith(generateSecretKey())
                        .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return
                Jwts
                        .builder()
                        .addClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuer("Spring")
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 20 * 1000))
                        .signWith(generateSecretKey())
                        .compact();
    }


    private final SecretKey generateSecretKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUserNameFromToken(String jwtToken) {
        return extractUserNameFromToken(jwtToken, Claims::getSubject);
    }

    private <T> T extractUserNameFromToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims1 = extractClaims(token);
        return claimsTFunction.apply(claims1);
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.io.DecodingException e) {
            System.err.println("JWT Decoding Error: " + e.getMessage());
            throw e; // Re-throw for debugging
        } catch (Exception e) {
            System.err.println("JWT Parsing Error: " + e.getMessage());
            throw e; // Re-throw for debugging
        }
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userName = extractUserNameFromToken(jwtToken);
        boolean isExpired = isExpired(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isExpired);
    }

    private boolean isExpired(String token) {
        Date date = extractUserNameFromToken(token, Claims::getExpiration);
        return date.before(new Date());
    }

    public String getUserNameFromToken(String token) {
        return extractUserNameFromToken(token);
    }
}
