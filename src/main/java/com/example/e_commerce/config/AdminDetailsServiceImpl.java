package com.example.e_commerce.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.e_commerce.repository.AdminRepository;

public class AdminDetailsServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;

    public AdminDetailsServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByName(username)
                .map(AdminUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}