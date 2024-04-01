package bank.model.repository;

import bank.model.domain.TransactionHistory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("TransactionHistoryDao")
public class TransactionHistoryDao  {

    public List<TransactionHistory> readAll() {
        return null; // TODO
    }

    public TransactionHistory readById(Long id) {
        return null; // TODO
    }

    public TransactionHistory read(TransactionHistory transactionHistory) {
        return null; // TODO
    }

    public TransactionHistory create(TransactionHistory transactionHistory) {
        return null; // TODO
    }

    public TransactionHistory update(TransactionHistory transactionHistory, Long id) {
        return null; // TODO
    }

    public boolean delete(Long id) {
        return false; // TODO
    }

}
