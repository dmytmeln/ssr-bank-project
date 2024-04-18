package bank.dto;

import bank.domain.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    @Positive(message = "Id should be greater than zero!")
    private Long id;

    @NotNull(message = "Balance can't be null")
    @PositiveOrZero
    @Builder.Default
    private Double balance = 0D;

    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

}
