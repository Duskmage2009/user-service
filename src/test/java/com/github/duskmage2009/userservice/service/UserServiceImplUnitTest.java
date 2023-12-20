package com.github.duskmage2009.userservice.service;

import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.entity.User;
import com.github.duskmage2009.userservice.exeption.UserNotFoundException;
import com.github.duskmage2009.userservice.mapper.UserMapper;
import com.github.duskmage2009.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {
    @Mock
    UserRepository userRepository;
    UserMapper userMapper = new UserMapper();
    UserServiceImpl userService;
    UserCreateUpdateDTO userCreateUpdateDTO;
    UserCreateUpdateDTO partialUpdateDTO;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userMapper);
        userCreateUpdateDTO = UserCreateUpdateDTO.builder()
                .email("majorio@gmail.com")
                .address("MJ")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2003, 1, 1))
                .phoneNumber("123123123").build();
        partialUpdateDTO = UserCreateUpdateDTO.builder()
                .email("bonjorno@gmail.com")
                .build();
    }

    @Test
    void create() {
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserReadDTO expectedDTO = userMapper.mapFromUserToReadDTO(user);
        UserReadDTO actualDTO = userService.create(userCreateUpdateDTO);
        Mockito.verify(userRepository).save(user);
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void partialUpdate() {
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        userMapper.updateUserFromCreateUpdateDTO(partialUpdateDTO, user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserReadDTO expectedDTO = userMapper.mapFromUserToReadDTO(user);
        UserReadDTO actualDTO = userService.partialUpdate(partialUpdateDTO, 1L);
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).save(user);
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void partialUpdateException() {
        Optional<User> userOptional = Optional.empty();
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.partialUpdate(partialUpdateDTO, 1L));
    }

    @Test
    void fullUpdate() {
        UserCreateUpdateDTO fullUpdateDTO = UserCreateUpdateDTO.builder()
                .email("Alloha@gmail.com")
                .address("Argentina")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2000, 1, 8))
                .phoneNumber("123123123").build();
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        userMapper.updateUserFromCreateUpdateDTO(fullUpdateDTO, user);
        user.setId(1L);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserReadDTO expectedDTO = userMapper.mapFromUserToReadDTO(user);
        UserReadDTO actualDTO = userService.fullUpdate(fullUpdateDTO, 1L);
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).save(user);
        assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void delete() {
        User user = userMapper.mapFromCreateUpdateDTOToUser(userCreateUpdateDTO);
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        userService.delete(1L);
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void deleteException() {
        Optional<User> userOptional = Optional.empty();
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void searchByBirthDateBetween() {
        UserCreateUpdateDTO localDateDTOTo = UserCreateUpdateDTO.builder()
                .email("Banzai@gmail.com")
                .address("Brazil")
                .firstName("Babyn")
                .lastName("Toharinto")
                .birthDate(LocalDate.of(2023, 1, 8))
                .phoneNumber("4212312312").build();
        UserCreateUpdateDTO localDateDTOFrom = UserCreateUpdateDTO.builder()
                .email("Alloha@gmail.com")
                .address("Argentina")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2000, 1, 8))
                .phoneNumber("123123123").build();
        LocalDate to = LocalDate.of(2023, 1, 8);
        LocalDate from = LocalDate.of(2000, 1, 8);
        User userTo = userMapper.mapFromCreateUpdateDTOToUser(localDateDTOTo);
        User userFrom = userMapper.mapFromCreateUpdateDTOToUser(localDateDTOFrom);
        List<User> userList = new ArrayList<>();
        userList.add(userTo);
        userList.add(userFrom);
        List<UserReadDTO> expectedDTO = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            expectedDTO.add(userMapper.mapFromUserToReadDTO(userList.get(i)));
        }
        Mockito.when(userRepository.findAllByBirthDateBetween(to, from)).thenReturn(userList);
        List<UserReadDTO> actualDTO = userService.searchByBirthDateBetween(to, from);
        assertEquals(expectedDTO, actualDTO);
    }
}