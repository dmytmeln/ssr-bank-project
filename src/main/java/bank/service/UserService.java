package bank.service;

import bank.domain.User;
import bank.dto.UserLogin;

public interface UserService {

    void delete(long userId);

    User findById(Long userId);

    User signup(User user);

    User login(UserLogin userLogin);

    User update(User user);

    void alreadyExists(User user);


}
