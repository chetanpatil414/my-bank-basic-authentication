package com.mybank.controller;

import com.mybank.model.LoginRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleClass {

    @PostMapping("/user")
    public String data(@RequestBody LoginRequestDTO loginRequestDTO){
        System.out.println(loginRequestDTO.userName());
        return loginRequestDTO.userName();
    }
}
