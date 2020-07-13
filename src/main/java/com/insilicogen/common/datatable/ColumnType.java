package com.insilicogen.common.datatable;

public interface ColumnType {
  public String getColumn(String dataTableHead);
  public FilterType getFilterType(String dataTableHead);

}

