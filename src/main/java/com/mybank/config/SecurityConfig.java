package com.mybank.config;

import com.mybank.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.mybank.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        CsrfTokenRequestAttributeHandler requestHandle = new CsrfTokenRequestAttributeHandler();
        requestHandle.setCsrfRequestAttributeName("_csrf");
        httpSecurity.securityContext(contextConfig-> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsCustomizer->corsCustomizer.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000/"));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setMaxAge(3600L);

                    return corsConfiguration;
                })).csrf(csrf->csrf.csrfTokenRequestHandler(requestHandle)
                .ignoringRequestMatchers("/", "/user","/logIn")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);



        httpSecurity.authorizeHttpRequests(req->req.requestMatchers("/dashboard").authenticated()
                .requestMatchers("/","/user","/logIn").permitAll());
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));



        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
        MyBankUsernamePasswordAuthenticationProvider authenticationProvider = new MyBankUsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }


//    @Bean
//    CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }


}
