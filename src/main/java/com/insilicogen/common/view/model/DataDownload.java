package com.insilicogen.common.view.model;

import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;

public abstract class DataDownload {

  protected List<Map> resultDataList;
  protected final Model model;
  
  protected DataDownload(Model model) {
    this.model = model;
  }
  public void setResultDataList(List<Map> resultDataList) {
    this.resultDataList = resultDataList;
  }
  public abstract String execute();
}
