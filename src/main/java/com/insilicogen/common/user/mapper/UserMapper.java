package com.insilicogen.common.user.mapper;

import java.util.List;
import java.util.Map;
import com.insilicogen.common.anotation.SessionLoginId;
import com.insilicogen.common.user.service.UserVO;

public interface UserMapper {
    public List<UserVO> selectUserList(UserVO userVO) ;
    public List<Map> selectUserListExcel(UserVO userVO);
    public Integer selectUserListTotCnt(UserVO userVO) ;
    public Integer selectUserIdCnt(UserVO userVO) ;
    public UserVO selectUser(UserVO userVO) ;
    @SessionLoginId
    public int insertUser(UserVO userVO) ;
    @SessionLoginId
    public int updateUser(UserVO userVO) ;
    @SessionLoginId
    public int updatePasswordReset(UserVO userVO) ;
    public int deleteUser(UserVO userVO) ;
    
    public int updateLoginTryCnt(UserVO userVO) ;

}
