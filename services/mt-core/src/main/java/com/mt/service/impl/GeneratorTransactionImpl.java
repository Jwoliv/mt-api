package com.mt.service.impl;

import com.mt.enums.TypeOperation;
import com.mt.enums.TypeTransaction;
import com.mt.mapper.TransactionMapper;
import com.mt.model.User;
import com.mt.model.transaction.Account;
import com.mt.model.transaction.Category;
import com.mt.model.transaction.Transaction;
import com.mt.repository.AccountRepository;
import com.mt.repository.CategoryRepository;
import com.mt.repository.TransactionRepository;
import com.mt.repository.UserRepository;
import com.mt.request.ChangeTransactionRequest;
import com.mt.service.GeneratorTransaction;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class GeneratorTransactionImpl implements GeneratorTransaction {
    private static final List<TypeTransaction> USUAL_TRANSACTION_TYPES = List.of(TypeTransaction.EARNING, TypeTransaction.SPENDING);

    @Setter(onMethod = @__({@Autowired}))
    private UserRepository userRepository;
    @Setter(onMethod = @__({@Autowired}))
    private AccountRepository accountRepository;
    @Setter(onMethod = @__({@Autowired}))
    private CategoryRepository categoryRepository;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionMapper transactionMapper;
    @Setter(onMethod = @__({@Autowired}))
    private TransactionRepository transactionRepository;

    @Override
    public Transaction getTransaction(ChangeTransactionRequest request, String email, TypeOperation operation) {
        return isUsualTransaction(request)
                ? handleUsualTransaction(request, email, operation)
                : handleTransferTransaction(request, email, operation);
    }

    private Transaction handleUsualTransaction(ChangeTransactionRequest request, String email, TypeOperation operation) {
        return Objects.equals(operation, TypeOperation.CREATE)
                ? initUsualTransaction(request, email)
                : updateUsualTransaction(request, email);
    }

    private Transaction updateUsualTransaction(ChangeTransactionRequest request, String email) {
        var data = fetchTransactionData(request, email);
        var transactionDb = transactionRepository.getUserTransactionById(email, request.getId());
        var transaction = transactionMapper.mapToUsualTransactionToUpdate(request, data.user, data.category, data.account, transactionDb.getCreatedAt());
        var differAmount = transaction.getAmount().subtract(transactionDb.getAmount());
        updateAccountByUsualTransactions(differAmount, request.getType(), data.account);
        return transactionRepository.save(transaction);
    }

    private Transaction initUsualTransaction(ChangeTransactionRequest request, String email) {
        var data = fetchTransactionData(request, email);
        var transaction = transactionMapper.mapToUsualTransactionToCreate(request, data.user, data.category, data.account);
        updateAccountByUsualTransactions(request.getAmount(), request.getType(), data.account);
        return transactionRepository.save(transaction);
    }

    private Transaction handleTransferTransaction(ChangeTransactionRequest request, String email, TypeOperation operation) {
        return Objects.equals(operation, TypeOperation.CREATE)
                ? initTransferTransaction(request, email)
                : updateTransferTransaction(request, email);
    }

    private Transaction updateTransferTransaction(ChangeTransactionRequest request, String email) {
        var transactionDb = transactionRepository.findById(request.getId());
        if (transactionDb.isEmpty()) return null;
        var data = fetchTransactionData(request, email);
        var transaction = transactionMapper.mapToTransferTransactionToUpdate(
                request, data.senderAccount, data.receiverAccount, data.user, data.category, transactionDb.get().getCreatedAt()
        );
        updateAccountsByTransferTransactionUpdate(transaction, data.senderAccount, data.receiverAccount, transactionDb.get());
        return transactionRepository.save(transaction);
    }

    private void updateAccountsByTransferTransactionUpdate(Transaction transaction, Account senderAccount, Account receiverAccount, Transaction transactionDb) {
        var differAmount = transaction.getAmount().subtract(transactionDb.getAmount());
        senderAccount.changeBalanceByTransfer(differAmount, receiverAccount);
        accountRepository.saveAll(List.of(senderAccount, receiverAccount));
    }

    private Transaction initTransferTransaction(ChangeTransactionRequest request, String email) {
        var transactionData = fetchTransactionData(request, email);
        var transaction = transactionMapper.mapToTransferTransactionToCreate(
                request, transactionData.senderAccount, transactionData.receiverAccount,
                transactionData.user, transactionData.category
        );
        updateAccountsByTransferTransaction(transaction, transactionData.senderAccount, transactionData.receiverAccount);
        return transactionRepository.save(transaction);
    }

    private Boolean isUsualTransaction(ChangeTransactionRequest request) {
        return USUAL_TRANSACTION_TYPES.contains(request.getType());
    }

    private void updateAccountsByTransferTransaction(Transaction transaction, Account senderAccount, Account receiverAccount) {
        senderAccount.changeBalanceByTransfer(transaction.getAmount(), receiverAccount);
        accountRepository.saveAll(List.of(senderAccount, receiverAccount));
    }

    private void updateAccountByUsualTransactions(BigDecimal amount, TypeTransaction typeTransaction, Account account) {
        account.changeBalanceByUsual(amount, typeTransaction);
        accountRepository.save(account);
    }

    private TransactionChangeData fetchTransactionData(ChangeTransactionRequest request, String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        if (Objects.equals(request.getType(), TypeTransaction.TRANSFER)) {
            var senderAccount = accountRepository.findById(request.getSenderAccountId()).orElse(null);
            var receiverAccount = accountRepository.findById(request.getReceiverAccountId()).orElse(null);
            var category = categoryRepository.findById(3L).orElse(null); // 3 - id of transfer category
            return new TransactionChangeData(senderAccount, receiverAccount, user, category);
        }
        var account = accountRepository.findById(request.getAccountId()).orElse(null);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        return new TransactionChangeData(user, category, account);

    }

    @Data
    private static final class TransactionChangeData {
        private Account senderAccount;
        private Account receiverAccount;
        private User user;
        private Category category;
        private Account account;

        private TransactionChangeData(Account senderAccount, Account receiverAccount, User user, Category category) {
            this.senderAccount = senderAccount;
            this.receiverAccount = receiverAccount;
            this.user = user;
            this.category = category;
        }

        private TransactionChangeData(User user, Category category, Account account) {
            this.user = user;
            this.category = category;
            this.account = account;
        }

    }
}