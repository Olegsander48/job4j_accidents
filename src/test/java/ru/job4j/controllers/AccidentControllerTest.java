package ru.job4j.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.models.Accident;
import ru.job4j.service.accident.AccidentService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    void shouldReturnTwoModelAttributesAndCreateView() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"))
                .andExpect(model().attributeExists("types", "rules"));
    }

    @Test
    @WithMockUser
    void shouldReturnAccidentModelAttributeAndEditView() throws Exception {
        Mockito.when(accidentService.findById(1)).thenReturn(Optional.of(new Accident()));
        this.mockMvc.perform(get("/editAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attribute("accident", new Accident()));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorViewWithMessage() throws Exception {
        Mockito.when(accidentService.findById(1)).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/editAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("fragments/errors/404"))
                .andExpect(model().attribute("message", "Accident not found"));
    }

}