package com.capgemini.customeraccount.model;




import com.capgemini.customeraccount.model.page.Pageable;
import com.capgemini.customeraccount.model.page.Sort;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TransactionPageModel{
    public List<TransactionModel> content;
    public Pageable pageable;
    public int totalPages;
    public int totalElements;
    public boolean last;
    public int size;
    public int number;
    public Sort sort;
    public boolean first;
    public int numberOfElements;
    public boolean empty;
}










