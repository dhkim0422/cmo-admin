package com.insilicogen.common.datatable;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;

/**
 * 페이징  ,검색  ,소팅
 */
public class PaginationData {
	@Getter @Setter
	private Integer start;
	@Getter @Setter
	private Integer length;
	@Getter @Setter
	private Integer totalRecords;//전체개수
	@Getter @Setter
	private SortBy sortBy;     // 소팅조건
	@Getter @Setter
	private FilterBy filterBy; //검색조건
	
	/** The Constant BLANK. */
    private static final String BLANK = "";
    
    /** The Constant SPACE. */
    private static final String SPACE = " ";
    
    /** The Constant EQUAL. */
    private static final String EQUAL = " = ";
    
    /** The Constant LIKE_PREFIX. */
    private static final String LIKE_PREFIX = " LIKE '%";
    
    /** The Constant LIKE_SUFFIX. */
    private static final String LIKE_SUFFIX = "%' ";
    
    /** The Constant AND. */
    private static final String AND = " AND ";
    
    /** The Constant OR. */
    private static final String OR = " OR ";
    
    /** The Constant ORDER_BY. */
    private static final String ORDER_BY = " ORDER BY ";
    
    private static final String BRKT_OPN = " ( ";
    
    private static final String BRKT_CLS = " ) ";
    
    /** The Constant single quartor. */
    private static final String SINGLE_QUATOR = "'";
    
    /** The Constant COMMA. */
    private static final String COMMA = " , ";
    
    /** The Constant PAGE_NO. */
    public static final String PAGE_NO = "start";
    
    /** The Constant PAGE_SIZE. */
    public static final String PAGE_SIZE = "length";
    
    /** The Constant DRAW. */
    public static final String DRAW = "draw";

	public boolean isFilterByEmpty() {
		if(null == filterBy || null == filterBy.getFilters() || filterBy.getFilters().size() == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isSortByEmpty() {
		if(null == sortBy || null == sortBy.getSorts() || sortBy.getSorts().size() == 0) {
			return true;
		}
		return false;
	}
	
	public String getFilterByClause() {
		
		StringBuilder fbsb = null;
		
		if (!isFilterByEmpty()) {
			Iterator<FilterInfo> fbit = filterBy.getFilters().iterator();
			
			while (fbit.hasNext()) {
				
			    FilterInfo pair =  fbit.next();
				
				if(null == fbsb) {
					fbsb = new StringBuilder();
					fbsb.append(BRKT_OPN);
					
					fbsb.append(SPACE)
							.append(BRKT_OPN)
								.append(pair.getFilterColumn());
								if(FilterType.EQUAL == pair.getFilterType()) {
								  fbsb.append(EQUAL)
								  .append(SINGLE_QUATOR)
								  .append(pair.getFilterValue())
								  .append(SINGLE_QUATOR);
								}else {
								  fbsb.append(LIKE_PREFIX)
                                  .append(pair.getFilterValue())
                                  .append(LIKE_SUFFIX);
								}
						fbsb.append(BRKT_CLS);
					
				} else {
					
					fbsb.append(filterBy.isGlobalSearch() ? OR : AND)
							.append(BRKT_OPN)
								  .append(pair.getFilterColumn());
								    if(FilterType.EQUAL == pair.getFilterType()) {
	                                  fbsb.append(EQUAL)
	                                  .append(SINGLE_QUATOR)
	                                  .append(pair.getFilterValue())
	                                  .append(SINGLE_QUATOR);
	                                }else {
	                                  fbsb.append(LIKE_PREFIX)
	                                  .append(pair.getFilterValue())
	                                  .append(LIKE_SUFFIX);
	                                }
					          fbsb.append(BRKT_CLS);
				}
			}
			fbsb.append(BRKT_CLS);
		}
		
		return (null == fbsb) ? BLANK : fbsb.toString();
	}
	
	/**
	 * Gets the order by clause.
	 *
	 * @return the order by clause
	 */
	public String getOrderByClause() {
		
		StringBuilder sbsb = null;
		
		if(!isSortByEmpty()) {
			Iterator<Entry<String, SortOrder>> sbit = sortBy.getSorts().entrySet().iterator();
			
			while (sbit.hasNext()) {
				Map.Entry<String, SortOrder> pair =  sbit.next();
				if(null == sbsb) {
					sbsb = new StringBuilder();
					sbsb.append(ORDER_BY).append(pair.getKey()).append(SPACE).append(pair.getValue());
				} else {
					sbsb.append(COMMA).append(pair.getKey()).append(SPACE).append(pair.getValue());
				}
			}
		}
		
		return (null == sbsb) ? BLANK : sbsb.toString();
	}

	

}