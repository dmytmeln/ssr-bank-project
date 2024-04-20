package bank.service;

import bank.domain.User;
import bank.dto.UserForm;
import bank.dto.UserLogin;

public interface UserService {

    void delete(long userId);

    User findById(Long userId);

    User signup(UserForm user);

    User login(UserLogin userLogin);

    User update(UserForm user, Long userId);

    void alreadyExists(UserForm user, Long userId);


}
