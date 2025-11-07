package com.example.billingsoftware.BillingSoftwareBackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claiams = new HashMap<>(); //what are claims - claims are the information about the user that we want to include in the token
        return createToken(claiams, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claiams, String subject){
        return Jwts.builder()
                .setClaims(claiams)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 10)) //10 hours expiration
                .signWith(SignatureAlgorithm.HS256, (SECRET_KEY))
                .compact();
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject); // extracting the subject from the token which is the username
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) { // generic method to extract any claim from the token
        final Claims claims = extractAllClaims(token); // extracting all the claims from the token
        return claimsResolver.apply(claims); // applying the function to the claims to get the desired claim
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // extracting the expiration date from the token
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey((SECRET_KEY)).parseClaimsJws(token).getBody(); // parsing the token to get the claims
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // checking if the token is expired
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username. equals(userDetails.getUsername()) && !isTokenExpired(token)); // validating the token by checking the username and expiration
    }
}
