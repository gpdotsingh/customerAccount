package com.capgemini.customeraccount.repository;

import com.capgemini.customeraccount.entity.CurrentAccountEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CurrentAcountRepo extends CrudRepository<CurrentAccountEntity,Long> {

    Optional<CurrentAccountEntity> findByCustomerId(String custId);

    @Modifying(clearAutomatically = true) // Refresh value after update
    @Query("UPDATE current_account c SET c.amount = (c.amount + :amount) ,  last_updated_time = :lastUpdatedTime WHERE c.customerId = :customerId")
    int updateByCustId(@Param("customerId") String custId,@Param("amount") BigDecimal amount, @Param("lastUpdatedTime")LocalDateTime now);

    @Modifying(clearAutomatically = true) // Refresh value after update
    @Query("UPDATE current_account c SET c.amount = (c.amount - :amount) , last_updated_time = :lastUpdatedTime  WHERE c.customerId = :customerId")
    int updateByCustIdDecrement(@Param("customerId") String custId, @Param("amount") BigDecimal amount, @Param("lastUpdatedTime")LocalDateTime now);

}
