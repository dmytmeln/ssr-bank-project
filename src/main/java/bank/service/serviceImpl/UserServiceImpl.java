package bank.service.serviceImpl;

import bank.model.BankAccount;
import bank.model.User;
import bank.dto.UserForm;
import bank.dto.UserLogin;
import bank.repository.UserRepository;
import bank.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public User findById(Long userId) {
        return userRepo.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "User with  id: %d not found".formatted(userId)
                )
        );
    }

    @Override
    @Transactional
    public User signup(UserForm userForm) {
        // check if a userForm exists with the email and phone number provided during registration (these fields must be unique)
        String email = userForm.getEmail();
        String phoneNumber = userForm.getPhoneNumber();
        if (userRepo.existsByEmailOrPhoneNumber(email, phoneNumber)) {
            throw new EntityExistsException(
                    "User with email: [%s] or phone number: [%s] already exists!".formatted(email, phoneNumber)
            );
        }

        User savedUser = userRepo.save(modelMapper.map(userForm, User.class));

        BankAccount bankAccount = BankAccount.builder()
                .user(savedUser)
                .build();
        savedUser.setBankAccount(bankAccount);

        return savedUser;

    }

    @Override
    @Transactional
    public User login(UserLogin userLogin) {
        // find user by email, phone number and password (info that user provide when he tries to log in)
        String email = userLogin.getEmail();
        String phoneNumber = userLogin.getPhoneNumber();
        String password = userLogin.getPassword();

        return userRepo.findUserByEmailAndPhoneNumberAndPassword(email, phoneNumber, password).orElseThrow(
                () -> new EntityNotFoundException(
                        "User with email: [%s], phone number: [%s] and password: [%s] not found!"
                                .formatted(email, phoneNumber, password)
                )
        );

    }

    @Override
    @Transactional
    public User update(UserForm userForm, Long userId) {
        alreadyExists(userForm, userId); // method throw exception if such user already exists or if he's not found
        User user = modelMapper.map(userForm, User.class);
        user.setId(userId);
        return userRepo.save(user);
    }

    // method to check whether userForm with such email or/and phone number already exists, because these fields are unique
    @Override
    @Transactional
    public void alreadyExists(UserForm userForm, Long userId) {
        User userDB = findById(userId);

        String email = userForm.getEmail();
        String phoneNumber = userForm.getPhoneNumber();

        // check whether new email and phone number (if they are new) are equal to old ones
        boolean sameEmail = Objects.equals(userDB.getEmail(), email);
        boolean samePhoneNumber = Objects.equals(userDB.getPhoneNumber(), phoneNumber);
        // if userForm changed both email and phone number -> check whether userForm with such info already exists
        if (!sameEmail && !samePhoneNumber) {

            if (userRepo.existsByEmailAndPhoneNumber(email, phoneNumber)) {
                throw new EntityExistsException(
                        "User with email [%s] and phone number [%s] already exists!".formatted(email, phoneNumber)
                );
            }

        } else if (!sameEmail) { // if userForm change only email -> check whether userForm with such email already exists

            if (userRepo.existsByEmail(email)) {
                throw new EntityExistsException(
                        "User with email [%s] already exists!".formatted(email)
                );
            }

        } else if (!samePhoneNumber) { //if userForm change only phone number -> check whether userForm with such phone number already exists

            if (userRepo.existsByPhoneNumber(phoneNumber)) {
                throw new EntityExistsException(
                        "User with phone number [%s] already exists!".formatted(phoneNumber)
                );
            }

        }
    }

    @Override
    @Transactional
    public void delete(long userId) {
        if (!userRepo.existsById(userId)) {
            throw new EntityNotFoundException(
                    "User with  userId [%d] not found".formatted(userId)
            );
        }

        userRepo.deleteById(userId);
    }

}