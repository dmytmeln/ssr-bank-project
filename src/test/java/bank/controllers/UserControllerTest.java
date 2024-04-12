package bank.controllers;

import bank.model.domain.User;
import bank.model.services.UserService;
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

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private final String USER_PAGE = "html/user";

    private static Long userId;
    private static User user;

    @BeforeAll
    static void init() {
        userId = 1L;
        user = User.builder()
                .id(userId)
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
        when(userServiceMock.findById(userId)).thenReturn(user);
        when(userServiceMock.update(any(User.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    }

    @Test
    void testGetShowHome() throws Exception {
        mockMvc.perform(get("/user").sessionAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user))
                .andExpect(view().name(USER_PAGE));

        verify(userServiceMock, times(1)).findById(userId);
    }

    @Test
    void testInvalidGetShowHome() throws Exception {
        long id = -1L;

        when(userServiceMock.findById(id)).thenThrow(new EntityNotFoundException(
                "User with  id: %d not found".formatted(userId)
        ));

        mockMvc.perform(get("/user").sessionAttr("userId", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("html/error"));

        verify(userServiceMock, times(1)).findById(id);
    }

    @Test
    void testValidUpdateUser() throws Exception {
        String updatedFirstName = "Jason";

        mockMvc.perform(post("/user/update/{userId}", userId)
                        .param("firstName", updatedFirstName)
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"))
                .andExpect(view().name("redirect:/user"));

        verify(userServiceMock, times(1)).update(userCaptor.capture());
        User updatedUser = userCaptor.getValue();

        assertEquals(updatedFirstName, updatedUser.getFirstName());
        assertEquals(userId, updatedUser.getId());
    }

    @Test
    void testInvalidUpdateUser() throws Exception {
        mockMvc.perform(post("/user/update/{userId}", userId)
                        .param("firstName", "")
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword())
                        .param("phoneNumber", user.getPhoneNumber()))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE));
    }


}
