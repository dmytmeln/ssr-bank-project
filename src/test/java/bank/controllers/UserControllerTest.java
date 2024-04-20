package bank.controllers;

import bank.domain.User;
import bank.dto.UserForm;
import bank.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.modelmapper.ModelMapper;
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

    @MockBean
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<UserForm> userCaptor;

    private final String USER_PAGE = "html/user";

    private final static Long USER_ID = 1L;
    private static UserForm userForm;
    private static User user;

    @BeforeAll
    static void init() {
        userForm = UserForm.builder()
                .email("dimamel28@gmail.com")
                .password("12!@asAS")
                .phoneNumber("380984035791")
                .firstname("Dmytro")
                .lastname("Melnyk")
                .creationDate(Instant.now())
                .build();

        user = User.builder()
                .id(USER_ID)
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
        when(userServiceMock.findById(USER_ID)).thenReturn(user);
        when(userServiceMock.update(any(UserForm.class), eq(USER_ID))).thenReturn(user);
        when(modelMapper.map(user, UserForm.class)).thenReturn(userForm);
    }

    @Test
    void testGetShowHome() throws Exception {
        mockMvc.perform(get("/user").sessionAttr("userId", USER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userForm"))
                .andExpect(model().attribute("userForm", userForm))
                .andExpect(model().attributeExists("userId"))
                .andExpect(model().attribute("userId", USER_ID))
                .andExpect(view().name(USER_PAGE));

        verify(userServiceMock, times(1)).findById(USER_ID);
    }

    @Test
    void testInvalidGetShowHome() throws Exception {
        long id = -1L;

        when(userServiceMock.findById(id)).thenThrow(new EntityNotFoundException(
                "User with  id: %d not found".formatted(USER_ID)
        ));

        mockMvc.perform(get("/user").sessionAttr("userId", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("html/error"));

        verify(userServiceMock, times(1)).findById(id);
    }

    @Test
    void testValidUpdateUser() throws Exception {
        String updatedFirstName = "Jason";

        mockMvc.perform(post("/user/update/{userId}", USER_ID)
                        .param("firstname", updatedFirstName)
                        .param("lastname", userForm.getLastname())
                        .param("email", userForm.getEmail())
                        .param("password", userForm.getPassword())
                        .param("phoneNumber", userForm.getPhoneNumber())
                        .flashAttr("userForm", new UserForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"))
                .andExpect(view().name("redirect:/user"));

        verify(userServiceMock, times(1)).update(userCaptor.capture(), eq(USER_ID));
        UserForm updatedUser = userCaptor.getValue();

        assertEquals(updatedFirstName, updatedUser.getFirstname());
    }

    @Test
    void testInvalidUpdateUser() throws Exception {
        mockMvc.perform(post("/user/update/{userId}", USER_ID)
                        .param("firstname", "")
                        .param("lastname", userForm.getLastname())
                        .param("email", userForm.getEmail())
                        .param("password", userForm.getPassword())
                        .param("phoneNumber", userForm.getPhoneNumber()))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("userForm", "firstname"))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_PAGE));
    }


}
