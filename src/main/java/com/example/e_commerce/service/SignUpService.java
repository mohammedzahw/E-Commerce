package com.example.e_commerce.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.MediaDto;
import com.example.e_commerce.dto.UserSignUpRequest;
import com.example.e_commerce.dto.VendorSignUpRequest;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.mapper.UserMapper;
import com.example.e_commerce.mapper.VendorMapper;
import com.example.e_commerce.model.User;
import com.example.e_commerce.model.UserImage;
import com.example.e_commerce.model.Vendor;
import com.example.e_commerce.model.VendorImage;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.repository.VendorRepository;

import jakarta.mail.MessagingException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SignUpService {

    private EmailService emailService;
    private UserMapper userMapper;
    private VendorMapper vendorMapper;

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private VendorRepository vendorRepository;
    private TokenUtil tokenUtil;
    private MediaService mediaService;

    public SignUpService(EmailService emailService, UserMapper userMapper,
            PasswordEncoder passwordEncoder, TokenUtil tokenUtil,
            MediaService mediaService,
            UserRepository userRepository,
            VendorMapper vendorMapper,
            VendorRepository vendorRepository) {
        this.emailService = emailService;
        this.vendorMapper = vendorMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.tokenUtil = tokenUtil;
        this.mediaService = mediaService;
    }

    /******************************************************************************************************************/

    public User saveUser(UserSignUpRequest request)
            throws MessagingException, IOException, SQLException {
        try {

            User user = userMapper.toEntity(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (request.getImage() != null) {
                MediaDto imageDto = mediaService.uploadImage(request.getImage(), null, "user" + user.getId());
                UserImage userImage = new UserImage(imageDto);
                user.setUserImage(userImage);
            }
            userRepository.save(user);

            return user;

        } catch (CustomException e) {

            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    /******************************************************************************************************************/

    public Vendor saveVendor(VendorSignUpRequest request)
            throws MessagingException, IOException, SQLException {
        try {

            Vendor vendor = vendorMapper.toEntity(request);
            vendor.setPassword(passwordEncoder.encode(vendor.getPassword()));

            if (request.getImage() != null) {
                MediaDto imageDto = mediaService.uploadImage(request.getImage(), null, "vendor" + vendor.getId());
                VendorImage vendorImage = new VendorImage(imageDto);
                vendor.setVendorImage(vendorImage);
            }

            vendorRepository.save(vendor);

            return vendor;

        } catch (CustomException e) {

            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    /******************************************************************************************************************/

    public void verifyEmail(String token) throws SQLException, IOException {

        if (tokenUtil.isTokenExpired(token)) {
            throw new CustomException("Token is expired", HttpStatus.BAD_REQUEST);
        }

        String role = tokenUtil.getRole(token);

        if (role.equals("ROLE_USER")) {
            User User = userRepository.findByEmail(tokenUtil.getEmail(token))
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

            User.setActive(true);
            userRepository.save(User);
        } else if (role.equals("ROLE_VENDOR")) {
            Vendor vendor = vendorRepository.findByEmail(tokenUtil.getEmail(token))
                    .orElseThrow(() -> new CustomException("Vendor not found", HttpStatus.NOT_FOUND));

            vendor.setActive(true);
            vendorRepository.save(vendor);
        }

        else {
            throw new CustomException("Invalid token", HttpStatus.BAD_REQUEST);
        }

    }

    /******************************************************************************************************************/
    public void sendRegistrationVerificationCode(String email, HttpServletRequest request,
            String verficationToken) {
        try {
            String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                    + "/verifyEmail/" + verficationToken;

            System.out.println("url : " + url);
            String subject = "Email Verification";
            String senderName = "User Registration Portal Service";
            String content = "<p> Hi, " + email + ", </p>" +
                    "<p>Thank you for registering with us," + "" +
                    "Please, follow the link below to complete your registration.</p>" +
                    "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                    "<p> Thank you <br> Users Registration Portal Service";
            emailService.sendEmail(email, content, subject, senderName);

        } catch (Exception e) {
            throw new CustomException("Error while sending Email", HttpStatus.BAD_REQUEST);
        }
    }

    /**************************************************************************************************************/
}
