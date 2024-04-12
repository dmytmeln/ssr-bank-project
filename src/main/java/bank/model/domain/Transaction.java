package bank.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @ToString.Exclude
    private BankAccount bankAccount;

    @NotNull(message = "Transaction msg can't be null")
    private String msg;

    @NotNull(message = "Transaction type can't be null")
    @Column(name = "transaction_type")
    private String transactionType;

    @NotNull(message = "Amount of money can't be null")
    @Column(name = "money_amount")
    @Positive(message = "Amount of money have to be bigger than zero")
    private Double moneyAmount;

    @Column(name = "transaction_date")
    @CreationTimestamp
    private Instant transactionDate;

}
