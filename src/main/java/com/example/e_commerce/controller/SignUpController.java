package com.example.e_commerce.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.Response;
import com.example.e_commerce.Validator;
import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.UserSignUpRequest;
import com.example.e_commerce.dto.VendorSignUpRequest;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.User;
import com.example.e_commerce.model.Vendor;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.repository.VendorRepository;
import com.example.e_commerce.service.SignUpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class SignUpController {

    private SignUpService signUpService;

    private UserRepository userRepository;
    private VendorRepository vendorRepository;

    private TokenUtil tokenUtil;

    public SignUpController(VendorRepository vendorRepository, SignUpService signUpService,
            UserRepository userRepository, TokenUtil tokenUtil) {
        this.signUpService = signUpService;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.tokenUtil = tokenUtil;
    }

    /******************************************************************************************************************/

    @PostMapping("/signup/vendor")
    public Response vendorSignup(@Valid @ModelAttribute VendorSignUpRequest signUpRequest, BindingResult result,
            HttpServletRequest request) {
        try {

            if (result.hasErrors()) {
                return Validator.validate(result);
            }

            Vendor vendor = vendorRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
            if (vendor != null) {
                return new Response(HttpStatus.BAD_REQUEST, null, "Email already exists ,Please login");
            }

            vendor = signUpService.saveVendor(signUpRequest);

            // send email
            String token = tokenUtil.generateToken(vendor.getEmail(),
                    vendor.getId(), 1000, "ROLE_VENDOR");
            signUpService.sendRegistrationVerificationCode(signUpRequest.getEmail(),
                    request,
                    token);

            return new Response(HttpStatus.OK, null,
                    "Vendor signup successfully and verification code sent to your email");

        } catch (CustomException e) {
            return new Response(HttpStatus.BAD_REQUEST, null, e.getMessage());

        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }

    }

    /**************************************************************************** */
    @PostMapping("/signup/user")
    public Response customerSignup(@Valid @ModelAttribute UserSignUpRequest signUpRequest, BindingResult result,
            HttpServletRequest request) {

        try {

            if (result.hasErrors()) {
                return Validator.validate(result);
            }
            User user = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
            if (user != null) {
                return new Response(HttpStatus.BAD_REQUEST, null, "Email already exists ,Please login");
            }

            user = signUpService.saveUser(signUpRequest);

            // send email
            String token = tokenUtil.generateToken(user.getEmail(),
                    user.getId(), 1000, "ROLE_USER");
            signUpService.sendRegistrationVerificationCode(signUpRequest.getEmail(),
                    request,
                    token);

            return new Response(HttpStatus.OK, null,
                    "User signup successfully and verification code sent to your email");

        } catch (CustomException e) {
            return new Response(HttpStatus.BAD_REQUEST, null, e.getMessage());

        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }

    }

    /******************************************************************************************** */
    @GetMapping("/verifyEmail/{token}")
    public ResponseEntity<?> verifyEmail(@PathVariable("token") String verficationToken,
            HttpServletResponse response)
            throws SQLException, IOException {

        signUpService.verifyEmail(verficationToken);

        return new ResponseEntity<>("Account is verified, you can login now", HttpStatus.OK);
    }
    /******************************************************************************************** */

}
