package bank.model.services;

import bank.model.domain.User;
import bank.model.services.servicesImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Autowired
    public UserServiceImplTest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Test
    @Transactional
    void checkSignupTest() {
        long expectedId = 2;
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .password("12!@asAS")
                .email("john.doe@example.com")
                .phoneNumber("380984035792")
                .build();

        User savedUser = userService.findById(expectedId);

        assertEquals(expectedId, savedUser.getId());
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(expectedId, savedUser.getBankAccount().getId());
        assertEquals(expectedId, savedUser.getBankAccount().getUser().getId());
    }

    @Test
    @Transactional
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
    void checkLoginInvalidCredentialsTest() {
        User invalidUser = new User();
        invalidUser.setEmail("invalid@example.com");
        invalidUser.setPhoneNumber("380987654321");
        invalidUser.setPassword("Mdm281004");

        assertThrows(EntityNotFoundException.class, () -> userService.login(invalidUser));
    }


    @Test
    @Transactional
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