package com.example.Web_Service.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/promo-code/pages")
public class RewardPageController {
    @GetMapping("/{url}")
    public String rewardPage (@PathVariable String url) {
        return "/promo-code/birthday";
    }
}