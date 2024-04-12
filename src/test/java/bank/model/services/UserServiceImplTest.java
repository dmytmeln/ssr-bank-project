package bank.model.services;

import bank.model.domain.User;
import bank.model.repository.UserRepository;
import bank.model.services.servicesImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepoMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void checkSignupTest() {
        String email = "john.doe@example.com";
        String phoneNumber = "380984035792";
        long expectedId = 2;
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .password("12!@asAS")
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

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

        assertEquals(expectedId, actualId);
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber());
    }

    @Test
    void checkSignupExistingUserTest() {
        // Create a user that already exists in the database
        User existingUser = new User();
        existingUser.setFirstName("Dmytro");
        existingUser.setLastName("Melnyk");
        existingUser.setPassword("mdm281004");
        existingUser.setEmail("dimamel28@gmail.com");
        existingUser.setPhoneNumber("380984035791");

        assertThrows(EntityExistsException.class, () -> userService.signup(existingUser));
    }

    @Test
    void checkLoginTest() {
        User user = User.builder()
                .email("dimamel28@gmail.com")
                .phoneNumber("380984035791")
                .password("mdm281004")
                .build();

        User loggedInUser = userService.login(user);

        assertNotNull(loggedInUser);
        assertEquals(user.getEmail(), loggedInUser.getEmail());
        assertEquals(user.getPhoneNumber(), loggedInUser.getPhoneNumber());
        assertEquals(user.getPassword(), loggedInUser.getPassword());
    }

    @Test
    void checkLoginNonexistentUserTest() {
        User nonexistent = new User();
        nonexistent.setEmail("invalid@example.com");
        nonexistent.setPhoneNumber("380987654321");
        nonexistent.setPassword("Mdm281004");

        assertThrows(EntityNotFoundException.class, () -> userService.login(nonexistent));
    }


    @Test
    void checkUpdateTest() {
        User userToUpdate = User.builder()
                .id(1L)
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .phoneNumber("380981258958")
                .password("12!@asAS")
                .email("dimamel28@gmail.com")
                .build();

        User updatedUser = userService.update(userToUpdate);

        User fetchedUser = userService.findById(1L);

        assertNotNull(updatedUser);
        assertEquals(userToUpdate.getFirstName(), updatedUser.getFirstName());
        assertEquals(userToUpdate.getLastName(), updatedUser.getLastName());
        assertEquals(userToUpdate.getEmail(), updatedUser.getEmail());
        assertEquals(userToUpdate.getPhoneNumber(), updatedUser.getPhoneNumber());
        assertEquals(userToUpdate.getPassword(), updatedUser.getPassword());

        assertNotNull(fetchedUser);
        assertEquals(userToUpdate.getFirstName(), fetchedUser.getFirstName());
        assertEquals(userToUpdate.getLastName(), fetchedUser.getLastName());
        assertEquals(userToUpdate.getEmail(), fetchedUser.getEmail());
        assertEquals(userToUpdate.getPhoneNumber(), fetchedUser.getPhoneNumber());
        assertEquals(userToUpdate.getPassword(), fetchedUser.getPassword());
    }

    @Test
    void checkUpdateNonExistingUserTest() {
        User nonExistingUser = User.builder()
                .id(2L)
                .firstName("NonExistingFirstName")
                .lastName("NonExistingLastName")
                .phoneNumber("987654321")
                .password("nonexistingpassword")
                .email("non_existing_email@example.com")
                .build();

        assertThrows(EntityNotFoundException.class, () -> userService.update(nonExistingUser));
    }

    @Test
    void checkInvalidUpdate() {
        long expectedId = 2;
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .password("12!@asAS")
                .email("john.doe@example.com")
                .phoneNumber("380984035792")
                .build();
        userService.signup(user);

        User savedUser = userService.findById(expectedId);
        savedUser.setEmail("dimamel28@gmail.com");

        assertThrows(EntityExistsException.class, () -> userService.update(savedUser));
    }

}