package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import com.capgemini.customeraccount.model.AccountModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountTransactionDtoImpl implements AccountTransactionDto{

    public AccountModel entityToModel(CurrentAccountEntity accountEntity) {
        AccountModel accountModel = new AccountModel(accountEntity.getAccountNumber(),accountEntity.getAccountEntity().getAccountEnum(), accountEntity.getAmount(), accountEntity.getAccountCreationTime(), accountEntity.getLastUpdatedTime(), null);
        return accountModel;
    }

    //Transform entity to model
    @Override
    public Optional<AccountModel> getCurrentAccount(Optional<CurrentAccountEntity> currentAccountEntity) {
        Optional<AccountModel> accountModelOptional = Optional.empty();
        // if account is present
        if (currentAccountEntity.isPresent()) {
            CurrentAccountEntity accountEntity = currentAccountEntity.get();
            AccountModel accountModel = entityToModel(accountEntity);
            return Optional.of(accountModel);
        }
        return accountModelOptional;
    }
}
