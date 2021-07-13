package com.capgemini.customeraccount.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="customer")
public class CustomerEntity {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Column(unique=true,name="CUSTOMER_ID",length=10)
    private String customerId;
    @NonNull
    @Column(name="FIRST_NAME",length=100)
    private String firstName;
    @NonNull
    @Column(name="LAST_NAME",length=100)
    private String lastName;
    @Column(name="MIDDLE_NAME",length=100)
    private String middleName;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AccountEntity> accountEntity;


    public void setAccountEntity(List<AccountEntity> accountEntity) {
        if(null!= accountEntity)
            accountEntity.
                    stream()
            .forEach(account -> account.setCustomer(this));

        this.accountEntity = accountEntity;
    }

}
