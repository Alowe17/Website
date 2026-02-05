package com.example.Web_Service.controller.auth;

import com.example.Web_Service.config.JwtUtil;
import com.example.Web_Service.model.dto.AuthResponseDto;
import com.example.Web_Service.model.dto.LoginRequestDto;
import com.example.Web_Service.model.entity.RefreshToken;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.RefreshTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtutil;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController (JwtUtil jwtutil, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.jwtutil = jwtutil;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtutil.generateToken(userDetails.getUsername());
        RefreshToken refreshToken = refreshTokenService.create(userDetails.getUsername());

        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(30 * 24 * 60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new AuthResponseDto(accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh (@CookieValue("refreshToken") String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validate(refreshTokenValue);
        User user = refreshToken.getUser();
        String newAccessToken = jwtutil.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponseDto(newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout (@CookieValue("refreshToken") String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validate(refreshTokenValue);

        ResponseCookie cookie = ResponseCookie
                .from("refreshToken", "")
                .path("/api/auth")
                .maxAge(0)
                .build();

        refreshTokenService.revoke(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}