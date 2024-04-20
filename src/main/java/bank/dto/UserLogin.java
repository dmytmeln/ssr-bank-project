package bank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {

    @NotNull(message = "Password can't be null")
    @Pattern(regexp = "^(?=.*[a-z].*[a-z])(?=.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+].*[!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+])[A-Za-z\\d!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+]{8,}$",
            message = "Password must contain at least 2 lowercase letters, 2 uppercase letters, 2 digits, and 2 special symbols, and be at least 8 characters long")
    private String password;

    @NotNull(message = "Email can't be null")
    @Pattern(regexp =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Improper email format")
    private String email;

    @NotNull(message = "Phone number can't be null")
    @Pattern(regexp = "\\d{12}", message = "Phone number has to contain 12 digits")
    private String phoneNumber;

}
