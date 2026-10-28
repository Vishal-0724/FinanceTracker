package com.finance.financetracker.repository;

import com.finance.financetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction,Long> {

    Boolean existsByTitleIgnoreCase(String title);

    //Queries
    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal findTotalAmount();

}
