package ru.job4j.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.models.User;
import ru.job4j.service.user.UserService;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @AfterEach
    void cleanUp() {
        userService.findAll().forEach(user -> userService.delete(user.getId()));
    }

    @Test
    @WithMockUser
    void shouldReturnRegistrationPage() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @WithMockUser
    void shouldRegisterUserAndReturnRedirect() throws Exception {
        this.mockMvc.perform(post("/register")
                        .param("username", "test name")
                        .param("password", "test password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        Optional<User> savedUser = userService.findByUsername("test name");
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getAuthority().getAuthority()).isEqualTo("ROLE_USER");
        assertThat(savedUser.get().getUsername()).isEqualTo("test name");
        assertThat(savedUser.get().isEnabled()).isTrue();
    }

    /**
     * First time add user - to add it properly to database
     * Second time - to get unique username constraint exception
     * @throws Exception
     */
    @Test
    @WithMockUser
    void shouldReturnDuplicateExceptionAndErrorMessageOnSameView() throws Exception {
        this.mockMvc.perform(post("/register")
                        .param("username", "test name")
                        .param("password", "test password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        this.mockMvc.perform(post("/register")
                        .param("username", "test name")
                        .param("password", "test password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}