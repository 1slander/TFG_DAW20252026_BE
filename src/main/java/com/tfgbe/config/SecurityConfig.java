package com.tfgbe.config;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tfgbe.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // 👇 ACTIVAMOS CORS AQUÍ
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                .csrf(AbstractHttpConfigurer::disable) 
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                    // Permitir todos los preflights de CORS
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    // Swagger
                    .requestMatchers("/swagger-ui/**").permitAll()

                    // Auth
                    .requestMatchers("/login","/signup").permitAll()
                    .requestMatchers("/admin/login").permitAll()

                    // Admin
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                    // Employees
                    .requestMatchers(HttpMethod.GET, "/employees")
                        .hasAuthority("ROLE_ADMIN")

                    .requestMatchers(HttpMethod.GET, "/employees/restaurant")
                        .hasAnyAuthority(
                            "ROLE_ADMIN",
                            "ROLE_OWNER",
                            "ROLE_MANAGER",
                            "ROLE_ASSISTANT_MANAGER",
                            "ROLE_TEAM_LEADER",
                            "ROLE_EMPLOYEE"
                        )

                    .requestMatchers(HttpMethod.GET, "/employees/*")
                        .hasAnyAuthority(
                            "ROLE_ADMIN",
                            "ROLE_OWNER",
                            "ROLE_MANAGER",
                            "ROLE_ASSISTANT_MANAGER",
                            "ROLE_TEAM_LEADER"
                        )

                    .requestMatchers("/employees/create",
                                     "/employees/delete/**",
                                     "/employees/update/**")
                        .hasAnyAuthority("ROLE_ADMIN","ROLE_OWNER","ROLE_MANAGER")

                    // Restaurant
                    .requestMatchers(HttpMethod.POST, "/restaurant")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER")

                    .requestMatchers(HttpMethod.GET, "/restaurant/my")
                        .hasAnyAuthority(
                            "ROLE_ADMIN",
                            "ROLE_OWNER",
                            "ROLE_MANAGER",
                            "ROLE_ASSISTANT_MANAGER",
                            "ROLE_TEAM_LEADER",
                            "ROLE_EMPLOYEE"
                        )

                    .requestMatchers(HttpMethod.GET, "/restaurant/owner/**")
                        .hasAuthority("ROLE_ADMIN")

                    .requestMatchers(HttpMethod.GET, "/restaurant/**")
                        .hasAuthority("ROLE_ADMIN")

                    .requestMatchers(HttpMethod.PUT, "/restaurant/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER")

                    .requestMatchers(HttpMethod.DELETE, "/restaurant/**")
                        .hasAuthority("ROLE_ADMIN")

                    // Shifts
                    .requestMatchers(HttpMethod.PUT, "/employees/*/shift/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_MANAGER", "ROLE_ASSISTANT_MANAGER")
                    
                    .requestMatchers(HttpMethod.GET, "/shifts", "/shifts/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_MANAGER", "ROLE_ASSISTANT_MANAGER", "ROLE_TEAM_LEADER", "ROLE_EMPLOYEE")

                    // Todas las demás operaciones de /shifts (POST, PUT, DELETE) requieren roles de gestión
                    .requestMatchers("/shifts/delete/**", "/shifts/update/**", "/shifts")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_OWNER", "ROLE_MANAGER", "ROLE_ASSISTANT_MANAGER")

                    .requestMatchers("/table-assignment/**").authenticated()
                    .requestMatchers("/tables/**").authenticated()

                    .requestMatchers(HttpMethod.GET, "/employees/me")
    .authenticated()

.requestMatchers(HttpMethod.PUT, "/employees/me/password")
    .authenticated()
    
                    .anyRequest().authenticated()
                )

                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req,res,authException) ->
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autenticado")
                    )
                    .accessDeniedHandler((req,res,accessDeniedException) ->
                        res.sendError(HttpServletResponse.SC_FORBIDDEN,"No tienes permisos")
                    )
                )

                .addFilterBefore(jwtAuthenticationFilter, 
                        UsernamePasswordAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    // 👇 CONFIGURACIÓN CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:4200",      // Angular local
                "https://tu-dominio.com"      // Producción
        ));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}