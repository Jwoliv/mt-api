package com.mt.mapper;

import com.mt.dto.TransactionDashboardDto;
import com.mt.dto.TransactionDto;
import com.mt.dto.model_dto.CreatedTransaction;
import com.mt.model.User;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Category;
import com.mt.model.transaction.Transaction;
import com.mt.request.ChangeTransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface TransactionMapper {

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "accountName", source = "account.name")
    TransactionDashboardDto mapToDashboardDto(Transaction transactions);

    List<TransactionDashboardDto> mapToDashboardDto(List<Transaction> transactions);

    @Mapping(target = "accountName", source = "account.name")
    @Mapping(target = "categoryName", source = "category.name")
    CreatedTransaction mapToCreateTransaction(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "receiverAccount", source = "receiverAccount")
    @Mapping(target = "date", expression = "java(request.getDate())")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Transaction mapToTransferTransactionToCreate(ChangeTransactionRequest request, Account account, Account receiverAccount, User user, Category category);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "receiverAccount", source = "receiverAccount")
    @Mapping(target = "date", expression = "java(request.getDate())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Transaction mapToTransferTransactionToUpdate(ChangeTransactionRequest request, Account account, Account receiverAccount, User user, Category category, LocalDateTime createdAt);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "receiverAccount", ignore = true)
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "date", expression = "java(request.getDate())")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Transaction mapToUsualTransactionToCreate(ChangeTransactionRequest request, User user, Category category, Account account);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "receiverAccount", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "date", expression = "java(request.getDate())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    Transaction mapToUsualTransactionToUpdate(ChangeTransactionRequest request, User user, Category category, Account account, LocalDateTime createdAt);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "receiverAccountId", source = "receiverAccount.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "accountName", source = "account.name")
    @Mapping(target = "receiverAccountName", source = "receiverAccount.name")
    TransactionDto toTransactionDto(Transaction transaction);
}
