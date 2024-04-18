package bank.service.serviceImpl;

import bank.domain.User;
import bank.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .password("12!@asAS")
                .email("john.doe@example.com")
                .phoneNumber("123123123123")
                .build();
    }

    @Test
    void testValidFindById() {
        long id = 1L;
        user.setId(id);
        when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(user));

        User foundUser = userService.findById(id);

        assertEquals(user, foundUser);
    }

    @Test
    void testInvalidFindById() {
        assertThrows(EntityNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void testSignupTest() {

        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        long expectedId = 2;

        when(userRepoMock.existsByEmailOrPhoneNumber(email, phoneNumber)).thenReturn(false);
        when(userRepoMock.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User userToSave = invocationOnMock.getArgument(0);
            userToSave.setId(expectedId);
            return userToSave;
        });

        User signupUser = userService.signup(user);
        Long actualId = signupUser.getId();

        when(userRepoMock.findById(actualId)).thenReturn(Optional.of(signupUser));

        User savedUser = userService.findById(actualId);

        verify(userRepoMock, times(1)).save(userCaptor.capture());
        verify(userRepoMock, times(1)).findById(actualId);

        assertEquals(expectedId, actualId);
        assertEquals(user, savedUser);

    }

    @Test
    void testSignupExistingUserTest() {
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();

        when(userRepoMock.existsByEmailOrPhoneNumber(email, phoneNumber)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.signup(user));
        verify(userRepoMock, times(1)).existsByEmailOrPhoneNumber(email, phoneNumber);
    }

    @Test
    void testLoginTest() {
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();

        when(userRepoMock.findUserByEmailAndPhoneNumberAndPassword(email, phoneNumber, password)).thenReturn(Optional.of(user));

        User loggedInUser = userService.login(user);

        verify(userRepoMock, times(1)).findUserByEmailAndPhoneNumberAndPassword(email, phoneNumber, password);

        assertNotNull(loggedInUser);
        assertEquals(email, loggedInUser.getEmail());
        assertEquals(phoneNumber, loggedInUser.getPhoneNumber());
        assertEquals(password, loggedInUser.getPassword());
    }

    @Test
    void testLoginNonexistentUserTest() {
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();

        assertThrows(EntityNotFoundException.class, () -> userService.login(user));

        verify(userRepoMock, times(1)).findUserByEmailAndPhoneNumberAndPassword(email, phoneNumber, password);
    }


    @Test
    void testUpdateTest() {
        long id = 1L;

        String email = "email@gmail.com";
        String phoneNumber = "380981258958";
        User userToUpdate = User.builder()
                .id(id)
                .firstname("UpdatedFirstName")
                .lastname("UpdatedLastName")
                .phoneNumber(phoneNumber)
                .password("12!@asAS")
                .email(email)
                .build();

        user.setId(id);
        when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userRepoMock.existsByEmailAndPhoneNumber(email, phoneNumber)).thenReturn(false);
        when(userRepoMock.save(any(User.class))).thenAnswer(invocationOnMock -> {
            User userToSave = invocationOnMock.getArgument(0);
            return userToSave.toBuilder()
                    .email(userToUpdate.getEmail())
                    .password(userToUpdate.getPassword())
                    .phoneNumber(userToUpdate.getPhoneNumber())
                    .firstname(userToUpdate.getFirstname())
                    .lastname(userToUpdate.getLastname())
                    .build();
        });

        userService.update(userToUpdate);

        verify(userRepoMock, times(1)).existsByEmailAndPhoneNumber(email, phoneNumber);
        verify(userRepoMock, times(1)).findById(id);
        verify(userRepoMock, times(1)).save(userCaptor.capture());

        User updatedUser = userCaptor.getValue();

        assertNotNull(updatedUser);
        assertEquals(userToUpdate, updatedUser);

    }

    @Test
    void testUpdateNonExistingUserTest() {
        long id = 1L;
        user.setId(id);
        assertThrows(EntityNotFoundException.class, () -> userService.update(user));
    }

    @Test
    void testUpdateWithAlreadyExistingEmailAndPhoneNumber() {
        long id = 1L;
        String email = "email@gmail.com";
        String phoneNumber = "380981258958";
        User userToUpdate = User.builder()
                .id(id)
                .firstname("UpdatedFirstName")
                .lastname("UpdatedLastName")
                .phoneNumber(phoneNumber)
                .password("12!@asAS")
                .email(email)
                .build();

        user.setId(id);
        when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userRepoMock.existsByEmailAndPhoneNumber(email, phoneNumber)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.update(userToUpdate));
    }

    @Test
    void testUpdateWithAlreadyExistingEmail() {
        long id = 1L;
        String email = "email@gmail.com";
        User userToUpdate = User.builder()
                .id(id)
                .firstname("UpdatedFirstName")
                .lastname("UpdatedLastName")
                .phoneNumber(user.getPhoneNumber())
                .password("12!@asAS")
                .email(email)
                .build();

        user.setId(id);
        when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userRepoMock.existsByEmail(email)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.update(userToUpdate));
    }

    @Test
    void testUpdateWithAlreadyExistingPhoneNumber() {
        long id = 1L;
        String phoneNumber = "380981258958";
        User userToUpdate = User.builder()
                .id(id)
                .firstname("UpdatedFirstName")
                .lastname("UpdatedLastName")
                .phoneNumber(phoneNumber)
                .password("12!@asAS")
                .email(user.getEmail())
                .build();

        user.setId(id);
        when(userRepoMock.findById(id)).thenReturn(Optional.ofNullable(user));
        when(userRepoMock.existsByPhoneNumber(phoneNumber)).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.update(userToUpdate));
    }

    @Test
    void testDeleteNonexistentUser() {
        long id = 1L;
        when(userRepoMock.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.delete(id));
    }

}