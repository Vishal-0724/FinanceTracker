package com.finance.financetracker.dto;

import com.finance.financetracker.model.transactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TransactionRequestDto {

    @NotNull(message = "Type should not be blank") //enums use null
    private transactionType type;
    @NotBlank(message = "Title should not be blank")
    private String title;
    @DecimalMin(value ="0.01",message = "amount should be greater than 0.01")
    @NotNull(message = "Amount should not be blank")
    private BigDecimal amount;
    @NotBlank(message = "Category should not be blank")
    private String category;
    @NotNull(message = "Date should not be blank")
    private LocalDate transactionDate;
    @NotBlank(message = "Description should not be blank")
    private String description;

}
