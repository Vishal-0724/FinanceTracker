package com.finance.financetracker.repository;

import com.finance.financetracker.model.Transaction;
import com.finance.financetracker.model.transactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Boolean existsByTitleIgnoreCase(String title);
    Boolean existsByTitleIgnoreCaseAndIdNot(String title,Long id);

    //Queries
    @Query("SELECT SUM(t.amount) FROM Transaction t")
    BigDecimal findTotalAmount();

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
    BigDecimal findTotalAmountByType(@Param("type") transactionType type);//param maps to :type

}
