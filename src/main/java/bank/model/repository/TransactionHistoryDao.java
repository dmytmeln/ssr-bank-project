package bank.model.repository;

import bank.configuration.HibernateConfiguration;
import bank.model.domain.TransactionHistory;
import bank.model.domain.User;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("TransactionHistoryDao")
public class TransactionHistoryDao  {

    public List<TransactionHistory> readAll() {

        Transaction transaction = null;
        List<TransactionHistory> allTransactions = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            //language=JPQL
            String sqlGetAll = "SELECT t FROM transaction_histories t";
            allTransactions = session.createQuery(sqlGetAll, TransactionHistory.class).getResultList();

            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return allTransactions;

    }

    public List<TransactionHistory> readByBankId(Long id) {

        Transaction transaction = null;
        List<TransactionHistory> allAccountTransactions = null;

        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TransactionHistory> query = cb.createQuery(TransactionHistory.class);
            Root<TransactionHistory> root = query.from(TransactionHistory.class);
            query.select(root).where(
                    cb.equal(root.get("bank_account_id"), id)
            );

            allAccountTransactions = session.createQuery(query).getResultList();

            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return allAccountTransactions;

    }

    public TransactionHistory create(TransactionHistory transactionHistory) {
        return null; // TODO
    }

}
