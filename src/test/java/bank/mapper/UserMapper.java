package bank.mapper;

import bank.domain.User;
import bank.dto.UserForm;
import bank.dto.UserLogin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapper {

    private static User user;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeAll
    static  void init() {
        user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .password("12!@asAS")
                .email("john.doe@example.com")
                .phoneNumber("123123123123")
                .creationDate(Instant.now())
                .build();
    }

    @Test
    void testMapFromUserToUserForm() {

        UserForm mapped = modelMapper.map(user, UserForm.class);

        assertEquals(user.getEmail(), mapped.getEmail());
        assertEquals(user.getFirstname(), mapped.getFirstname());
        assertEquals(user.getLastname(), mapped.getLastname());
        assertEquals(user.getPassword(), mapped.getPassword());
        assertEquals(user.getPhoneNumber(), mapped.getPhoneNumber());
        assertEquals(user.getCreationDate(), mapped.getCreationDate());

    }

    @Test
    void testMapFromUserFormToUser() {

        UserForm mappedForm = modelMapper.map(user, UserForm.class);
        User mappedUser = modelMapper.map(mappedForm, User.class);

        assertEquals(mappedForm.getEmail(), mappedUser.getEmail());
        assertEquals(mappedForm.getFirstname(), mappedUser.getFirstname());
        assertEquals(mappedForm.getLastname(), mappedUser.getLastname());
        assertEquals(mappedForm.getPassword(), mappedUser.getPassword());
        assertEquals(mappedForm.getPhoneNumber(), mappedUser.getPhoneNumber());
        assertEquals(mappedForm.getCreationDate(), mappedUser.getCreationDate());

    }

    @Test
    void testMapFromUserToUserLogin() {

        UserLogin mapped = modelMapper.map(user, UserLogin.class);

        assertEquals(user.getEmail(), mapped.getEmail());
        assertEquals(user.getPassword(), mapped.getPassword());
        assertEquals(user.getPhoneNumber(), mapped.getPhoneNumber());

    }

    @Test
    void testMapFromUserLoginToUser() {

        UserLogin mappedLogin = modelMapper.map(user, UserLogin.class);
        User mappedUser = modelMapper.map(mappedLogin, User.class);

        assertEquals(mappedLogin.getEmail(), mappedUser.getEmail());
        assertEquals(mappedLogin.getPassword(), mappedUser.getPassword());
        assertEquals(mappedLogin.getPhoneNumber(), mappedUser.getPhoneNumber());

    }

}
