package com.capgemini.customeraccount.model.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Sort{
    public boolean sorted;
    public boolean unsorted;
    public boolean empty;
}