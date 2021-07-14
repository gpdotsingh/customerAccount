package com.capgemini.customeraccount.model.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Sort{
    private boolean sorted;
    private boolean unsorted;
    private boolean empty;
}