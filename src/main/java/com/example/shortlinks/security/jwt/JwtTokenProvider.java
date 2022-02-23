package com.example.shortlinks.security.jwt;

import com.example.shortlinks.exceptions.MyAuthenticationException;
import com.example.shortlinks.model.User;
import com.example.shortlinks.security.SecurityUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private SecurityUserDetailsService userDetailsService;

    public JwtTokenProvider(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(User user){
        log.info("creating jwt token for user with username: {}", user.getUsername());
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRoles());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token){
        log.info("validating jwt token: {}", token);

        String[] chunks = token.split("\\.");
        if(chunks.length != 3){
            throw new MyAuthenticationException("cannot identify token");
        }
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(algorithm,
                new SecretKeySpec(secret.getBytes(), algorithm.getJcaName()));

        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new MyAuthenticationException("cannot identify token");
        }

        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return claims.getBody().getExpiration().before(new Date());
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authentication");
        if(token != null){
            return token;
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

}
