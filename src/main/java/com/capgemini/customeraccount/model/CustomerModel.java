package com.capgemini.customeraccount.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    private String custId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String middleName;
    private List<AccountModel> accounts;

    public CustomerModel(String custId, String firstName, String lastName, String middleName) {
     this.custId = custId;
     this.firstName =firstName;
     this.lastName = lastName;
     this.middleName = middleName;
    }
}
