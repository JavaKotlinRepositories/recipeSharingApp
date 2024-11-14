package com.example.recipeApp.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    // Replace this with your own secret key
    private   String SECRET_KEY = "your-secret-key";
    private long expiryTime= 1000 *60 *60 *24;
    private String issuer="vishalgandla.com";

    public  String generateToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() +expiryTime )) // Token valid for 1 hour
                .sign(algorithm);

        return token;
    }

    public boolean isTokenValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Date expiration = jwt.getExpiresAt();
            if (expiration != null && expiration.before(new Date())) {
                return false;
            }
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public  String getTokenSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer) // Set expected issuer
                    .build();

            DecodedJWT jwt = verifier.verify(token);

            return jwt.getSubject();

        } catch (JWTVerificationException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return null;
        }
    }

}
