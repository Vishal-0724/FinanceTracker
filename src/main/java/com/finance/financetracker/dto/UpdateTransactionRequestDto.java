package com.finance.financetracker.dto;

import com.finance.financetracker.model.transactionType;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateTransactionRequestDto {

    private transactionType type;
    private String title;
    @DecimalMin(value ="0.01",message = "amount should be greater than 0.01")
    private BigDecimal amount;
    private String category;
    private LocalDate transactionDate;
    private String description;

}
