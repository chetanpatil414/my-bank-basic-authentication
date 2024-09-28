package com.mybank.controller;

import com.mybank.model.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    public final AuthenticationManager authenticationManager;

    @PostMapping("/logIn")
    public ResponseEntity userLogin(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            UsernamePasswordAuthenticationToken unuthenticated = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.userName(), loginRequestDTO.Password());
            Authentication authenticate = authenticationManager.authenticate(unuthenticated);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return ResponseEntity.ok("Log In Successful");
        }catch (AuthenticationException ex){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Username and password");
        }

    }
}
