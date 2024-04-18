package bank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private BankAccount bankAccount;

    @Column
    @CreationTimestamp
    private Instant creationDate;

}
