package com.mybank.config;

import com.mybank.entity.Customer;
import com.mybank.repository.FetchUserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBankUserDetailService implements UserDetailsService {


   private final FetchUserDetailRepository fetchUserDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Customer resp = fetchUserDetailRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Detail not found for the User: " + username));
        List<SimpleGrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(resp.getRole()));
        User user = new User(resp.getEmail(),resp.getPwd(),grantedAuthorities);
        return user;
    }
}
