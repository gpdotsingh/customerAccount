package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerDtoImpl implements CustomerDto{

    @Override
    public Page<CustomerModel> entityToModel(Page<CustomerEntity> customerEntity) {
        return  customerEntity.map(customerFromDB ->
             new CustomerModel(customerFromDB.getCustomerId()
                     ,customerFromDB.getFirstName()
                     ,customerFromDB.getLastName()
                     ,customerFromDB.getMiddleName())
         );
    }


}
