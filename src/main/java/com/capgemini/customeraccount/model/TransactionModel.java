package com.capgemini.customeraccount.model;

import com.capgemini.customeraccount.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private TransactionTypeEnum type;
    private BigDecimal amount;
    private String description;
    private String accountNumber;

}
