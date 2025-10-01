package ru.job4j.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldReturnLoginPageWithEmptyErrorMessage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("errorMessage",
                        (@org.jspecify.annotations.Nullable Object) null));
    }

    @Test
    @WithMockUser
    void shouldReturnLoginPageWithIncorrectCredentialsErrorMessage() throws Exception {
        this.mockMvc.perform(get("/login").param("error", "yes"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("errorMessage", "Username or password is incorrect !!"));
    }

    @Test
    @WithMockUser
    void shouldReturnLoginPageWithLoginMessage() throws Exception {
        this.mockMvc.perform(get("/login").param("logout", "yes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("errorMessage", "You have been successfully logged out !!"));
    }

    @Test
    @WithMockUser
    void shouldReturnRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}