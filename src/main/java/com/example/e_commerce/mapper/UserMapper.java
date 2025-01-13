package com.example.e_commerce.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.e_commerce.dto.UserSignUpRequest;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.User;

import lombok.Data;

@Data
@Component
public class UserMapper {

    public User toEntity(UserSignUpRequest signUpRequest) {
        if (signUpRequest == null) {
            return null;
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(signUpRequest.getPassword());
        user.setActive(false);
        Address address = new Address();
        address.setCity(signUpRequest.getCity());
        address.setState(signUpRequest.getState());
        address.setCountry(signUpRequest.getCountry());
        address.setZipCode(signUpRequest.getZipCode());
        address.setStreet(signUpRequest.getStreet());
        address.setHouseNumber(signUpRequest.getHouseNumber());
        address.setUser(user);
        user.setAddresses(List.of(address));

        return user;
    }

    /********************************************************************************************* */

    // public UserDto toDto(User user) {
    // if (user == null) {
    // return null;
    // }

    // UserDto UserDto = new UserDto();

    // UserDto.setAbout(user.getAbout());
    // UserDto.setId(user.getId());
    // UserDto.setEmail(user.getEmail());
    // UserDto.setOnline(user.getOnline());
    // UserDto.setImageUrl(user.getImageUrl());
    // UserDto.setName(user.getName());

    // return UserDto;
    // }

    /*************************************************************************************************/

    // public List<UserDto> toDtoList(List<User> customers) {
    // if (customers == null) {
    // return null;
    // }

    // List<UserDto> list = new ArrayList<UserDto>(customers.size());
    // for (User User : customers) {
    // list.add(toDto(User));
    // }

    // return list;
    // }

    /************************************************************************************************/

}
