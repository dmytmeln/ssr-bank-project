package bank.dto;

import bank.domain.Transaction;

public class TransactionTransformer {

    public static Transaction convertToEntity(TransactionForm transactionForm) {
        return Transaction.builder()
                .msg(transactionForm.getMsg())
                .type(transactionForm.getType())
                .moneyAmount(transactionForm.getMoneyAmount())
                .build();
    }

}
