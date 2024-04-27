package bank.service;

import bank.model.User;
import bank.dto.UserForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void delete(long userId);

    User findById(Long userId);

    User signup(UserForm user);

    User update(UserForm user, Long userId);

    void alreadyExists(UserForm user, Long userId);

    void checkIfUserExistsByEmailOrPhoneNumber(UserForm userForm);


}
