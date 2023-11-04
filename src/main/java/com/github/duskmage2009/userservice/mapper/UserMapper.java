package com.github.duskmage2009.userservice.mapper;

import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User mapFromCreateUpdateDTOToUser(UserCreateUpdateDTO userCreateUpdateDTO) {
        return User.builder()
                .email(userCreateUpdateDTO.getEmail())
                .firstName(userCreateUpdateDTO.getFirstName())
                .lastName(userCreateUpdateDTO.getLastName())
                .birthDate(userCreateUpdateDTO.getBirthDate())
                .address(userCreateUpdateDTO.getAddress())
                .phoneNumber(userCreateUpdateDTO.getPhoneNumber())
                .build();
    }

    public UserReadDTO mapFromUserToReadDTO(User user) {
        return UserReadDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }

    public void updateUserFromCreateUpdateDTO(UserCreateUpdateDTO userCreateUpdateDTO, User user) {
        if (userCreateUpdateDTO.getEmail() != null) {
            user.setEmail(userCreateUpdateDTO.getEmail());
        }
        if (userCreateUpdateDTO.getFirstName() != null) {
            user.setFirstName(userCreateUpdateDTO.getFirstName());
        }
        if (userCreateUpdateDTO.getLastName() != null) {
            user.setLastName(userCreateUpdateDTO.getLastName());
        }
        if (userCreateUpdateDTO.getBirthDate() != null) {
            user.setBirthDate(userCreateUpdateDTO.getBirthDate());
        }
        if (userCreateUpdateDTO.getAddress() != null) {
            user.setAddress(userCreateUpdateDTO.getAddress());
        }
        if (userCreateUpdateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userCreateUpdateDTO.getPhoneNumber());
        }
    }
}
