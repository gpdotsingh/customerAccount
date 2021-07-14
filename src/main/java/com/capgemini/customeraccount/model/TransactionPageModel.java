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
    private List<TransactionModel> content;
    private Pageable pageable;
    private int totalPages;
    private int totalElements;
    private boolean last;
    private int size;
    private int number;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private boolean empty;
}










