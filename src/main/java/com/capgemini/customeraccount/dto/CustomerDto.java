package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.data.domain.Page;

public interface CustomerDto {

    CustomerModel entityToModel(CustomerEntity customerEntity, AccountModel accountModel);
    Page<CustomerModel> entityToModel(Page<CustomerEntity>  customerEntity);


}
