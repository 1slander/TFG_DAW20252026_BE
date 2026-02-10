package com.tfgbe.security;

import java.io.IOException;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                   String path = request.getServletPath();

    // RUTAS PÚBLICAS
    if (path.equals("/login")
        || path.equals("/admin/login")
        || path.equals("/signup")
        || path.startsWith("/swagger-ui")) {

        filterChain.doFilter(request, response);
        return;
    }
        

                String authHeader = request.getHeader("Authorization");
                String token = null;

                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    token=authHeader.substring(7);
                }

                if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    try{
                    String username =jwtUtil.getUsernameFromToken(token);
                    String role=jwtUtil.getRoleFromToken(token);

                    // creando la info para generar el contexto y que spring security sepa QUIEN
                    // hace la peticicion y QUE PUEDE HACER
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                        null,
                    Collections.singletonList(new SimpleGrantedAuthority(role))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (TokenExpiredException e){
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                   response.getWriter().write("Tu sesion ha caducado, debes volver a hace el login");
                   return;
                }

            }
            // Pasamos al siguiente filtro, si no hay miraremos en el securityConfig
            filterChain.doFilter(request, response);



    }
}
