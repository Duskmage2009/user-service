package com.github.duskmage2009.userservice.service;

import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.entity.User;
import com.github.duskmage2009.userservice.exeption.UserNotFoundException;
import com.github.duskmage2009.userservice.mapper.UserMapper;
import com.github.duskmage2009.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserReadDTO create(UserCreateUpdateDTO userCreateUpdateDTO) {
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        User baseUser = userRepository.save(user);
        return userMapper.mapFromUserToReadDTO(baseUser);
    }

    @Override
    public UserReadDTO partialUpdate(UserCreateUpdateDTO userCreateUpdateDTO, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException(id));
        userMapper.updateUserFromCreateUpdateDTO(userCreateUpdateDTO, user);
        User baseUser = userRepository.save(user);
        return userMapper.mapFromUserToReadDTO(baseUser);
    }

    @Override
    public UserReadDTO fullUpdate(UserCreateUpdateDTO userCreateUpdateDTO, Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        user.setId(id);
        User baseUser = userRepository.save(user);
        return userMapper.mapFromUserToReadDTO(baseUser);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    @Override
    public List<UserReadDTO> searchByBirthDateBetween(LocalDate from, LocalDate to) {
        List<User> userList = userRepository.findAllByBirthDateBetween(from, to);
        List<UserReadDTO> userReadDTOList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            userReadDTOList.add(userMapper.mapFromUserToReadDTO(userList.get(i)));
        }
        return userReadDTOList;
    }
}
