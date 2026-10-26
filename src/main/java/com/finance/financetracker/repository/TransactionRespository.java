package com.finance.financetracker.repository;

import com.finance.financetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRespository extends JpaRepository<Transaction,Long> {

    Boolean existsByTitleIgnoreCase(String title);

}
