package org.example.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers) throws SignatureException {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    public boolean isTokenExpired(String token) throws SignatureException{
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws SignatureException {
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractRole(String token) throws SignatureException {
        return extractClaim(token,
                claims -> claims.get("role",String.class));
    }
    private Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSigningKey() throws SignatureException {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}