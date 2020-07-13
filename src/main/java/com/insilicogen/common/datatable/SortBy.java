package com.insilicogen.common.datatable;

import java.util.HashMap;
import java.util.Map;

/**
 * 소팅 컬럼
 */
public class SortBy {
	
	
	private final Map<String, SortOrder> sorts;
	
	public SortBy() {
		sorts = new HashMap<String, SortOrder>();
	}


	public Map<String, SortOrder> getSorts() {
		return sorts;
	}
	
	public void addSort(String sortBy) {
		this.addSort(sortBy, SortOrder.ASC);
	}
	
	public void addSort(String sortBy, SortOrder sortOrder) {
		sorts.put(sortBy, sortOrder);
	}

}