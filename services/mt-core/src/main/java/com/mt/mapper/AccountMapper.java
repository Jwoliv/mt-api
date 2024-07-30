package com.mt.mapper;

import com.mt.dto.model_dto.AccountDto;
import com.mt.dto.form_dto.AccountFormDto;
import com.mt.model.transaction.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
    List<AccountDto> toDto(List<Account> account);
    List<AccountFormDto> toFormDto(List<Account> accounts);
}
