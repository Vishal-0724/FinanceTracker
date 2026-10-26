package com.finance.financetracker.service;

import com.finance.financetracker.dto.TransactionRequestDto;
import com.finance.financetracker.dto.TransactionResponseDto;
import com.finance.financetracker.exception.TransactionAlreadyExistsException;
import com.finance.financetracker.exception.TransactionNotExistsException;
import com.finance.financetracker.model.Transaction;
import com.finance.financetracker.repository.TransactionRespository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRespository transactionRespository;

    public TransactionService(TransactionRespository transactionRespository) {
        this.transactionRespository = transactionRespository;
    }

    //map to response dto (common method for all)
    public TransactionResponseDto mapToTransactionResponseDto(Transaction t){
        return new TransactionResponseDto(t.getId(),t.getType(),t.getTitle(),t.getAmount(),t.getCategory(),t.getTransactionDate(),t.getDescription());
    }

    //add/create transaction
    public String createTransaction(TransactionRequestDto transactionRequestDto){

        if(transactionRespository.existsByTitleContainingIgnoreCase(transactionRequestDto.getTitle())){
            throw new TransactionAlreadyExistsException("Transaction already exists");
        }

        Transaction transaction = new Transaction();
        transaction.setCategory(transactionRequestDto.getCategory());
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setDescription(transactionRequestDto.getDescription());
        transaction.setTitle(transactionRequestDto.getTitle());
        transaction.setTransactionDate(transactionRequestDto.getTransactionDate());
        transaction.setType(transactionRequestDto.getType());
        Transaction newTransaction = transactionRespository.save(transaction);

        return "Transaction " + newTransaction.getId() + " with title " + newTransaction.getTitle() +" has been created successfully!";
    }

    //get transaction by ID
    public TransactionResponseDto getTransactionById(Long id){
        Transaction tr = transactionRespository.findById(id).orElseThrow(() -> new TransactionNotExistsException("No Transaction exists by this Id!"));
        return mapToTransactionResponseDto(tr);
    }

    //get all transaction
    public Page<TransactionResponseDto> getAllTransaction(Integer size,Integer page,String direction,String sortBy) {

        //list of sortBy(actual elements/fields in the table)
        List<String> allowedFields = List.of("id","title","amount","category","Description","type","TransactionDate");
        if(!allowedFields.contains(sortBy)){
            throw new IllegalArgumentException("Field not available!");
        }

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);//take direction as param
        Sort sort =  Sort.by(sortDirection,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);//put page first,size second
        Page<Transaction> tAllpage = transactionRespository.findAll(pageable);

        return tAllpage.map(transaction -> mapToTransactionResponseDto(transaction));
    }

    //update transaction
//    public String updateTransaction(Long id){

//      Transaction tr = transactionRespository.findById(id).orElseThrow(() -> new TransactionNotExistsException("No Transaction exists by this Id!")){
//
//      }
//    }

    //delete transaction
    public String deleteTransaction(Long id){
        Transaction tr = transactionRespository.findById(id).orElseThrow(() -> new TransactionNotExistsException("No Transaction exists by this Id!"));
        transactionRespository.deleteById(id);
        return "Transaction has been deleted";
    }



}
