package bank.model.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long id;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<TransactionHistory> transactions;

    @NotNull
    @PositiveOrZero
    @Builder.Default
    private Double balance = 0D;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}