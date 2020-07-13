package com.insilicogen.common.datatable;

/**
 * 
 * 정렬
 *
 */
public enum SortOrder {


	ASC("ASC"), DESC("DESC");


	private final String value;

	SortOrder(String v) {
		value = v;
	}

	public static SortOrder findBy(String v) {
		for (SortOrder c : SortOrder.values()) {
			if (c.name().equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

	public String value() {
		return value;
	}

}