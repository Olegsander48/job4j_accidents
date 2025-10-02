package ru.job4j.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.models.Accident;
import ru.job4j.service.accident.AccidentService;
import ru.job4j.service.accidenttype.AccidentTypeService;
import ru.job4j.service.rule.RuleService;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccidentService accidentService;

    @Autowired
    private AccidentTypeService accidentTypeService;

    @Autowired
    private RuleService ruleService;

    @AfterEach
    void cleanUp() {
        accidentService.findAll().forEach(accident -> accidentService.delete(accident.getId()));
    }

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
    void shouldSaveAccident() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "test name")
                        .param("description", "test description")
                        .param("address", "test address")
                        .param("type.id", "1")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        List<Accident> savedAccident = accidentService.findAll();
        assertThat(savedAccident).hasSize(1);
        assertThat(savedAccident.get(0).getName()).isEqualTo("test name");
        assertThat(savedAccident.get(0).getDescription()).isEqualTo("test description");
        assertThat(savedAccident.get(0).getAddress()).isEqualTo("test address");
        assertThat(savedAccident.get(0).getAccidentType()).isEqualTo(accidentTypeService.findById(1).get());
        assertThat(savedAccident.get(0).getRules()).isEqualTo(ruleService.findRulesByIds(List.of(1, 2)));
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundViewAndMessage() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "test name")
                        .param("description", "test description")
                        .param("address", "test address")
                        .param("type.id", "1000")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("fragments/errors/4xx"))
                .andExpect(model().attribute("message", "Element not found"));
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestViewAndMessage() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "test name")
                        .param("description", "   ")
                        .param("address", "test address")
                        .param("type.id", "1")
                        .param("rIds", "1", "2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name("fragments/errors/4xx"))
                .andExpect(model().attribute("message", "Accident description cannot be null or empty"));
    }

    @Test
    @WithMockUser
    void shouldReturnAccidentModelAttributeAndEditView() throws Exception {
        Accident accident = new Accident(0, "test name", "test description", "test address",
                accidentTypeService.findById(1).get(),
                ruleService.findRulesByIds(List.of(1, 2)));
        accidentService.save(accident);

        this.mockMvc.perform(get("/editAccident").param("id", String.valueOf(accident.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attribute("accident", accident));
    }

    @Test
    @WithMockUser
    void shouldReturnErrorViewWithMessage() throws Exception {
        this.mockMvc.perform(get("/editAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("fragments/errors/4xx"))
                .andExpect(model().attribute("message", "Element not found"));
    }

    @Test
    @WithMockUser
    void shouldUpdateAccident() throws Exception {
        Accident accident = new Accident(0, "test name", "test description", "test address",
                accidentTypeService.findById(1).get(),
                ruleService.findRulesByIds(List.of(1, 2)));
        accidentService.save(accident);

        this.mockMvc.perform(post("/editAccident")
                        .param("id", String.valueOf(accident.getId()))
                        .param("name", "new name")
                        .param("description", "new description")
                        .param("address", "new address"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Accident updatedAccident = accidentService.findById(accident.getId()).get();
        assertThat(updatedAccident.getId()).isEqualTo(accident.getId());
        assertThat(updatedAccident.getName()).isEqualTo("new name");
        assertThat(updatedAccident.getDescription()).isEqualTo("new description");
        assertThat(updatedAccident.getAddress()).isEqualTo("new address");
        assertThat(updatedAccident.getAccidentType()).isNull();
        assertThat(updatedAccident.getRules()).isEmpty();
    }
}