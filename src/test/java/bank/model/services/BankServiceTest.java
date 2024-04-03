//package bank.model.services;
//
//import bank.exceptions.AccountNotFound;
//import bank.exceptions.NotFound;
//import bank.model.domain.BankAccount;
//import bank.model.repository.BankAccountDao;
//import bank.model.repository.TransactionHistoryDao;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class BankServiceTest {
//
//    @Mock
//    private BankAccountDao accountRepoMock;
//
//    @Mock
//    private TransactionHistoryDao transactionRepoMock;
//
//    @Mock
//    private AccountTransactionRepositoryImpl accountTransactionRepoMock;
//
//    @Mock
//    private Validation validationMock;
//
//    private final BankAccount bankAccount = BankAccount.builder()
//            .balance(1000D)
//            .build();
//
//    private final Long id = 1L;
//
//    private BankService service;
//
//    @Before
//    public void setup() throws AccountNotFound {
//
//        service = new BankService(accountRepoMock, transactionRepoMock, accountTransactionRepoMock, validationMock);
//
//        when(validationMock.validateTransactionMsg(null)).thenReturn(null);
//
//        when(accountRepoMock.readById(id)).thenReturn(bankAccount);
//        when(accountTransactionRepoMock.add(any(AccountTransactions.class))).thenReturn(mock(AccountTransactions.class));
//
//        when(transactionRepoMock.add(any(TransactionHistory.class))).thenReturn(mock(TransactionHistory.class));
//
//    }
//
//    @Test
//    public void checkCorrectDeposit() throws NotFound {
//        double moneyAmount = 1000D;
//        service.makeDeposit(id, moneyAmount, null);
//        assertEquals(Double.valueOf(2000D), bankAccount.getBalance());
//    }
//
//    @Test
//    public void checkIncorrectDeposit() {
//        assertThrows(IllegalArgumentException.class, () -> service.makeDeposit(id, -10D, null));
//    }
//
//    @Test
//    public void checkCorrectWithdrawal() throws NotFound {
//        service.makeWithdrawal(id, 1000D, null);
//        assertEquals(Double.valueOf(0), bankAccount.getBalance());
//    }
//
//    @Test
//    public void checkIncorrectWithdrawal() {
//        assertThrows(IllegalArgumentException.class, () -> service.makeWithdrawal(id, 10000D, null));
//    }
//
//}
