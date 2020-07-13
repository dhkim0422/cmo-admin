package com.insilicogen.common.comm.service;

import lombok.Data;

@Data
public class CommonVO {
  //datatable 
  private int totalCnt;
  private int start;
  private int length;
  private int draw;
  private String filterByClause;
  private String orderByClause;
  private String excelFlag;//excel down data check
  
  private String registId;
  private String registDt;
  private String updateId;
  private String updateDt;
}
