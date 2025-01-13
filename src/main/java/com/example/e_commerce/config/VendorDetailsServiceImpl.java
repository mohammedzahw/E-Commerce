package com.example.e_commerce.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.e_commerce.repository.VendorRepository;

@Service
public class VendorDetailsServiceImpl implements UserDetailsService {
    private final VendorRepository vendorRepository;

    public VendorDetailsServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return vendorRepository.findByEmail(username)
                .map(VendorUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Vendor not found"));
    }
}