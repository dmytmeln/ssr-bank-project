package bank.model.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    @NotNull(message = "First name can't be null")
    @NotBlank(message = "First name can't be blank")
    private String firstName;

    @Column(name = "lastname")
    @NotNull(message = "Last name can't be null")
    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @Column(name = "pass")
    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be blank")
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be blank")
    @Email
    private String email;

    @Column(name = "phone_number", unique = true)
    @NotNull(message = "Phone number can't be null")
    @NotBlank(message = "Phone number can't be blank")
    private String phoneNumber;

    @NotNull
    @Column(name = "creation_date")
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @OneToOne(mappedBy = "user", optional = false, cascade = CascadeType.ALL)
    private BankAccount bankAccount;

}
