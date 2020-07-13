package com.insilicogen.common.type;

import com.insilicogen.common.type.config.EnumMapperType;

/**
 * 
 *예 , 아니오 선택형
 */
public enum YesOrNo implements EnumMapperType{
  Y("예") , N("아니오");
  private String title;
  
  private YesOrNo(String title) {
    this.title = title;
  }
  @Override
  public String getCode() {
    return this.name();
  }

  @Override
  public String getTitle() {
    return this.title;
  }
}
