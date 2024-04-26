package bank.controllers;

import bank.dto.UserForm;
import bank.dto.UserLogin;
import bank.model.User;
import bank.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Captor
    private ArgumentCaptor<UserLogin> userLoginCaptor;
    @Captor
    private ArgumentCaptor<UserForm> userFormCaptor;

    private final String SIGNUP_PAGE = "auth-signup";
    private final String LOGIN_PAGE = "auth-login";

    private static final Long ID = 1L;
    private static User user;

    @BeforeAll
    static void init() {
        user = User.builder()
                .id(ID)
                .email("dimamel28@gmail.com")
                .password("12!@asAS")
                .phoneNumber("380984035791")
                .firstname("Dmytro")
                .lastname("Melnyk")
                .build();
    }

    @BeforeEach
    void setup() {
        when(userServiceMock.signup(any(UserForm.class))).thenReturn(user);

        when(userServiceMock.login(any(UserLogin.class))).thenAnswer(invocationOnMock -> {
            UserLogin userLogin = invocationOnMock.getArgument(0);
            if (userLogin.getEmail().isBlank()) {
                throw new EntityNotFoundException();
            }
            return User.builder()
                    .id(ID)
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(userLogin.getEmail())
                    .password(userLogin.getPassword())
                    .phoneNumber(userLogin.getPhoneNumber())
                    .build();
        });
    }

    @Test
    void testShowAuthSignup() throws Exception {
        mockMvc.perform(get("/auth/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"))
                .andExpect(view().name(SIGNUP_PAGE));
    }

    @Test
    void testShowAuthLogin() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userLogin"))
                .andExpect(view().name(LOGIN_PAGE));
    }

    @Test
    void testSignupUser() throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        mockMvc.perform(post("/auth/signup")
                        .param("email", email)
                        .param("firstname", user.getFirstname())
                        .param("lastname", user.getLastname())
                        .param("password", password)
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/auth/login"));

        verify(userServiceMock, times(1)).signup(userFormCaptor.capture());
        UserForm captorValue = userFormCaptor.getValue();

        assertEquals(email, captorValue.getEmail());
        assertEquals(password, captorValue.getPassword());
    }

    @Test
    void testInvalidSignupUser() throws Exception {
        mockMvc.perform(post("/auth/signup")
                        .param("email", user.getEmail())
                        .param("firstname", "")
                        .param("lastname", user.getLastname())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(model().attributeHasFieldErrors("userForm", "firstname"))
                .andExpect(status().isOk())
                .andExpect(view().name(SIGNUP_PAGE));
    }

    @Test
    void testLoginUser() throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String phoneNumber = user.getPhoneNumber();
        mockMvc.perform(post("/auth/login")
                        .param("email", email)
                        .param("password", password)
                        .param("phoneNumber", phoneNumber))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user"));

        verify(userServiceMock, times(1)).login(userLoginCaptor.capture());
        UserLogin captorValue = userLoginCaptor.getValue();

        UserLogin userLogin = new UserLogin(password, email, phoneNumber);
        assertEquals(userLogin, captorValue);
    }

    @Test
    void testInvalidLoginUser() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .param("email", "")
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));
    }

}