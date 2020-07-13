package com.insilicogen.common.datatable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import com.insilicogen.common.util.AppUtil;
import lombok.Data;

/**
 * 데이타테이블 Requests
 *
 */
@Data
public class DataTableRequest {
	
	/** The unique id. */
	private String uniqueId;
	
	/** The draw. */
	private String draw;
	
	/** The start. */
	private Integer start;
	
	/** The length. */
	private Integer length;
	
	/** The search. */
	private String search;
	
	/** The regex. */
	private boolean regex;

	/** The columns. */
	private List<DataTableColumnSpecs> columns;
	
	/** The order. */
	private DataTableColumnSpecs order;
	
	/** The is global search. */
	private boolean isGlobalSearch;
	
	private ColumnType columnType;

	/**
	 * Instantiates a new data table request.
	 *
	 * @param request the request
	 */
	public DataTableRequest(HttpServletRequest request , ColumnType columnType) {
	    this.columnType = columnType;
		prepareDataTableRequest(request);
	}

	/**
	 * Prepare data table request.
	 *
	 * @param request the request
	 */
	private void prepareDataTableRequest(HttpServletRequest request) {
		
		Enumeration<String> parameterNames = request.getParameterNames();
    	
    	if(parameterNames.hasMoreElements()) {
    		
    		this.setStart(Integer.parseInt(StringUtils.defaultString(request.getParameter("start"),"0")));
    		this.setLength(Integer.parseInt(StringUtils.defaultString(request.getParameter("length"),"10")));
    		this.setUniqueId(request.getParameter("_"));
    		this.setDraw(request.getParameter("draw"));
    		
    		this.setSearch(request.getParameter("search[value]"));
    		this.setRegex(Boolean.valueOf(request.getParameter("search[regex]")));
    		
    		int sortableCol = Integer.parseInt(request.getParameter("order[0][column]"));
    		
    		List<DataTableColumnSpecs> columns = new ArrayList<DataTableColumnSpecs>();
    		
    		if(!AppUtil.isEmpty(this.getSearch())) {
    			this.setGlobalSearch(true);
    		}

    		maxParamsToCheck = getNumberOfColumns(request);

    		for(int i=0; i < maxParamsToCheck; i++) {
    			if(null != request.getParameter("columns["+ i +"][data]") 
    					&& !"null".equalsIgnoreCase(request.getParameter("columns["+ i +"][data]"))  
    					&& !AppUtil.isEmpty(request.getParameter("columns["+ i +"][data]"))) {
    				DataTableColumnSpecs colSpec = new DataTableColumnSpecs(request, i);
    				//있을경우만 변환
    				if(StringUtils.isNotEmpty(colSpec.getData())) {
    				    String dbCol = columnType.getColumn(colSpec.getData());
    				    if(StringUtils.isNotEmpty(dbCol)) {//변환값이 있을경우에만 치환
    				      colSpec.setFilterType(columnType.getFilterType(colSpec.getData()));
    				      colSpec.setData(dbCol);
    				    }else {
    				      //continue;//없을경우 path
    				    }
    				}
    				
    				if(i == sortableCol) {
    					this.setOrder(colSpec);
    				}
    				columns.add(colSpec);
    				
    				if(!AppUtil.isEmpty(colSpec.getSearch())) {
    					this.setGlobalSearch(false);
    				}
    			} 
    		}

    		if(!AppUtil.isEmpty(columns)) {
    			this.setColumns(columns);
    		}
    	}
	}
	/**
	 * 컬럼 개수
	 * @param request
	 * @return
	 */
	private int getNumberOfColumns(HttpServletRequest request) {
		Pattern p = Pattern.compile("columns\\[[0-9]+\\]\\[data\\]");  
		@SuppressWarnings("rawtypes")
		Enumeration params = request.getParameterNames(); 
		List<String> lstOfParams = new ArrayList<String>();
		while(params.hasMoreElements()){		
		 String paramName = (String)params.nextElement();
		 Matcher m = p.matcher(paramName);
		 if(m.matches())	{
			 lstOfParams.add(paramName);
		 }
		}
		return lstOfParams.size();
	}
	
	/**
	 * Gets the pagination request.
	 *
	 * @return the pagination request
	 */
	public PaginationData getPaginationRequest() {
		
		PaginationData pagination = new PaginationData();
		pagination.setStart(this.getStart());
		pagination.setLength(this.getLength());
		
		SortBy sortBy = null;
		if(!AppUtil.isEmpty(this.getOrder())) {
			sortBy = new SortBy();
			sortBy.addSort(this.getOrder().getData(), SortOrder.findBy(this.getOrder().getSortDir()));
		}
		
		FilterBy filterBy = new FilterBy();
		filterBy.setGlobalSearch(this.isGlobalSearch());
		if(this.getColumns() != null) {
		  for(DataTableColumnSpecs colSpec : this.getColumns()) {
            if(colSpec.isSearchable() && this.isGlobalSearch()) {//전체검색일경우 필드별 검색여부 판별
                if(!AppUtil.isEmpty(this.getSearch()) || !AppUtil.isEmpty(colSpec.getSearch())) {
                    //filterBy.addFilter(colSpec.getData(), (this.isGlobalSearch()) ? this.getSearch() : colSpec.getSearch() ,colSpec.getFilterType());
                  filterBy.addFilter(colSpec.getData(), this.getSearch() ,colSpec.getFilterType());
                }
            }else if(!this.isGlobalSearch()){ //개별검색일경우 필드옵션 무시
              if(!AppUtil.isEmpty(colSpec.getSearch())) {
                filterBy.addFilter(colSpec.getData(), colSpec.getSearch() ,colSpec.getFilterType());
              }
            }
          }
		}
		
		pagination.setSortBy(sortBy);
		pagination.setFilterBy(filterBy);
		
		return pagination;
	}
	
	/** The max params to check. */
	private int maxParamsToCheck = 0;
	
}