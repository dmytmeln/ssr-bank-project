package bank.model.repository;

import bank.model.domain.BankAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("BankAccountDao")
public class BankAccountDao {

    public BankAccountDao() {
    }

    public List<BankAccount> readAll() {
        return null; // TODO
    }

    public BankAccount readById(Long id) {
        return null; // TODO
    }

    public BankAccount read(BankAccount bankAccount) {
        return null; // TODO
    }

    public BankAccount create(BankAccount bankAccount) {
        return null; // TODO
    }

    public BankAccount update(BankAccount bankAccount, Long id) {
        return null; // TODO
    }

    public boolean delete(Long id) {
        return false; // TODO
    }

}