package bank.model.repository;

import bank.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailOrPhoneNumberAndPassword(String email, String phoneNumber, String password);

}