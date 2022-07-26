package com.example.news_app.service;


import com.example.news_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userResository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userResository.findByPhone(username).orElse(null);
    }
}
