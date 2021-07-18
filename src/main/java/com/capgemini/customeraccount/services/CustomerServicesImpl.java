package com.capgemini.customeraccount.services;


import com.capgemini.customeraccount.dao.CustomerDao;
import com.capgemini.customeraccount.dto.CustomerDto;
import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.exception.CustomerNotFoundException;
import com.capgemini.customeraccount.model.CustomerModel;
import com.capgemini.customeraccount.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    CustomerRepo customerRepo;
   @Autowired
    CustomerDao customerDao;
    @Autowired
    CustomerDto customerDto;

    @Override
    public Page<CustomerModel> getCustomers(int pageNo, int pageSize) {
        Page<CustomerEntity> customerEntityPage = customerRepo
                .findAll(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "customerId")));
        return customerDto.entityToModel(customerEntityPage);
    }
    @Override
    public CustomerModel getCustomerByCustomerId(String customerId) {

        Optional<CustomerEntity> customerEntity = customerRepo
                .findByCustomerIdIgnoreCase(customerId.trim().toLowerCase(Locale.ROOT));
        if (customerEntity.isPresent()) {
            return customerDao.customerEntityToModel(customerEntity);
        }
        throw new CustomerNotFoundException(customerId);
    }


}
