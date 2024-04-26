package bank.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BankAccount bankAccount;

    @Column
    private String msg;

    @Column
    private String type;

    @Column
    @Builder.Default
    private Double moneyAmount = 0D;

    @Column
    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    private Instant transactionDate;

}
