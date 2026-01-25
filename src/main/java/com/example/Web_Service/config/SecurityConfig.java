package com.example.Web_Service.config;

import com.example.Web_Service.users.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toStaticResources;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig (CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/login", "/access-denied", "/register", "/in-development", "/register-account").permitAll()
                .requestMatchers("/admin/**", "/story/**", "/api/**").hasAnyRole("ADMINISTRATOR", "GOD")
                .requestMatchers("/profile", "/index", "/support/**").authenticated()
        )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .loginProcessingUrl("/perform_login")
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}