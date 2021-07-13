package com.capgemini.customeraccount.model;

import com.capgemini.customeraccount.enums.AccountEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {

    @JsonProperty("account_Number")
    private String accountNumber;
    @JsonProperty("account_type")
    private AccountEnum accountEnum;
    @JsonProperty("balance_amount")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("account_created_on")
    private LocalDateTime accountCreationTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("last_updated_at")
    private LocalDateTime lastUpdatedTime;
    @JsonProperty("transactions")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Page<TransactionModel> transactionModelList;
}
