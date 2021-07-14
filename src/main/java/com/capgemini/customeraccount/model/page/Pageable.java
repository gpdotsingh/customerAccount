package com.capgemini.customeraccount.model.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Pageable{
    private Sort sort;
    private int offset;
    private int pageNumber;
    private int pageSize;
    private boolean paged;
    private boolean unpaged;
}