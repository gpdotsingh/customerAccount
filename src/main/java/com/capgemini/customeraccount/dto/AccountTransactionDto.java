package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import com.capgemini.customeraccount.model.AccountModel;

import java.util.Optional;

public interface AccountTransactionDto {
    AccountModel entityToModel(CurrentAccountEntity accountEntity);
    Optional<AccountModel> getCurrentAccount(Optional<CurrentAccountEntity> currentAccountEntity);

}
