package com.mybank.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboardDetails(){
        return "This is my Dashboard";
    }
}
