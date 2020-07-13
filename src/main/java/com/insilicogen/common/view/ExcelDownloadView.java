package com.insilicogen.common.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import com.insilicogen.common.util.AppUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(ExcelDownloadView.VIEW_NAME)
public class ExcelDownloadView extends AbstractXlsView implements DownloadView {

    public static final String VIEW_NAME = "excelDownloadView";
    
    public static final String HEADERS = "headers";
    public static final String DATA_KEYS = "dataKeys";
    public static final String ROW_SPANS = "rowSpans";
    /**
     * 엑셀파일을 설정하고 생성한다.
     * @param model
     * @param wb
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
      response.setContentType("application/octet-stream");
      response.setHeader("Accept-Ranges", "bytes");

      response.setHeader("Content-Disposition", "attachment; filename=\"export-excel.xls\"");

      List<Map> resultList = (List<Map>)model.get(RESULT_DATA_LIST);
      log.debug("headers : {}" , model.get(HEADERS));
      log.debug("dataKeys : {}" , model.get(DATA_KEYS));
      String[] headers = (String[])model.get(HEADERS);
      String[][] dataKeys = (String[][])model.get(DATA_KEYS);
      int[] rowSpans = (int[])model.get(ROW_SPANS);
      
      if(isHeaderValueExists(headers, dataKeys[0])){
          throw new IOException("엑셀 헤더와 데이타길이가 다릅니다.");
      }

      Sheet sheet = workbook.createSheet();
      
      int rowIdx = 0;
      rowIdx = makeHeadRow(headers, sheet, rowIdx);
      
      makeDataRow(resultList, dataKeys, sheet, rowIdx , rowSpans);
      
      setColAutoWIdth(headers, sheet);
    }
    /**
     *  header 데이타 체크
     * @param headers
     * @param dataKeys
     * @return
     */
    private boolean isHeaderValueExists(String[] headers, String[] dataKeys) {
      return headers.length < 1 ||headers.length != dataKeys.length;
    }
    /**
     * data row  생성
     * @param resultList
     * @param dataKeys
     * @param sheet
     * @param rowIdx
     */
    private void makeDataRow(List<Map> resultList, String[][] dataKeys, Sheet sheet,int rowIdx , int[] rowSpans) {
      Cell cell = null;
      int cellIdx = 0;
      Row row = null;
      int nRowStart; 
      for (Map map : resultList) {
        nRowStart = rowIdx;
        for (int i = 0; i < dataKeys.length; i++) {
          row = sheet.createRow(rowIdx);
          cellIdx = 0;
          
          String[] rowKey = dataKeys[i];
          for (String dataKey : rowKey) {
            cell = row.createCell(cellIdx);
            cell.setCellValue(this.valueToString(map.get(dataKey)));
            cellIdx++;
          }
          rowIdx++;
        }
        
        //rowspan 병합 영역 설정
        if(rowSpans != null) {
          for (int rowSpanIndex : rowSpans) {
            sheet.addMergedRegion(new CellRangeAddress(nRowStart, row.getRowNum(), rowSpanIndex, rowSpanIndex));
          }
        }

      }

    }
    /**
     * head 데이타 생성
     * @param headers
     * @param sheet
     * @param rowIdx
     * @param cellIdx
     * @return
     */
    private int makeHeadRow(String[] headers, Sheet sheet, int rowIdx) {
      Cell cell;
      //excel header
      Row row = sheet.createRow(rowIdx);
      int cellIdx = 0;
      for (String header : headers) {
          cell = row.createCell(cellIdx);
          cell.setCellValue(header);
          cellIdx++;
      }
      rowIdx++;
      return rowIdx;
    }
    /**
     * 셀 넓이 조절
     * @param headers
     * @param sheet
     */
    private void setColAutoWIdth(String[] headers, Sheet sheet) {
      for(int i = 0 ; i < headers.length ; i ++) {
        sheet.autoSizeColumn(i);
      }
    }
    /**
     * String 으로 변환
     * @param src
     * @return
     */
    private String valueToString(Object src) {
      return AppUtil.nvl(src);
    }
    
}
