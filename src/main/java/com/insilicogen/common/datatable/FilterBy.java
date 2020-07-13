package com.insilicogen.common.datatable;

import java.util.HashSet;
import java.util.Set;

/**
 * filter 필드별 정보
 * 
 */
public class FilterBy {

	private final Set<FilterInfo> filters;
	
	private boolean globalSearch;

	public FilterBy() {
	  filters = new HashSet<FilterInfo>();
	}

	public Set<FilterInfo> getFilters() {
		return filters;
	}

	public void addFilter(String filterColumn, String filterValue , FilterType filterType) {
		filters.add(new FilterInfo(filterColumn, filterValue, filterType));
	}

	public boolean isGlobalSearch() {
		return globalSearch;
	}

	public void setGlobalSearch(boolean globalSearch) {
		this.globalSearch = globalSearch;
	}

}