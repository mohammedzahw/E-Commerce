package com.example.e_commerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.e_commerce.config.TokenUtil;
import com.example.e_commerce.dto.AddAddressRequest;
import com.example.e_commerce.dto.MediaDto;
import com.example.e_commerce.dto.UpdateProfileImage;
import com.example.e_commerce.exception.CustomException;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.Phone;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.User;
import com.example.e_commerce.model.UserImage;
import com.example.e_commerce.repository.AddressRepository;
import com.example.e_commerce.repository.PhoneRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TokenUtil tokenUtil;
    private AddressRepository addressRepository;
    private MediaService mediaService;
    private PhoneRepository phoneRepository;

    public UserService(UserRepository userRepository, PhoneRepository phoneRepository,
            AddressRepository addressRepository, MediaService mediaService,
            ProductRepository productRepository,
            TokenUtil tokenUtil) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.mediaService = mediaService;
        this.tokenUtil = tokenUtil;
        this.productRepository = productRepository;
    }

    /*********************************************************************************************** */
    @Transactional
    public void addToCart(Integer productId) {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (!productRepository.existsById(productId)) {
            throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
        }
        userRepository.addToCart(user.getId(), productId);

    }

    /*********************************************************************************************** */
    @Transactional
    public void removeFromCart(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        userRepository.removeFromCart(user.getId(), productId);
    }

    /*********************************************************************************************** */
    @Transactional
    public void clearCart() {
        userRepository.clearCart(tokenUtil.getUserId());
    }

    /*********************************************************************************************** */
    @Transactional
    public void addToWishList(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (productRepository.existsById(productId)) {
            throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
        }
        userRepository.addToWishList(user.getId(), productId);
    }

    /*********************************************************************************************** */
    @Transactional
    public void removeFromWishList(Integer productId) {
        User user = userRepository.findById(productId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        userRepository.removeFromWishList(user.getId(), productId);
    }

    /*********************************************************************************************** */

    public List<Product> getCart() {
        return userRepository.getCart(tokenUtil.getUserId());
    }

    /*********************************************************************************************** */
    public List<Product> getWishList() {
        return userRepository.getWishList(tokenUtil.getUserId());
    }

    /**
     * @throws IOException
     *                     *********************************************************************************************
     */
    @Transactional
    public void updateProfile(UpdateProfileImage updateProfileImage) throws IOException {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (updateProfileImage.getImage() != null) {
            MediaDto imageDto = mediaService.uploadImage(updateProfileImage.getImage(), user.getUserImage().getId(),
                    "user" + user.getId());
            UserImage userImage = new UserImage(imageDto);
            user.setUserImage(userImage);
            userRepository.save(user);
        }

    }

    /*********************************************************************************************** */

    public void addAddress(AddAddressRequest addAddressRequest) {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        Address address = new Address(addAddressRequest, user);
        addressRepository.save(address);

    }

    /************************************************************************************************/
    public void addPhone(Integer phone) {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        Phone phone1 = new Phone(phone, user);
        phoneRepository.save(phone1);
    }

    /*********************************************************************************************** */

    public List<Address> getAddresses() {
        return addressRepository.findAllByUserId(tokenUtil.getUserId());
    }

    /*********************************************************************************************** */

    public List<Phone> getPhones() {

        return phoneRepository.findAllByUserId(tokenUtil.getUserId());
    }

    /************************************************************************************************ */
    public User getUser() {
        User user = userRepository.findById(tokenUtil.getUserId())
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        user.setAddresses(getAddresses());
        user.setPhones(getPhones());
        return user;
    }

    /***************************************************************************************************** */
    public void deleteAddress(Integer addressId) {
        if (!addressRepository.isUserIsOwner(addressId, tokenUtil.getUserId()))
            throw new CustomException("You can't delete this address", HttpStatus.NOT_FOUND);
        addressRepository.deleteById(addressId);
    }

    /*******************************************************************************************************/

    public void deletePhone(Integer phoneId) {
        if (!phoneRepository.isUserIsOwner(phoneId, tokenUtil.getUserId()))
            throw new CustomException("You can't delete this phone", HttpStatus.NOT_FOUND);
        phoneRepository.deleteById(phoneId);
    }

    /**
     * @throws Exception
     *                   **************************************************************************************************
     */

    public void deleteAccount() throws Exception {
        mediaService.deleteByFolder("user" + tokenUtil.getUserId());
        userRepository.deleteById(tokenUtil.getUserId());
    }
}
