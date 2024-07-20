package com.mt.mapper;

import com.mt.dto.AccountDto;
import com.mt.model.transaction.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    List<AccountDto> toDto(List<Account> account);
    Account toEntity(AccountDto accountDto);
    List<Account> toEntity(List<AccountDto> accountDto);
}
