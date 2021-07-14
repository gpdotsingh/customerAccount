package com.capgemini.customeraccount.model;

import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel {

    private String id;
    private LocalDateTime time;
    private TransactionTypeEnum type;
    private BigDecimal amount;
    private String description;
    private String accountNumber;

}
