package com.example.Web_Service.controller.api;

import com.example.Web_Service.model.dto.promo.request.PromoCodeUseDto;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.service.PromoCodeService;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promo-code")
public class PromoCodeApiController {
    private final PromoCodeService promoCodeService;

    public PromoCodeApiController (PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/code")
    public ResponseEntity<?> usePromoCode (@RequestBody PromoCodeUseDto promoCodeuseDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();

        return promoCodeService.usePromoCode(promoCodeuseDto, user);
    }
}