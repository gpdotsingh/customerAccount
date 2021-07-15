package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.enums.AccountEnum;
import com.capgemini.customeraccount.enums.ExceptionMessageEnum;
import com.capgemini.customeraccount.exception.AccountException;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerDaoImpl implements CustomerDao{
    /**
     *
     * @param customerEntity
     * @return
     */
    @Override
    public CustomerModel customerEntityToModel(Optional<CustomerEntity> customerEntity) {

        List<AccountModel> accounts = customerEntity.orElse(new CustomerEntity())
                .getAccountEntity()
                .stream()
                .map(accountEntity -> {
                            AccountEnum accountEnum = accountEntity.getAccountEnum();
                            String accountNumber = accountEntity.getCurrentAccountEntity().getAccountNumber();
                            BigDecimal amount = accountEntity.getCurrentAccountEntity().getAmount();
                            LocalDateTime lastUpdatedTime = accountEntity.getCurrentAccountEntity().getLastUpdatedTime();
                            LocalDateTime creationTime = accountEntity.getCurrentAccountEntity().getAccountCreationTime();
                            return new AccountModel(accountNumber,accountEnum,amount,creationTime,lastUpdatedTime,null);
                        }
                ).collect(Collectors.toList());

        String customerId = customerEntity.orElseThrow(() -> new AccountException(ExceptionMessageEnum.CUSTOMER_NOT_FOUND.name())).getCustomerId();

        return new CustomerModel(customerId, customerEntity.get().getFirstName(), customerEntity.get().getLastName(), customerEntity.get().getMiddleName(),accounts);

    }
}
