package com.insilicogen.common.board.type;


public enum BoardColumn {
  BBS_ID("bbsId"), BBS_SEQ("bbsSeq"), BBS_SJ("bbsSj"), READ_CNT("readCnt"), REGIST_ID("registId"), UPDATE_ID("updateId"), REGIST_DT("registDt"), UPDATE_DT("updateDt");

  private final String column;

  BoardColumn(String column) {
      this.column = column;
  }

  public String getColumn() {
    return this.column;
  }
  public static String getColumn(String dataTableHead) {
    for (BoardColumn u : BoardColumn.values()) {
        if (u.getColumn().equalsIgnoreCase(dataTableHead)) {
            return u.name();
        }
    }
    throw new IllegalArgumentException(dataTableHead);
  }
}
