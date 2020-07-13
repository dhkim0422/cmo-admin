package com.insilicogen.common.datatable;

import javax.servlet.http.HttpServletRequest;
import lombok.Data;

/**
 * 데이타 테이블 컬럼 헤더정보
 *
 */
@Data
public class DataTableColumnSpecs {
	
	/** The index. */
	private int index;
	
	/** The data. */
	private String data;
	
	/** The name. */
	private String name;
	
	/** The searchable. */
	private boolean searchable;
	
	/** The orderable. */
	private boolean orderable;
	
	/** The search. */
	private String search;
	
	/** The regex. */
	private boolean regex;
	
	/** The sort dir. */
	private String sortDir;
	
	private FilterType filterType;
	
	
	/**
	 * Instantiates a new data table column specs.
	 *
	 * @param request the request
	 * @param i the i
	 */
	public DataTableColumnSpecs(HttpServletRequest request, int i) {
		this.setIndex(i);
		prepareColumnSpecs(request, i);
	}

		
	/**
	 * Prepare column specs.
	 *
	 * @param request the request
	 * @param i the i
	 */
	private void prepareColumnSpecs(HttpServletRequest request, int i) {
		
		this.setData(request.getParameter("columns["+ i +"][data]"));
		this.setName(request.getParameter("columns["+ i +"][name]"));
		this.setOrderable(Boolean.valueOf(request.getParameter("columns["+ i +"][orderable]")));
		this.setRegex(Boolean.valueOf(request.getParameter("columns["+ i +"][search][regex]")));
		this.setSearch(request.getParameter("columns["+ i +"][search][value]"));
		this.setSearchable(Boolean.valueOf(request.getParameter("columns["+ i +"][searchable]")));
		
		int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));
		String sortDir = request.getParameter("order[0][dir]");
		
		if(i == sortableCol) {
			this.setSortDir(sortDir);
		}
	}
	
}