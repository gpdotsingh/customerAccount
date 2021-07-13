package com.capgemini.customeraccount.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="current_account")
public class CurrentAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NonNull
    @Column(unique=true,name="CUSTOMER_ID",length=10)
    private String customerId;
    @Column(unique=true,name = "ACCOUNT_NUMBER", nullable = false,length=30)
    private String accountNumber;
    @Column( nullable = false)
    @Min(value = 0)
    private BigDecimal amount;
    @Column(name = "ACCOUNT_CREATION_TIME", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime accountCreationTime;
    @Column(name = "LAST_UPDATED_TIME", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdatedTime;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = "curraccount"+this.customerId ;
    }
}
