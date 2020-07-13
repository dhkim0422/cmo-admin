package com.insilicogen.common.datatable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * filter 정보
 * 
 */
@Data @AllArgsConstructor
public class FilterInfo {

    private final String filterColumn;
    private final String filterValue;
    private final FilterType filterType;
	

}