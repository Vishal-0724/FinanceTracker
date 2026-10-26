package com.finance.financetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity //to generate table(map this class as data model)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//unique id
    private Long id;

    @Enumerated(EnumType.STRING)//user defined values
    private transactionType type;

    @Column(unique = true)
    private String title;

    private BigDecimal amount;
    private String category;
    private LocalDate transactionDate;
    private String description;

}
