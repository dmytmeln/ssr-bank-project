package bank.controllers;

import bank.domain.User;
import bank.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private final String AUTH_PAGE = "html/auth";

    private static final Long ID = 1L;
    private static User user;

    @BeforeAll
    static void init() {
        user = User.builder()
                .id(ID)
                .email("dimamel28@gmail.com")
                .password("12!@asAS")
                .phoneNumber("380984035791")
                .firstName("Dmytro")
                .lastName("Melnyk")
                .creationDate(Instant.now())
                .build();
    }

    @BeforeEach
    void setup() {
        when(userServiceMock.signup(any(User.class))).thenAnswer(invocationOnMock -> {
            User user = invocationOnMock.getArgument(0);
            user.setId(ID);
            return user;
        });

        when(userServiceMock.login(any(User.class))).thenAnswer(invocationOnMock -> {
            User loginUser = invocationOnMock.getArgument(0);
            loginUser.setId(ID);
            loginUser.setFirstName(user.getFirstName());
            loginUser.setLastName(user.getLastName());
            loginUser.setCreationDate(user.getCreationDate());
            return loginUser;
        });
    }

    @Test
    void testShowAuth() throws Exception {
        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name(AUTH_PAGE));
    }

    @Test
    void testSignupUser() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .param("email", user.getEmail())
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber())
                        .param("creationDate", String.valueOf(user.getCreationDate())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/auth"));

        verify(userServiceMock, times(1)).signup(userCaptor.capture());
        User captorValue = userCaptor.getValue();

        assertEquals(user, captorValue);
    }

    @Test
    void testInvalidSignupUser() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .param("email", user.getEmail())
                        .param("firstName", "")
                        .param("lastName", user.getLastName())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber())
                        .param("creationDate", String.valueOf(user.getCreationDate())))
                .andExpect(status().isOk())
                .andExpect(view().name(AUTH_PAGE));
    }

    @Test
    void testLoginUser() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user"));

        verify(userServiceMock, times(1)).login(userCaptor.capture());
        User captorValue = userCaptor.getValue();

        assertEquals(user, captorValue);
    }

}
