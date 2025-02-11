package com.example.e_commerce.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("null")
@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${auth.header}")
    private String TOKEN_HEADER;

    private final UserDetailsServiceImpl userDetailsService;
    private final VendorDetailsServiceImpl vendorDetailsService;
    private final AdminDetailsServiceImpl adminDetailsService;
    private final TokenUtil tokenUtil;

    public AuthFilter(UserDetailsServiceImpl userDetailsService, AdminDetailsServiceImpl adminDetailsService,
            VendorDetailsServiceImpl vendorDetailsService,
            TokenUtil tokenUtil) {
        this.adminDetailsService = adminDetailsService;
        this.userDetailsService = userDetailsService;
        this.vendorDetailsService = vendorDetailsService; // Initialize it here
        this.tokenUtil = tokenUtil;
    }

    /***************************************************************************************************** */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/public/")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String header = request.getHeader(TOKEN_HEADER);

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (header != null && securityContext.getAuthentication() == null) {

            String token = header.substring("Bearer ".length());
            String email = tokenUtil.getEmail(token);
            String role = tokenUtil.getRole(token);
            // System.out.println("email : " + email);
            // System.out.println("role : " + role);

            if (email != null) {
                try {

                    UserDetails userDetails = null;

                    if ("ROLE_USER".equals(role)) {
                        userDetails = userDetailsService.loadUserByUsername(email);
                    } else if ("ROLE_VENDOR".equals(role)) {
                        userDetails = vendorDetailsService.loadUserByUsername(email);
                    } else if (role.equals("ROLE_ADMIN")) {
                        userDetails = adminDetailsService.loadUserByUsername(email);
                    }
                    if (userDetails != null) {
                        if (tokenUtil.isTokenValid(token, userDetails)) {
                            // System.out.println("userDetails : " + userDetails);

                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } else {
                        log.error("User not found");
                    }
                } catch (Exception e) {
                    log.error("Error setting security context", e);
                }

            }
        }

        filterChain.doFilter(request, response);
    }

}
