package com.github.gabrielgua.websocket_chat.api.security;

import com.github.gabrielgua.websocket_chat.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthProperties properties;

    public String generateToken(User user) {
        return buildToken(new HashMap<>(), user.getUsername(), properties.getToken().getExpiration());
    }

    private String buildToken(Map<String, Object> claims, String subject, long expiration) {
        var nowMs = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("websocket-chat")
                .setIssuedAt(new Date(nowMs))
                .setExpiration(new Date(nowMs + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String subject) {
        final String tokenSubject = extractUsername(token);
        return tokenSubject.equals(subject) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token is expired");
        }
    }



    private Key getSignKey() {
        var bytes = Decoders.BASE64.decode(properties.getToken().getSecret());
        return Keys.hmacShaKeyFor(bytes);
    }

}
