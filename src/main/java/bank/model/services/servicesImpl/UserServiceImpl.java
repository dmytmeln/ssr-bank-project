package bank.model.services.servicesImpl;

import bank.model.domain.User;
import bank.model.repository.AccountRepository;
import bank.model.repository.UserRepository;
import bank.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepo;
    AccountRepository accountRepo;
    @Autowired
    public UserServiceImpl(UserRepository userRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    @Transactional
    public User findById(Long userId) {
        return userRepo.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with  id: %d not found", userId)
                )
        );
    }

    @Override
    @Transactional
    public User signup(User user) {
        // check if a user exists with the email and phone number provided during registration (these fields must be unique)
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        if (userRepo.existsByEmailOrPhoneNumber(email, phoneNumber)) {
            throw new EntityExistsException(
                    String.format("User with email: [%s] or phone number: [%s] already exists!", email, phoneNumber)
            );
        }

        return userRepo.save(user);

    }

    @Override
    @Transactional
    public User login(User user) {
        // find user by email, phone number and password (info that user provide when he tries to log in)
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        return userRepo.findUserByEmailAndPhoneNumberAndPassword(email, phoneNumber, password).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("User with email: [%s], phone number: [%s] and password: [%s] not found!",
                                email, phoneNumber, password)
                )
        );

    }

    @Override
    @Transactional
    public User update(User user) {

        User userDB = findById(user.getId());
        String emailDB = userDB.getEmail();
        String phoneNumberDB = userDB.getPhoneNumber();

        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();

        // check whether new email and phone number (it they are new) are equal to old ones
        boolean sameEmail = Objects.equals(emailDB, email);
        boolean samePhoneNumber = Objects.equals(phoneNumberDB, phoneNumber);
        // if user changed both email and phone number -> check whether user with such info already exists
        if (!sameEmail && !samePhoneNumber) {

            if (userRepo.existsByEmailAndPhoneNumber(email, phoneNumber)) {
                throw new EntityExistsException(
                        String.format("User with email: [%s] and phone number: [%s] already exists!", email, phoneNumber)
                );
            }

        } else if (!sameEmail) { // if user change only email -> check whether user with such email already exists

            if (userRepo.existsByEmail(email)) {
                throw new EntityExistsException(
                        String.format("User with email: [%s] already exists!", email)
                );
            }

        } else if (!samePhoneNumber) { //if user change only phone number -> check whether user with such phone number already exists

            if (userRepo.existsByPhoneNumber(phoneNumber)) {
                throw new EntityExistsException(
                        String.format("User with phone number: [%s] already exists!", phoneNumber)
                );
            }

        }

        // otherwise -> update user
        return userRepo.save(user);

    }

    @Override
    @Transactional
    public void delete(long userId) {
        if (!userRepo.existsById(userId)) {
            throw new EntityNotFoundException(
                    String.format("User with  userId: %d not found", userId)
            );
        }

        userRepo.deleteById(userId);
    }

}