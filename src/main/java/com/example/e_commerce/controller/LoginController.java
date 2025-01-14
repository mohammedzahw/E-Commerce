package com.example.e_commerce.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.e_commerce.Response;
import com.example.e_commerce.Validator;
import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.ChangePasswordRequest;
import com.example.e_commerce.dto.LoginRequest;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.IUser;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.repository.VendorRepository;
import com.example.e_commerce.service.LoginService;
import com.example.e_commerce.service.SignUpService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController

public class LoginController {

    private SignUpService signUpService;

    private UserRepository userRepository;
    private VendorRepository vendorRepository;

    private TokenUtil tokenUtil;

    private LoginService loginService;

    public LoginController(SignUpService signUpService,

            VendorRepository vendorRepository, UserRepository userRepository, TokenUtil tokenUtil,
            LoginService loginService) {
        this.vendorRepository = vendorRepository;
        this.signUpService = signUpService;
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        this.loginService = loginService;
    }

    /***************************************************************************************************************/
    @PostMapping("/login")
    public Response loginWithEmailAndPassword(@RequestBody @Valid LoginRequest loginRequest, BindingResult result,
            HttpServletRequest request)
            throws MessagingException, SQLException, IOException {
        try {
            if (result.hasErrors()) {
                return Validator.validate(result);
            }
            return new Response(HttpStatus.OK, loginService.verifyLogin(loginRequest, request), "Login Successfull");
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    /****************************************************************************************************************/

    @PostMapping("/forget-password")

    public Response enterEmailToChangePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult result,
            HttpServletRequest request)
            throws MessagingException, SQLException, IOException {

        if (result.hasErrors()) {
            return Validator.validate(result);
        }

        IUser user = null;
        if (changePasswordRequest.getRole().equals("ROLE_USER")) {
            user = userRepository.findByEmail(changePasswordRequest.getEmail()).orElseThrow(
                    () -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        } else if (changePasswordRequest.getRole().equals("ROLE_VENDOR")) {
            user = vendorRepository.findByEmail(changePasswordRequest.getEmail()).orElseThrow(
                    () -> new CustomException("Vendor not found", HttpStatus.NOT_FOUND));
        } else {
            throw new CustomException("Invalid token", HttpStatus.BAD_REQUEST);
        }

        if (!user.isActive()) {
            signUpService.sendRegistrationVerificationCode(changePasswordRequest.getEmail(),
                    request,
                    tokenUtil.generateToken(changePasswordRequest.getEmail(), user.getId(),
                            900, changePasswordRequest.getRole()));

            return new Response(HttpStatus.OK, null,
                    "This email is not verified, Activation link sent to your email");
        }
        loginService.sendResetpasswordEmail(changePasswordRequest.getEmail(),
                request,
                tokenUtil.generateToken(changePasswordRequest.getEmail() + "," +
                        changePasswordRequest.getPassword(),
                        user.getId(), 900, changePasswordRequest.getRole()));

        return new Response(HttpStatus.OK, null, "Password reset link sent to your email");
    }

    // /*************************************************************************************************************/
    @GetMapping("/check-token/{token}")
    public ResponseEntity<?> savePassword(@PathVariable("token") String token,
            HttpServletResponse response)
            throws SQLException, IOException, MessagingException {
        if (tokenUtil.isTokenExpired(token)) {
            throw new CustomException("Token is expired", HttpStatus.BAD_REQUEST);
        }
        String email = tokenUtil.getEmail(token).split(",")[0];

        String password = tokenUtil.getEmail(token).split(",")[1];
        String role = tokenUtil.getRole(token);
        IUser user = null;
        if (role.equals("ROLE_USER")) {
            user = userRepository.findByEmail(email).orElseThrow(
                    () -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        } else if (role.equals("ROLE_VENDOR")) {
            user = vendorRepository.findByEmail(email).orElseThrow(
                    () -> new CustomException("Vendor not found", HttpStatus.NOT_FOUND));
        } else {
            throw new CustomException("Invalid token", HttpStatus.BAD_REQUEST);
        }
        loginService.savePassword(user, password);

        return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
    }

}
