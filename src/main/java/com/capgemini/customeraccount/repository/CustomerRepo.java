package com.capgemini.customeraccount.repository;

import com.capgemini.customeraccount.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<CustomerEntity,Long> {

    Optional<CustomerEntity> findByCustomerIdIgnoreCase(String custId);
}
