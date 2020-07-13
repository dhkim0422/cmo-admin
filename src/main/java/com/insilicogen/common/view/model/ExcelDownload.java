package com.insilicogen.common.view.model;

import org.springframework.ui.Model;
import com.insilicogen.common.view.ExcelDownloadView;
/**
 * 
 * 엑셀 다운로드
 *
 */
/**
String[] headers = {"Property" , "Min" , "Max" };
String[][] dataKeys = {{"PROPERTY" , "MIN_VALUE" , "MAX_VALUE" }};

List<Map<String, Object>> resultList = service.selectListByExcel

DataDownload download = new ExcelDownload(model , headers , dataKeys);
download.setResultDataList(resultList);
download.execute();
 */
public class ExcelDownload extends DataDownload{
  

  private final String[] headers ;
  private final String[][] dataKeys;
  private final int[] rowSpans;
  
  public ExcelDownload(Model model , String[] headers , String[][] dataKeys) {
    this(model, headers, dataKeys, null);
  }
  
  public ExcelDownload(Model model , String[] headers , String[][] dataKeys , int[] rowSpans) {
    super(model);
    this.headers = headers;
    this.dataKeys = dataKeys;
    this.rowSpans = rowSpans;
  }

  private void setData() {
    model.addAttribute(ExcelDownloadView.HEADERS , this.headers);
    model.addAttribute(ExcelDownloadView.DATA_KEYS , this.dataKeys);
    model.addAttribute(ExcelDownloadView.ROW_SPANS , this.rowSpans);
    model.addAttribute(ExcelDownloadView.RESULT_DATA_LIST , this.resultDataList);
  }
  public String execute() {
    this.setData();
    return ExcelDownloadView.VIEW_NAME;
  }

}
