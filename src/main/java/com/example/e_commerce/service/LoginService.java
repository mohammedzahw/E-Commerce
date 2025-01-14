package com.example.e_commerce.service;

import java.io.IOException;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.LoginRequest;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.IUser;
import com.example.e_commerce.model.User;
import com.example.e_commerce.model.Vendor;
import com.example.e_commerce.repository.AdminRepository;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.repository.VendorRepository;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class LoginService {

    private UserRepository userRepository;
    private VendorRepository vendorRepository;
    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    private TokenUtil tokenUtil;

    private SignUpService signUpService;

    private EmailService emailService;

    public LoginService(PasswordEncoder passwordEncoder, AdminRepository adminRepository, TokenUtil tokenUtil,
            VendorRepository vendorRepository,
            UserRepository userRepository,
            SignUpService signUpService, EmailService emailService) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.vendorRepository = vendorRepository;
        this.signUpService = signUpService;
        this.emailService = emailService;
    }

    /********************************************************************************* */
    public String verifyLogin(LoginRequest loginRequest, HttpServletRequest request)
            throws SQLException, IOException {
        IUser user = null;
        if (loginRequest.getRole().equals("ROLE_USER")) {
            user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        } else if (loginRequest.getRole().equals("ROLE_VENDOR")) {
            user = vendorRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        } else if (loginRequest.getRole().equals("ROLE_ADMIN")) {
            user = adminRepository.findByName(loginRequest.getEmail())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        }

        else {
            throw new CustomException("Invalid role", HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new CustomException("Wrong Password!", HttpStatus.BAD_REQUEST);
        }
        if (!user.isActive()) {
            String token = tokenUtil.generateToken(loginRequest.getEmail(), user.getId(), 1000, "ROLE_USER");
            signUpService.sendRegistrationVerificationCode(loginRequest.getEmail(),
                    request,
                    token);
            throw new CustomException("Please verify your email first, link sent to your email",
                    HttpStatus.BAD_REQUEST);
        }
        String token = tokenUtil.generateToken(loginRequest.getEmail(), user.getId(), 3000000, loginRequest.getRole());

        return token;

    }

    /********************************************************************************************************************/
    public void savePassword(IUser user, String password)
            throws SQLException, IOException, MessagingException {
        if (user instanceof User) {
            ((User) user).setPassword(passwordEncoder.encode(password));
            userRepository.save((User) user);
        } else if (user instanceof Vendor) {
            ((Vendor) user).setPassword(passwordEncoder.encode(password));
            vendorRepository.save((Vendor) user);
        } else {
            throw new CustomException("Invalid user", HttpStatus.BAD_REQUEST);
        }

    }

    /********************************************************************************************************************/

    public void sendResetpasswordEmail(String email, HttpServletRequest request,
            String token) {

        try {
            String url = "http://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath()
                    + "/check-token/" + token;
            System.out.println("url : " + url);
            String subject = "Reset Password Verification";
            String senderName = "User Registration Portal Service";
            String content = "<p> Hi, " + email + ", </p>" +
                    "<p>Thank you for registering with us," + "" +
                    "Please, follow the link below to complete your registration.</p>" +
                    "<a href=\"" + url + "\">Reset password</a>" +
                    "<p> Thank you <br> Reset Password Portal Service";
            emailService.sendEmail(email, content, subject, senderName);

            // return new ResponseEntity<>("Please, check your email to reset your
            // password", HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Error while sending email",
                    HttpStatus.BAD_REQUEST);

        }
    }

    /********************************************************************************************************************/

}
