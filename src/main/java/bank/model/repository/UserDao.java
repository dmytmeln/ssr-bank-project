package bank.model.repository;

import bank.configuration.HibernateConfiguration;
import bank.model.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("UserDao")
public class UserDao {

    public UserDao(){}

    public List<User> readAll() {
        return null; // TODO
    }

    public User readById(Long id) {

        Transaction transaction = null;
        User persistentUser = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            persistentUser = Optional.ofNullable((session.get(User.class, id)))
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("User with id: {%d} not found!", id)
                    ));

            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return persistentUser;

    }

    public User read(User user) {
        return null; // TODO
    }

    public Long create(User user) {

        Transaction transaction = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(user);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return user.getId();

    }

    public User update(User user, Long id) {

        User persistentUser = null;
        Transaction transaction = null;
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            persistentUser = Optional.ofNullable((session.get(User.class, id)))
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("User with id: {%d} not found!", id)
                    ));

            persistentUser = persistentUser.toBuilder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();

            session.merge(persistentUser);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return persistentUser;

    }

    public boolean delete(Long id) {
        return false; // TODO
    }

}