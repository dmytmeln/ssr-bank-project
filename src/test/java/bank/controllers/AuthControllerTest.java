package bank.controllers;

import bank.domain.User;
import bank.dto.UserForm;
import bank.dto.UserLogin;
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
                .firstname("Dmytro")
                .lastname("Melnyk")
                .creationDate(Instant.now())
                .build();
    }

    @BeforeEach
    void setup() {
        when(userServiceMock.signup(any(UserForm.class))).thenReturn(user);

        when(userServiceMock.login(any(UserLogin.class))).thenAnswer(invocationOnMock -> {
            UserLogin userLogin = invocationOnMock.getArgument(0);
            return User.builder()
                    .id(ID)
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(userLogin.getEmail())
                    .password(userLogin.getPassword())
                    .phoneNumber(userLogin.getPhoneNumber())
                    .creationDate(user.getCreationDate())
                    .build();
        });
    }

    @Test
    void testShowAuth() throws Exception {
        mockMvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"))
                .andExpect(model().attributeExists("userLogin"))
                .andExpect(view().name(AUTH_PAGE));
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
                        .param("phoneNumber", user.getPhoneNumber())
                        .param("creationDate", String.valueOf(user.getCreationDate()))
                        .flashAttr("userLogin", new UserLogin())
                        .flashAttr("userForm", new UserForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/auth"));

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
                        .param("phoneNumber", user.getPhoneNumber())
                        .param("creationDate", String.valueOf(user.getCreationDate()))
                        .flashAttr("userLogin", new UserLogin())
                        .flashAttr("userForm", new UserForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(AUTH_PAGE));
    }

    @Test
    void testLoginUser() throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String phoneNumber = user.getPhoneNumber();
        mockMvc.perform(post("/auth/login")
                        .param("email", email)
                        .param("password", password)
                        .param("phoneNumber", phoneNumber)
                        .flashAttr("userLogin", new UserLogin())
                        .flashAttr("userForm", new UserForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user"));

        verify(userServiceMock, times(1)).login(userLoginCaptor.capture());
        UserLogin captorValue = userLoginCaptor.getValue();

        UserLogin userLogin = new UserLogin(password, email, phoneNumber);
        assertEquals(userLogin, captorValue);
    }

}
