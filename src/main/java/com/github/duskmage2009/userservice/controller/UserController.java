package com.github.duskmage2009.userservice.controller;

import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDTO create(@RequestBody UserCreateUpdateDTO userCreateUpdateDTO) {
        return userService.create(userCreateUpdateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserReadDTO fullUpdate(@RequestBody UserCreateUpdateDTO userCreateUpdateDTO, @PathVariable("id") Long id) {
        return userService.fullUpdate(userCreateUpdateDTO, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserReadDTO partialUpdate(@RequestBody UserCreateUpdateDTO userCreateUpdateDTO, @PathVariable("id") Long id) {
        return userService.partialUpdate(userCreateUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserReadDTO> searchByBirthDateBetween(LocalDate from, LocalDate to) {
        return userService.searchByBirthDateBetween(from, to);
    }
}

