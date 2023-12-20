package com.github.duskmage2009.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.duskmage2009.userservice.DTO.UserCreateUpdateDTO;
import com.github.duskmage2009.userservice.DTO.UserReadDTO;
import com.github.duskmage2009.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    UserCreateUpdateDTO userCreateUpdateDTO = UserCreateUpdateDTO.builder()
            .email("majorio@gmail.com")
            .address("MJ")
            .firstName("Jonn")
            .lastName("Lohanto")
            .birthDate(LocalDate.of(2003, 1, 1))
            .phoneNumber("123123123").build();

    UserReadDTO userReadDTO = UserReadDTO.builder()
            .id(1L)
            .email("majorio@gmail.com")
            .address("MJ")
            .firstName("Jonn")
            .lastName("Lohanto")
            .birthDate(LocalDate.of(2023, 1, 1))
            .phoneNumber("123123123").build();

    List<UserReadDTO> userReadDTOList = new ArrayList<>();

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() throws Exception {
        Mockito.when(userService.create(Mockito.any())).thenReturn(userReadDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userCreateUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void createWrong() throws Exception {
        Mockito.when(userService.create(Mockito.any())).thenReturn(userReadDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userCreateUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void fullUpdate() throws Exception {
        Mockito.when(userService.fullUpdate(Mockito.any(), Mockito.anyLong())).thenReturn(userReadDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/1")
                        .content(objectMapper.writeValueAsString(userCreateUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void partialUpdate() throws Exception {
        Mockito.when(userService.partialUpdate(Mockito.any(), Mockito.anyLong())).thenReturn(userReadDTO);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/1")
                        .content(objectMapper.writeValueAsString(userCreateUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/1")
                        .content(objectMapper.writeValueAsString(userCreateUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void searchByBirthDateBetween() throws Exception {
        Mockito.when(userService.searchByBirthDateBetween(Mockito.any(), Mockito.any())).thenReturn(userReadDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .param("from", "1991-12-05")
                        .param("to", "2023-12-05")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}