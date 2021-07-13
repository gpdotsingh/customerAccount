package com.capgemini.customeraccount.entity;


import com.capgemini.customeraccount.enums.AccountEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="account")
public class AccountEntity {
    @Id
    @GeneratedValue
    private Long id;
    private AccountEnum accountEnum;
    @ManyToOne
    private CustomerEntity customer;
    @OneToOne(mappedBy = "accountEntity", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private CurrentAccountEntity currentAccountEntity;
}
