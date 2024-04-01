package bank.model.services;

import bank.model.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Qualifier("Validation")
public class Validation {

    public User validate(User user) {

        Objects.requireNonNull(user, "You haven't entered any field");

        String firstName = validateName(user.getFirstName());
        String lastName = validateName(user.getLastName());
        String phoneNumber = validatePhoneNumber(user.getPhoneNumber());
        String email = validateEmail(user.getEmail());
        String pass = validatePass(user.getPassword());

        return user.toBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .password(pass)
                .email(email)
                .build();

    }

    public String validateName(String name) {

        String exceptionMsg = "Incorrect name";

        Objects.requireNonNull(name, exceptionMsg);

        name = name.trim();
        if (name.isBlank() ||
            !name.equals(name.replaceAll("[^-a-zA-Z]", ""))) {
            throw new IllegalArgumentException(exceptionMsg);
        }

        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String validatePhoneNumber(String phoneNumber) {

        String exceptionMsg = "Incorrect Phone Number";

        Objects.requireNonNull(phoneNumber, exceptionMsg);

        phoneNumber = phoneNumber.trim().replaceAll("\\D", "");

        if (phoneNumber.isBlank() || phoneNumber.length() != 12) {
            throw new IllegalArgumentException(exceptionMsg);
        }

        return phoneNumber;

    }

    public String validateEmail(String email) {

        String exceptionMsg = "Incorrect email";

        Objects.requireNonNull(email, exceptionMsg);

        if (email.isBlank()) {
            throw new IllegalArgumentException(exceptionMsg);
        }

        String regex = "^(.+)@(.+\\.[a-zA-Z]+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            return email;
        } else {
            throw new IllegalArgumentException(exceptionMsg);
        }

    }

    public String validatePass(String pass) {

        String exceptionMsg = "Incorrect password";

        Objects.requireNonNull(pass, exceptionMsg);

        if (pass.isBlank()) {
            throw new IllegalArgumentException(exceptionMsg);
        }

        pass = pass.trim();

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);

        if (matcher.matches()) {
            return pass;
        } else {
            throw new IllegalArgumentException(exceptionMsg);
        }

    }

    public String validateTransactionMsg(String transactionMsg) {
        return transactionMsg == null || transactionMsg.isBlank() ? "Standard Transaction Message" : transactionMsg;
    }

}
