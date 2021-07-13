package com.capgemini.customeraccount.dao;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.model.CustomerModel;

import java.util.Optional;

public interface CustomerDao {

    CustomerModel customerEntityToModel(Optional<CustomerEntity> customerEntity);
}
