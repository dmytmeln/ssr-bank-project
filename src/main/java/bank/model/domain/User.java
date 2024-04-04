package bank.model.domain;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User {

    @Transient
    private static final String NAME_REGEX = "^[A-Z][a-z]*(?:-[A-Z][a-z]*)*$";

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "Message should be greater than zero!")
    private Long id;

    @Column(name = "firstname")
    @NotNull(message = "First name can't be null")
    @Pattern(regexp = NAME_REGEX, message = "Invalid first name format")
    private String firstName;

    @Column(name = "lastname")
    @NotNull(message = "Last name can't be null")
    @Pattern(regexp = NAME_REGEX, message = "Invalid last name format")
    private String lastName;

    @Column(name = "pass")
    @NotNull(message = "Password can't be null")
    @Pattern(regexp = "^(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+].*[!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+])[A-Za-z\\d!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+]{8,}$",
            message = "Password must contain at least 2 lowercase letters, 2 uppercase letters, 2 digits, and 2 special symbols, and be at least 8 characters long")
    private String password;

    @Column(unique = true)
    @NotNull(message = "Email can't be null")
    @Pattern(regexp =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Improper email format")
    private String email;

    @Column(name = "phone_number", unique = true)
    @NotNull(message = "Phone number can't be null")
    @Pattern(regexp = "\\d{12}", message = "Phone number has to contain 12 digits")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private BankAccount bankAccount;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Instant creationDate;

}
