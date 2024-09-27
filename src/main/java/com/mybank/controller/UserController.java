package com.mybank.controller;

import com.mybank.config.MyBankUserDetailService;
import com.mybank.model.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    MyBankUserDetailService myBankUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;


   private final AuthenticationManager authenticationManager;

    @PostMapping("/logIn")
    public ResponseEntity logIn(@RequestBody LoginRequestDTO details){
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(details.userName(), details.Password());
        Authentication authenticate = authenticationManager.authenticate(unauthenticated);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity.ok("Login Successful");
    }

}
