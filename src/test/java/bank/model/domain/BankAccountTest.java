package bank.model.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountTest {

    private BankAccount validAccount;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    void init() {
        validAccount = BankAccount.builder()
                .id(1L)
                .balance(0D)
                .build();
    }

    @Test
    void testValidAccount() {
        Set<ConstraintViolation<BankAccount>> violations = validator.validate(validAccount);
        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidBalance")
    void testInvalidBalance(Double inputValue, Double errorValue) {
        validAccount.setBalance(inputValue);

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(validAccount);

        assertEquals(1, violations.size());
        assertEquals(errorValue, violations.iterator().next().getInvalidValue());
    }

    private static Stream<Arguments> provideInvalidBalance() {
        return Stream.of(
                Arguments.of(-1D, -1D),
                Arguments.of(-1000D, -1000D),
                Arguments.of(null, null)
        );
    }

}
