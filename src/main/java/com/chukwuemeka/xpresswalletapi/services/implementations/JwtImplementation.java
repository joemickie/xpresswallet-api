package com.chukwuemeka.xpresswalletapi.services.implementations;

import com.chukwuemeka.xpresswalletapi.services.JwtService;
import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtImplementation implements JwtService {
    @Value("${security.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Override
    public String extractEmailAddressFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> extract) {
        return extract.apply(getAllClaims(token));
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new XpressException("Session has expired. Please login again");
        } catch (UnsupportedJwtException | SignatureException | IllegalArgumentException | MalformedJwtException e) {
            throw new XpressException("An error occurred while reading your session. Please login again" + e.getMessage());
        }
    }
    @Override
    public String generateJwtToken(Map<String, Object> claims, String emailAddress) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        long EXPIRATION_LIFECYCLE = 86400000L;
        Instant expiration = issuedAt.plus(EXPIRATION_LIFECYCLE, ChronoUnit.MILLIS);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuer("Xpress")
                .setSubject(emailAddress)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(signingKey())
                .compact();
    }

    private Key signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET_KEY));
    }

    @Override
    public Boolean isExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }
}
