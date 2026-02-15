package com.example.Web_Service.controller.page;

import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import com.example.Web_Service.model.enums.Status;
import com.example.Web_Service.repository.MessageSupportRepository;
import com.example.Web_Service.users.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class SupportController {
    @GetMapping("/support")
    public String support() {
        return "support";
    }
}