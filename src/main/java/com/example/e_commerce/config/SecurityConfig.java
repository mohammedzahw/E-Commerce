package com.example.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.e_commerce.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@SuppressWarnings("null")
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final VendorDetailsServiceImpl vendorDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, VendorDetailsServiceImpl vendorDetailsService) {
        this.userDetailsService = userDetailsService;
        this.vendorDetailsService = vendorDetailsService;
    }

    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http) throws
    // Exception {
    // AuthenticationManagerBuilder authenticationManagerBuilder = http
    // .getSharedObject(AuthenticationManagerBuilder.class);
    // authenticationManagerBuilder
    // .userDetailsService(userDetailsService)
    // .passwordEncoder(passwordEncoder());
    // authenticationManagerBuilder
    // .userDetailsService(vendorDetailsService)
    // .passwordEncoder(passwordEncoder());
    // return authenticationManagerBuilder.build();
    // }

    /*****************************************************************************************************/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /***************************************************************************************************** */
    // @Bean
    // public WebMvcConfigurer corsConfigurer() {
    // return new WebMvcConfigurer() {
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**")
    // .allowedOrigins("http://localhost:4200")
    // .allowedOriginPatterns("*")
    // .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    // .allowedHeaders("*")
    // .allowCredentials(true);
    // }
    // };
    // }

    /***************************************************************************************************** */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**")
                .permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN", "VENDOR"));
        // .addFilterAfter(authFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Access Denied");
                }));
        return http.build();
    }

    /***************************************************************************************************** */

}
