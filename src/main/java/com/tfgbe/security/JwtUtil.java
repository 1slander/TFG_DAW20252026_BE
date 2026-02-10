package com.tfgbe.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private  String SECRET;
     @Value("${jwt.expiration}")
    private  long EXPIRATION_DATE;

    public String generateToken(String username, String role){
        System.out.println("Secret: " + SECRET);
        System.out.println("Expiration: " + EXPIRATION_DATE);

       String token=JWT.create()
            .withSubject(username)
            .withClaim("role",role)
            .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_DATE))
            .sign(Algorithm.HMAC256(SECRET));
        System.out.println("Secret: " + SECRET);
    System.out.println("Token generado: " + token);
        return token;
    }

    // Validacion Token
    public DecodedJWT validateToken(String token){
        try{

            return JWT.require(Algorithm.HMAC256(SECRET))
                        .build()
                        .verify(token);
             

        }  catch (TokenExpiredException e) {
            
            throw e;
    
        }catch(JWTVerificationException e) {
            return null;

        }
    }

    // Sacamos el usuario
    public String getUsernameFromToken(String token){
        DecodedJWT decodedJWT = validateToken(token);

        return decodedJWT!=null ? decodedJWT.getSubject() : null;
    }

    // Sacamos el role para usar en los filtros
    public String getRoleFromToken(String token){
        DecodedJWT decodedJWT = validateToken(token);

        return decodedJWT!=null ? decodedJWT.getClaim("role").asString() : null;
    }
}
