package com.insilicogen.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.insilicogen.common.datatable.DataTableFilterCode;

public class DataTableFilterUtil<T extends DataTableFilterCode> {

  /**
   * datatable filter code 형태로 데이타 변환
   * @param filterCodes
   * @return
   */
  public List<Map> addDataTableCode( List<T> filterCodes ) {
    List<Map> list = new ArrayList<>();
    filterCodes.forEach(r->{
      Map<String,String> code = new HashMap<>();
      code.put("code", r.getFilterCode());
      code.put("codeNm", r.getFilterCodeNm());
      list.add(code);
    });
    return list;
  }
}
