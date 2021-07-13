package com.capgemini.customeraccount.dto;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.data.domain.Page;

public interface CustomerDto {

    Page<CustomerModel> entityToModel(Page<CustomerEntity>  customerEntity);
}
