package com.finance.financetracker.controller;

import com.finance.financetracker.dto.TransactionRequestDto;
import com.finance.financetracker.dto.UpdateTransactionRequestDto;
import com.finance.financetracker.service.TransactionService;
import jakarta.validation.Valid;;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionRequestDto transactionRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionRequestDto));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllTransaction(@RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "asc") String direction,
                                               @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(transactionService.getAllTransaction(size, page, direction, sortBy));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody UpdateTransactionRequestDto updateTransactionRequestDto){
        return ResponseEntity.ok(transactionService.updateTransaction(id,updateTransactionRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransactions(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.deleteTransaction(id));
    }

}
