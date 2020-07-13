package com.insilicogen.common.user.web;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.insilicogen.common.auth.service.AuthService;
import com.insilicogen.common.auth.service.AuthVO;
import com.insilicogen.common.code.service.CodeService;
import com.insilicogen.common.code.service.CodeVO;
import com.insilicogen.common.comm.service.ResultCode;
import com.insilicogen.common.comm.service.ResultData;
import com.insilicogen.common.datatable.ColumnType;
import com.insilicogen.common.datatable.DataTableRequest;
import com.insilicogen.common.datatable.FilterType;
import com.insilicogen.common.datatable.PaginationData;
import com.insilicogen.common.inst.service.InstService;
import com.insilicogen.common.inst.service.InstVO;
import com.insilicogen.common.user.service.UserService;
import com.insilicogen.common.user.service.UserVO;
import com.insilicogen.common.user.type.UserColumn;
import com.insilicogen.common.util.AppUtil;
import com.insilicogen.common.util.DataTableFilterUtil;
import com.insilicogen.common.util.ResponseUtil;
import com.insilicogen.common.util.SessionUtils;
import com.insilicogen.common.variable.Layout;
import com.insilicogen.common.view.model.DataDownload;
import com.insilicogen.common.view.model.ExcelDownload;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

    /** UserService */
    @Resource(name = "userService")
    private UserService userService;

	/** CodeService */
	@Resource(name = "codeService")
	private CodeService codeService;

	/** InstService */
	@Resource(name = "instService")
	private InstService instService;
	
	@Resource(name = "authService")
    private AuthService authService;

    @RequestMapping(value = "/common/user/login.do")
    public String login(@ModelAttribute UserVO userVO, ModelMap model, HttpServletRequest request)  {
        SessionUtils.invalidate(request);
        return Layout.LOGIN_LAYOUT+"/common/user/login";
    }

    @RequestMapping(value = "/common/user/login.json" , method = RequestMethod.POST)
    @ResponseBody
    public ResultData<ResultCode> loginPrc(@ModelAttribute UserVO userVO, ModelMap model, HttpServletRequest request)  {
        userService.selectLogin(userVO);
        return ResponseUtil.response(ResultCode.SUCCESS);
    }
    @RequestMapping(value = "/common/user/logout.do")
    public String logout(@ModelAttribute UserVO userVO, ModelMap model, HttpServletRequest request)  {
        SessionUtils.invalidate(request);
        return Layout.LOGIN_LAYOUT+"/common/user/login";
    }

    @RequestMapping(value = "/common/user/selectUserList.do")
    public String selectUserList(@ModelAttribute UserVO userVO, ModelMap model, HttpServletRequest request)  {
        List<CodeVO> userStatusList = codeService.selectCodeListByGroup("USER_STATUS", "Y");
        List<AuthVO> authList = authService.selectAuthList(AuthVO.EMPTY);
        model.addAttribute("userStatusList", AppUtil.toJson(new DataTableFilterUtil<CodeVO>().addDataTableCode(userStatusList)));
        model.addAttribute("authList", AppUtil.toJson(new DataTableFilterUtil<AuthVO>().addDataTableCode(authList)));
        return Layout.ADMIN_LAYOUT+"/common/user/selectUserList";
    }

    

    @ResponseBody
    @RequestMapping(value = "/common/user/getUserList.json")
    public ResultData<List<UserVO>> getUserList(@ModelAttribute UserVO userVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
        DataTableRequest dataTableInRQ = new DataTableRequest(request , new ColumnType() {
          @Override
          public String getColumn(String dataTableHead) {
            return UserColumn.getDbColumn(dataTableHead);
          }
          @Override
          public FilterType getFilterType(String dataTableHead) {
            return UserColumn.getFilterType(dataTableHead);
          }
        });
        PaginationData pagination = dataTableInRQ.getPaginationRequest();
        userVO.setFilterByClause(pagination.getFilterByClause());
        userVO.setOrderByClause(pagination.getOrderByClause());

        int totalCnt = userService.selectUserListTotCnt(userVO);
        userVO.setTotalCnt(totalCnt);
        List<UserVO> selectUserList = userService.selectUserList(userVO);
        return ResponseUtil.response(selectUserList, userVO);
    }

    @RequestMapping(value = "/common/user/selectUserListExcel.do")
    public String selectUserListExcel(@ModelAttribute UserVO userVO, Model model, HttpServletRequest request)  {
      String[] headers = new String[UserColumn.values().length] ;
      String[][] dataKeys = new String[1][UserColumn.values().length] ;
      int i = 0;
      for(UserColumn u : UserColumn.values()) {
        headers[i] = u.getColumnKrNm();
        dataKeys[0][i++] = u.getDataTableColumn();
      }
      DataTableRequest dataTableInRQ = new DataTableRequest(request , new ColumnType() {
        @Override
        public String getColumn(String dataTableHead) {
          return UserColumn.getDbColumn(dataTableHead);
        }
        @Override
        public FilterType getFilterType(String dataTableHead) {
          return UserColumn.getFilterType(dataTableHead);
        }
      });
      PaginationData pagination = dataTableInRQ.getPaginationRequest();
      userVO.setFilterByClause(pagination.getFilterByClause());
      userVO.setOrderByClause(pagination.getOrderByClause());

      @SuppressWarnings("rawtypes")
      List<Map> resultList = userService.selectUserListExcel(userVO);
      DataDownload download = new ExcelDownload(model , headers , dataKeys);
      download.setResultDataList(resultList);

      return download.execute();
    }

    @RequestMapping(value = "/common/user/insertUser.do")
    public String insertUser(@ModelAttribute("data") UserVO userVO, ModelMap model, HttpServletRequest request)  {
    	model.addAttribute("instList", instService.selectInstList(new InstVO()));
    	model.addAttribute("userStatusList", codeService.selectCodeListByGroup("USER_STATUS", "Y"));
    	model.addAttribute("authList", authService.selectAuthList(AuthVO.EMPTY));
    	
        return Layout.SPA_LAYOUT+"/common/user/insertUser";
    }

    @ResponseBody
    @RequestMapping(value = "/common/user/insertUser.json")
    public ResultData<ResultCode> insertUser(@ModelAttribute UserVO userVO, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
        userService.insertUser(userVO);
        return ResponseUtil.response(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/common/user/updateUser.do")
    public String updateUser(@ModelAttribute("data") UserVO userVO, ModelMap model, HttpServletRequest request)  {
    	model.addAttribute("instList", instService.selectInstList(new InstVO()));
    	model.addAttribute("userStatusList", codeService.selectCodeListByGroup("USER_STATUS", "Y"));
    	model.addAttribute("authList", authService.selectAuthList(AuthVO.EMPTY));
        model.addAttribute("data", userService.selectUser(userVO));
        return Layout.SPA_LAYOUT+"/common/user/updateUser";
    }

    @ResponseBody
    @RequestMapping(value = "/common/user/updateUser.json")
    public ResultData<ResultCode> updateUser(@ModelAttribute UserVO userVO, ModelMap model, HttpServletRequest request, HttpServletResponse response)  {
        userService.updateUser(userVO);
        return ResponseUtil.response(ResultCode.SUCCESS);
    }

    @ResponseBody
    @RequestMapping(value = "/common/user/deleteUser.json", method = RequestMethod.POST)
    public ResultData<ResultCode>  deleteUser(@RequestBody UserVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
        userService.deleteUser(list);
        return ResponseUtil.response(ResultCode.SUCCESS);
    }
    @ResponseBody
    @RequestMapping(value = "/common/user/updatePasswordReset.json", method = RequestMethod.POST)
    public ResultData<ResultCode> updatePasswordReset(@RequestBody UserVO[] list, ModelMap model,HttpServletRequest request, HttpServletResponse response)  {
        userService.updatePasswordReset(list);
        return ResponseUtil.response(ResultCode.SUCCESS);
    }
}
