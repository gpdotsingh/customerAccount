package com.capgemini.customeraccount.controller.common;

import com.capgemini.customeraccount.entity.CustomerEntity;
import com.capgemini.customeraccount.repository.CustomerRepo;

import java.util.ArrayList;
import java.util.List;

public class DataSetUp {


    public void datasetUp(CustomerRepo customerRepo) {
        customerRepo.deleteAll();

        CustomerEntity customerEntity = new CustomerEntity();
        List<CustomerEntity> list = new ArrayList<>();

        customerEntity.setId(1l);
        customerEntity.setCustomerId("customer1");
        customerEntity.setFirstName("Test");
        customerEntity.setLastName("Test");

        customerRepo.save(customerEntity);
        customerEntity = new CustomerEntity();

        customerEntity.setId(2l);
        customerEntity.setCustomerId("customer2");
        customerEntity.setFirstName("Test");
        customerEntity.setLastName("Test");

        customerRepo.save(customerEntity);
        customerEntity = new CustomerEntity();

        customerEntity.setId(3l);
        customerEntity.setCustomerId("customer3");
        customerEntity.setFirstName("Test");
        customerEntity.setLastName("Test");

        customerRepo.save(customerEntity);
        customerEntity = new CustomerEntity();

        customerEntity.setId(4l);
        customerEntity.setCustomerId("customer4");
        customerEntity.setFirstName("Test");
        customerEntity.setLastName("Test");

        customerRepo.save(customerEntity);
        customerEntity = new CustomerEntity();

        customerEntity.setId(5l);
        customerEntity.setCustomerId("customer5");
        customerEntity.setFirstName("Test");
        customerEntity.setLastName("Test");
        list.add(customerEntity);
        customerRepo.save(customerEntity);
    }

}
