package com.capgemini.customeraccount.model.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Pageable{
    public Sort sort;
    public int offset;
    public int pageNumber;
    public int pageSize;
    public boolean paged;
    public boolean unpaged;
}