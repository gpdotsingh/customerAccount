package com.capgemini.customeraccount.repository;

import com.capgemini.customeraccount.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo  extends JpaRepository<AccountEntity,Long> {
}
