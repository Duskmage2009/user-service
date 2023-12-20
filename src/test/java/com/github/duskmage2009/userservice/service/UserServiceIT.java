package com.github.duskmage2009.userservice.service;

import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.entity.User;
import com.github.duskmage2009.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIT {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        UserCreateUpdateDTO userCreateUpdateDTO = UserCreateUpdateDTO.builder()
                .email("mail@ua")
                .address("MJ")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2022, 1, 1))
                .phoneNumber("123123123").build();

        UserReadDTO userReadDTO = userService.create(userCreateUpdateDTO);
        assertEquals(userCreateUpdateDTO.getAddress(), userReadDTO.getAddress());
        assertEquals(userCreateUpdateDTO.getEmail(), userReadDTO.getEmail());
        assertEquals(Long.valueOf(1), userReadDTO.getId());
        assertEquals(userCreateUpdateDTO.getFirstName(), userReadDTO.getFirstName());
        assertEquals(userCreateUpdateDTO.getLastName(), userReadDTO.getLastName());
        assertEquals(userCreateUpdateDTO.getPhoneNumber(), userReadDTO.getPhoneNumber());
        assertEquals(userCreateUpdateDTO.getBirthDate(), userReadDTO.getBirthDate());
        assertTrue(userRepository.findById(Long.valueOf(1)).isPresent());
    }

    @Test
    void partialUpdate() {
        User user = User.builder()
                .email("newmail@com")
                .address("goodmoon3")
                .firstName("Konstans")
                .lastName("Kioto")
                .birthDate(LocalDate.of(2020, 3, 3))
                .phoneNumber("13264568768")
                .build();
        UserCreateUpdateDTO userCreateUpdateDTO = UserCreateUpdateDTO.builder()
                .email("trevor@ua")
                .address("MJ")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2020, 1, 1))
                .phoneNumber("123123123").build();
        User savedUser = userRepository.save(user);
        UserReadDTO userReadDTO = userService.partialUpdate(userCreateUpdateDTO, savedUser.getId());
        assertEquals(userCreateUpdateDTO.getAddress(), userReadDTO.getAddress());
        assertEquals(userCreateUpdateDTO.getEmail(), userReadDTO.getEmail());
        assertEquals(savedUser.getId(), userReadDTO.getId());
        assertEquals(userCreateUpdateDTO.getFirstName(), userReadDTO.getFirstName());
        assertEquals(userCreateUpdateDTO.getLastName(), userReadDTO.getLastName());
        assertEquals(userCreateUpdateDTO.getPhoneNumber(), userReadDTO.getPhoneNumber());
        assertEquals(userCreateUpdateDTO.getBirthDate(), userReadDTO.getBirthDate());
    }

    @Test
    void fullUpdate() {
        User user = User.builder()
                .email("jovanni@com")
                .address("goodmoon3")
                .firstName("Konstans")
                .lastName("Kioto")
                .birthDate(LocalDate.of(2019, 3, 3))
                .phoneNumber("13264568768")
                .build();
        UserCreateUpdateDTO userCreateUpdateDTO = UserCreateUpdateDTO.builder()
                .email("holopenio@ua")
                .address("MJ")
                .firstName("Jonn")
                .lastName("Lohanto")
                .birthDate(LocalDate.of(2020, 1, 1))
                .phoneNumber("123123123").build();
        User savedUser = userRepository.save(user);
        UserReadDTO userReadDTO = userService.fullUpdate(userCreateUpdateDTO, savedUser.getId());

        assertEquals(userCreateUpdateDTO.getAddress(), userReadDTO.getAddress());
        assertEquals(userCreateUpdateDTO.getEmail(), userReadDTO.getEmail());
        assertEquals(savedUser.getId(), userReadDTO.getId());
        assertEquals(userCreateUpdateDTO.getFirstName(), userReadDTO.getFirstName());
        assertEquals(userCreateUpdateDTO.getLastName(), userReadDTO.getLastName());
        assertEquals(userCreateUpdateDTO.getPhoneNumber(), userReadDTO.getPhoneNumber());
        assertEquals(userCreateUpdateDTO.getBirthDate(), userReadDTO.getBirthDate());
    }

    @Test
    void delete() {
        User user = User.builder()
                .email("jovanni@com")
                .address("Kongo")
                .firstName("Alba")
                .lastName("Jonas")
                .birthDate(LocalDate.of(2018, 3, 3))
                .phoneNumber("878887778877")
                .build();

        User savedUser = userRepository.save(user);
        userService.delete(savedUser.getId());
        Optional<User> userOptional = userRepository.findById(savedUser.getId());
        Assertions.assertTrue(userOptional.isEmpty());
    }

    @Test
    void deleteException() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.delete(-1L));

    }

    @Test
    void searchByBirthDateBetween() {
        LocalDate from = LocalDate.of(2022, 1, 1);
        LocalDate to = LocalDate.of(2022, 1, 16);
        User user = new User(1L, "Hello@mail", "John", "Travolta",
                LocalDate.of(2022, 1, 2), "USA", "12312312");
        userRepository.save(user);
        User user2 = new User(2L, "Faraon@mail", "Borito", "Puerta",
                LocalDate.of(2022, 1, 15), "England", "147675675");
        userRepository.save(user2);
        List<UserReadDTO> userReadDTOList = userService.searchByBirthDateBetween(from, to);
        assertEquals(userReadDTOList.size(), 2);
        for (int i = 0; i < userReadDTOList.size(); i++) {

            boolean before = userReadDTOList.get(i).getBirthDate().isBefore(to);
            boolean after = userReadDTOList.get(i).getBirthDate().isAfter(from);
            assertTrue(after);
            assertTrue(before);
        }
    }
}