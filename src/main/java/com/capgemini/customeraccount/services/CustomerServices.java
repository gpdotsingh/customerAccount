package com.capgemini.customeraccount.services;


import com.capgemini.customeraccount.model.CustomerModel;
import org.springframework.data.domain.Page;

public interface CustomerServices {

    Page<CustomerModel> getCustomers(int pageNo, int pageSize);

    CustomerModel getCustomerByCustomerId(String customerId);
}
