package com.github.duskmage2009.userservice.controller;

import com.github.duskmage2009.userservice.DTO.SearchByDateRequest;
import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.service.UserService;
import jakarta.validation.Valid;
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
    public UserReadDTO create(@Valid @RequestBody UserCreateUpdateDTO userCreateUpdateDTO) {
        return userService.create(userCreateUpdateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserReadDTO fullUpdate(@Valid @RequestBody UserCreateUpdateDTO userCreateUpdateDTO, @PathVariable("id") Long id) {
        return userService.fullUpdate(userCreateUpdateDTO, id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserReadDTO partialUpdate(@Valid @RequestBody UserCreateUpdateDTO userCreateUpdateDTO, @PathVariable("id") Long id) {
        return userService.partialUpdate(userCreateUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserReadDTO> searchByBirthDateBetween(@Valid SearchByDateRequest searchByDateRequest) {
        return userService.searchByBirthDateBetween(searchByDateRequest.getFrom(),searchByDateRequest.getTo());
    }
}

