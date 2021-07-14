package com.capgemini.customeraccount.controller;


import com.capgemini.customeraccount.model.CustomerModel;
import com.capgemini.customeraccount.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    @Autowired
    CustomerServices customerservices;

    @GetMapping
    public Page<CustomerModel> cutomer(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                        @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return customerservices.getCustomers(pageNo,pageSize);
    }

    @GetMapping("{customerId}")
    public CustomerModel cutomerInfo(@PathVariable(name = "customerId", required = true) String customerId){
        return customerservices.getCustomerByCustomerId(customerId);
    }
}
