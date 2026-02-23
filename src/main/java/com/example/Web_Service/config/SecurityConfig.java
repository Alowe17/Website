package com.example.Web_Service.config;

import com.example.Web_Service.users.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTAccessDeniedHandler jwtAuthenticationFailureHandler;
    private final JwtFilter jwtFilter;

    public SecurityConfig (CustomUserDetailsService customUserDetailsService, JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, JWTAccessDeniedHandler jwtAuthenticationFailureHandler, JwtFilter jwtFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    config.setAllowedHeaders(Arrays.asList("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/login", "/register", "/in-development", "/api/auth/**", "/api/register", "/index", "/profile", "/support/**", "/admin", "/story",
                                "/error", "/admin/change-password/**", "/admin/update-user/**", "/admin/create-new/npc", "/api/admin/info-npc/**", "/admin/update-npc/**",
                                "/admin/create-new/dish", "/admin/update-dish/**", "/admin/create-new/product", "/admin/update-product/**", "/admin/support-answer/**", "/api/admin/load-message/**").permitAll()
                        .requestMatchers("/api/profile/**", "/api/update-user-data/**", "/api/index/chapters-list", "/api/index/characters-list", "/api/support/message-sent", "/api/old-message-support").authenticated()
                        .requestMatchers("/api/admin", "/api/admin-list/**", "/api/admin/change-password", "/api/admin/password/**", "/api/admin/info-user/**",
                                "/api/admin/user-update-data/**", "/api/admin/create-new/npc", "/api/admin/update-npc/**", "/api/admin/create-new/dish",
                                "/api/admin/info-dish/**", "/api/admin/update-dish/**", "/api/admin/create-new/product", "/api/admin/update-product/**", "/api/admin/info-product/**",
                                "/api/admin/rejected-message/**", "/api/admin/reply-message/**").hasRole("ADMINISTRATOR")
                        .anyRequest().denyAll()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAuthenticationFailureHandler)
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .userDetailsService(customUserDetailsService);

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}