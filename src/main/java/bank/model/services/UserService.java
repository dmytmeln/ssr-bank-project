//package bank.model.services;
//
//import bank.exceptions.AccountAlreadyExists;
//import bank.exceptions.UserAlreadyExists;
//import bank.exceptions.UserNotFound;
//import bank.model.domain.BankAccount;
//import bank.model.domain.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
//@Service
//@Qualifier("UserService")
//public class UserService {
//
//    private final UserRepository userRepo;
//
//    private final BankAccountRepository accountRepo;
//
//    private final Validation validation;
//
//    @Autowired
//    public UserService(@Qualifier("UserRepositoryImpl") UserRepository userRepo,
//                       @Qualifier("BankAccountDao") BankAccountRepository accountRepo, Validation validation) {
//        this.userRepo = userRepo;
//        this.accountRepo = accountRepo;
//        this.validation = validation;
//    }
//
//    public void add(User user) throws UserAlreadyExists, AccountAlreadyExists {
//
//        user = validation.validate(user);
//        try {
//            // if user was found (method doesn't throw UserNotFound exception) we need to throw exception UserAlreadyExists,
//            // what means that such user can't be created
//            userRepo.find(user);
//            throw new UserAlreadyExists("Such user already exists");
//
//        } catch (UserNotFound e) {
//            // if user haven't been found (method throw UserNotFound exception) we can add him and his bank account to DB
//            user = userRepo.add(user);
//            accountRepo.add(BankAccount.builder().userId(user.getId()).build());
//        }
//
//    }
//
//    public User find(User user) throws UserNotFound {
//
//        String validatedPhoneNumber;
//        try {
//            validatedPhoneNumber = validation.validatePhoneNumber(user.getPhoneNumber());
//        } catch (IllegalArgumentException | NullPointerException e) {
//            validatedPhoneNumber = "";
//        }
//
//        user = user.toBuilder()
//                .phoneNumber(validatedPhoneNumber)
//                .build();
//
//        return userRepo.find(user).get(0);
//
//    }
//
//    public User find(Long userId) throws UserNotFound {
//        return userRepo.findById(userId);
//    }
//
//    public User update(User user, Long id) throws UserNotFound, UserAlreadyExists {
//        try {
//            user = validation.validate(user);
//
//            List<User> users = userRepo.find(user);
//
//            int foundUsersNumber = users.size();
//            boolean isTheSameUser = foundUsersNumber == 1 && Objects.equals(users.get(0).getId(), id);
//            if (foundUsersNumber >= 2 || !isTheSameUser) {
//                throw new UserAlreadyExists(
//                        String.format("User with such email: %s or/and phone number: %s already exists!",
//                                user.getEmail(), user.getPhoneNumber())
//                );
//            }
//
//            return userRepo.update(user, id);
//
//        } catch (UserNotFound e) {
//            return userRepo.update(user, id);
//        }
//
//    }
//
//}