package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.model.AccountModel;
import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerDtoImpl implements CustomerDto{

    @Autowired
    CustomerDto customerDto;
    @Override
    public CustomerModel entityToModel(CustomerEntity customerEntity, AccountModel accountModel) {

    return null;
    }

    @Override
    public Page<CustomerModel> entityToModel(Page<CustomerEntity> customerEntity) {
        return  customerEntity.map(customerFromDB ->
             new CustomerModel(customerFromDB.getCustomerId(),customerFromDB.getFirstName(),customerFromDB.getLastName(),customerFromDB.getMiddleName())
         );
    }


}
