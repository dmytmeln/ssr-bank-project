package bank.model.repository;

import bank.configuration.HibernateConfiguration;
import bank.model.domain.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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

        Transaction transaction = null;
        List<User> allUsers = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            //language=JPQL
            String sqlGetAll =
                    """
                    SELECT u
                    FROM users u;
                    """;
            allUsers = session.createQuery(sqlGetAll, User.class).getResultList();

            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return allUsers;

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

    public List<User> read(User user) {

        List<User> allMatchedUsers = null;
        Transaction transaction = null;
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root)
                    .where(cb.or(
                            cb.equal(root.get("email"), user.getEmail()),
                            cb.equal(root.get("phoneNumber"), user.getPhoneNumber())
                    ));
            allMatchedUsers = session.createQuery(query).getResultList();

            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return allMatchedUsers;
    }

    public Long create(User user) {

        Transaction transaction = null;
        Long id = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            id = (Long) session.save(user);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return id;

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

        User persistentUser = null;
        Transaction transaction = null;
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            persistentUser = Optional.ofNullable((session.get(User.class, id)))
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("User with id: {%d} not found!", id)
                    ));
            session.delete(persistentUser);

            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return true;

    }

}