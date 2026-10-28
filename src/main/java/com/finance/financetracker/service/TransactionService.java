package com.finance.financetracker.service;

import com.finance.financetracker.dto.TransactionRequestDto;
import com.finance.financetracker.dto.TransactionResponseDto;
import com.finance.financetracker.dto.UpdateTransactionRequestDto;
import com.finance.financetracker.exception.TransactionAlreadyExistsException;
import com.finance.financetracker.exception.TransactionNotExistsException;
import com.finance.financetracker.model.Transaction;
import com.finance.financetracker.model.transactionType;
import com.finance.financetracker.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRespository;

    public TransactionService(TransactionRepository transactionRespository) {
        this.transactionRespository = transactionRespository;
    }

    //map to response dto (common method for all)
    public TransactionResponseDto mapToTransactionResponseDto(Transaction t){
        return new TransactionResponseDto(t.getId(),t.getType(),t.getTitle(),t.getAmount(),t.getCategory(),t.getTransactionDate(),t.getDescription());
    }

    //add/create transaction
    public String createTransaction(TransactionRequestDto transactionRequestDto){

        if(transactionRespository.existsByTitleIgnoreCase(transactionRequestDto.getTitle())){
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
        List<String> allowedFields = List.of("id","title","amount","category","description","type","transactionDate");
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
    public String updateTransaction(Long id, UpdateTransactionRequestDto updateTransactionRequestDto){

        if(transactionRespository.existsByTitleIgnoreCaseAndIdNot(updateTransactionRequestDto.getTitle(),id)){
            throw new TransactionAlreadyExistsException("Title exists already!");
        }

        Transaction tr = transactionRespository.findById(id).orElseThrow(() -> new TransactionNotExistsException("No Transaction exists by this Id!"));

        if(updateTransactionRequestDto.getTitle() != null){
            tr.setTitle(updateTransactionRequestDto.getTitle());
        }
        if(updateTransactionRequestDto.getCategory() != null){
            tr.setCategory(updateTransactionRequestDto.getCategory());
        }
        if(updateTransactionRequestDto.getDescription() != null){
            tr.setDescription(updateTransactionRequestDto.getDescription());
        }
        if(updateTransactionRequestDto.getType() != null){
            tr.setType(updateTransactionRequestDto.getType());
        }
        if(updateTransactionRequestDto.getTransactionDate() != null){
            tr.setTransactionDate(updateTransactionRequestDto.getTransactionDate());
        }
        if(updateTransactionRequestDto.getAmount() != null){
            tr.setAmount(updateTransactionRequestDto.getAmount());
        }

        Transaction uptr = transactionRespository.save(tr);
        return "Transaction with id " + uptr.getId() + " has been updated successfully";

    }

    //delete transaction
    public String deleteTransaction(Long id){
        Transaction tr = transactionRespository.findById(id).orElseThrow(() -> new TransactionNotExistsException("No Transaction exists by this Id!"));
        transactionRespository.deleteById(id);
        return "Transaction has been deleted";
    }

    //total amount
    public BigDecimal sumAmount(){
        BigDecimal total = transactionRespository.findTotalAmount();
        return total;
    }

    //amount by type(income,expense)
    public BigDecimal sumAmountByType(transactionType type){
        BigDecimal totalin = transactionRespository.findTotalAmountByType(type);
        return totalin;
    }

    //balance
    public BigDecimal balanceAmount(){
        BigDecimal income= transactionRespository.findTotalAmountByType(transactionType.INCOME);
        BigDecimal expense =  transactionRespository.findTotalAmountByType(transactionType.EXPENSE);
        return income.subtract(expense);//bigdecimal is immutable so sum1.subtract(sum2)
    }



}
