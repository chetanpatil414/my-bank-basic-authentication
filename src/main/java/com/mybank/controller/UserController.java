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
public class UserController {

   private final AuthenticationManager authenticationManager;

    @PostMapping("/logIn")
    public ResponseEntity logIn(@RequestBody LoginRequestDTO details){
        ResponseEntity result;
        try {
            UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(details.userName(), details.Password());
            Authentication authenticate = authenticationManager.authenticate(unauthenticated);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            result = ResponseEntity.ok("Login Successful");
        }catch (AuthenticationException ex){
            result = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return result;
    }

}
