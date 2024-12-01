package edu.miu.apsd.eWallet.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.key}")
    public String secret;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .issuedAt(new Date())
                .issuer("edu.miu.apsd.eWallet")
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .subject(userDetails.getUsername())
                .claim("authorities", populateAuthorities(userDetails))
                .signWith(signInKey())
                .compact();
    }

    private String populateAuthorities(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(","));
    }

    private SecretKey signInKey() {
        // Hash based message authentication code
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

}
