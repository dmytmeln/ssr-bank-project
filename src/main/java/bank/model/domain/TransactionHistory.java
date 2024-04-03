package bank.model.domain;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_histories")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @ToString.Exclude
    private BankAccount bankAccount;

    @NotNull(message = "Transaction msg can't be null")
    private String msg;

    @NotNull(message = "Transaction type can't be null")
    @NotBlank(message = "Transaction type can't be blank")
    @Column(name = "transaction_type")
    private String transactionType;

    @NotNull(message = "Amount of money can't be null")
    @Column(name = "money_amount")
    @Positive
    private Double moneyAmount;

    @Column(name = "transaction_date")
    @CreationTimestamp
    private Instant transactionDate;

}
