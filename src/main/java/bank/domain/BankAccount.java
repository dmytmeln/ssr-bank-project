package bank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Builder.Default
    private Double balance = 0D;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public String toString() {
        return "BankAccount(id=" + this.id + ", balance=" + this.balance + ", transactions=" + this.transactions + ")";
    }
}