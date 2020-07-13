package com.insilicogen.common.util;
import javax.servlet.jsp.tagext.*;
import lombok.Setter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.*;
import java.io.*;

public class BtnTag extends SimpleTagSupport {
  
   @Setter
   private String menuSeq;
   @Setter
   private String btnId;

   public void doTag() throws JspException, IOException
   {
       PageContext pageContext = (PageContext) getJspContext();
       HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
       getJspContext().getOut().print(SessionUtils.getBtnUrl(request, menuSeq, btnId));
   }
}