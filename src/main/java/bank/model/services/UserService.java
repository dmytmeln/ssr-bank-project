package bank.model.services;

import bank.model.domain.User;

public interface UserService {

    void delete(long userId);

    User findById(Long userId);

    User signup(User user);

    User login(User user);

    User update(User user);


}
