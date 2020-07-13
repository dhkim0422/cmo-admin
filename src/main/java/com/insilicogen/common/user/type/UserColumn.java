package com.insilicogen.common.user.type;

import com.insilicogen.common.datatable.FilterType;

public enum UserColumn {
  USER_ID("userId","a.USER_ID","아이디" )
  , USER_NM("userNm","a.USER_NM","이름")
  , EMAIL_ADDR("emailAddr","a.EMAIL_ADDR","이메일")
  , ADRES("adres","a.ADRES","주소")
  , DETAIL_ADRES("detailAdres","a.DETAIL_ADRES","상세주소")
  , POST_NO("postNo","a.POST_NO","우편번호")
  
  , LOGIN_TRY_CNT("loginTryCnt","a.LOGIN_TRY_CNT" ,"로그인시도" ,FilterType.EQUAL)
  , USER_STATUS("userStatusNm","a.USER_STATUS" ,"상태" ,FilterType.EQUAL)
  
  , AUTH_GROUP_NM("authGroupNm","b.AUTH_GROUP_SEQ" ,"권한",FilterType.EQUAL)
  , REGIST_DT("registDt" ,"a.REGIST_DT","등록일",FilterType.EQUAL)
  
  ;
  
  private final String dataTableColumn;
  private final String dbColumn;
  private final String columnKrNm;
  private final FilterType filterType;
  
  UserColumn(String dataTableColumn,String dbColumn,String columnKrNm ) {
      this(dataTableColumn, dbColumn, columnKrNm, FilterType.LIKE);
  }
  UserColumn(String dataTableColumn,String dbColumn,String columnKrNm , FilterType filterType) {
      this.dataTableColumn = dataTableColumn;
      this.dbColumn = dbColumn;
      this.columnKrNm = columnKrNm;
      this.filterType = filterType;
  }

  public final String getDbColumn() {
    return this.dbColumn;
  }
  
  public final String getDataTableColumn() {
    return this.dataTableColumn;
  }
  
  public final String getColumnKrNm() {
    return columnKrNm;
  }
  

  public final FilterType getFilterType() {
    return this.filterType;
  }
  
  public static String getDbColumn(String dataTableHead) {
    for (UserColumn u : UserColumn.values()) {
        if (u.getDataTableColumn().equalsIgnoreCase(dataTableHead)) {
            return u.getDbColumn();
        }
    }
    return "";
  }
  public static FilterType getFilterType(String dataTableHead) {
    for (UserColumn u : UserColumn.values()) {
        if (u.getDataTableColumn().equalsIgnoreCase(dataTableHead)) {
            return u.getFilterType();
        }
    }
    return FilterType.LIKE;
  }
}
