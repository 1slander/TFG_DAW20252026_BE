package com.tfgbe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tfgbe.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                    // Primer Filtro
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/login","/signup").permitAll()
                    .requestMatchers("/admin/login").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/employees/create","/employees/delete/**","/employees/update/**").hasAnyAuthority("ROLE_ADMIN","ROLE_OWNER","ROLE_MANAGER")
                    .requestMatchers(HttpMethod.GET,"/employees/**").hasAnyAuthority("ROLE_ADMIN","ROLE_OWNER","ROLE_MANAGER","ROLE_ASSISTANT_MANAGER","ROLE_TEAM_LEADER","ROLE_EMPLOYEE")
                    .requestMatchers(HttpMethod.POST, "/restaurant").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER")
                    .requestMatchers(HttpMethod.GET, "/restaurant/my").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER")
                    .requestMatchers(HttpMethod.GET, "/restaurant/owner/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.GET, "/restaurant/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/restaurant/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER")
                    .requestMatchers(HttpMethod.DELETE, "/restaurant/**").hasAuthority("ROLE_ADMIN")
                    //.requestMatchers(HttpMethod.GET,"/admin/**").authenticated() EJEMPLo
                    .anyRequest().authenticated()
                )
                .exceptionHandling((ex -> ex.authenticationEntryPoint((req,res,authException)->{
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado");
                })
                .accessDeniedHandler((req,res,accessDeniedException)->{
                    res.sendError(HttpServletResponse.SC_FORBIDDEN,"No tienes permisos");
                })
            ))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

                return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
