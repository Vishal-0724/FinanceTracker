package com.finance.financetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.financetracker.model.transactionType;;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    @JsonProperty("Transaction_Id")
    private Long id;
    @JsonProperty("Transaction_Type")
    private transactionType type;
    @JsonProperty("Transaction_Title")
    private String title;
    @JsonProperty("Transaction_Amount")
    private BigDecimal amount;
    @JsonProperty("Transaction_Category")
    private String category;
    @JsonProperty("Transaction_Date")
    private LocalDate TransactionDate;
    @JsonProperty("Transaction_Description")
    private String Description;

}
