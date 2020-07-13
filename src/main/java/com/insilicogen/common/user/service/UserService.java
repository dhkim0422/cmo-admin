package com.insilicogen.common.user.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.insilicogen.common.exception.BaseRuntimeException;
import com.insilicogen.common.menu.service.MenuService;
import com.insilicogen.common.user.mapper.UserMapper;
import com.insilicogen.common.util.AppUtil;
import com.insilicogen.common.util.SessionUtils;

@Service
public class UserService {
    @Resource(name = "txManager")
    protected PlatformTransactionManager transactionManager;
  
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /** MenuService */
    @Resource(name = "menuService")
    private MenuService menuService;
    
    public List<Map> selectUserListExcel(UserVO userVO) {
        return userMapper.selectUserListExcel(userVO);
    }
    
    public List<UserVO> selectUserList(UserVO userVO)  {
        return userMapper.selectUserList(userVO);
    }
    public int selectUserListTotCnt(UserVO userVO)  {
      return userMapper.selectUserListTotCnt(userVO);
    }
    
    public UserVO selectLogin(UserVO paramVO)   {
      UserVO user = userMapper.selectUser(paramVO);
      if(user == null || AppUtil.isEmpty(user)) {
          throw new BaseRuntimeException("회원정보가 없습니다.");
      }

      if(user.getLoginTryCnt() > 5) {
        throw new BaseRuntimeException("비밀번호를 5회이상 틀렸습니다.");
      }
      
      if(!passwordEncoder.matches(paramVO.getPasswd() , user.getPasswd())) {
         this.updateFailLoginTryCnt(user);
         throw new BaseRuntimeException("아이디 , 비밀번호를 확인하세요.");
      }
      
      if(!"US-20".equals(user.getUserStatus())) {//승인완료
        throw new BaseRuntimeException("이용하실수 없는 회원입니다.");
      }
      
      user.setMenuList(menuService.selectMyMenuList(user.getAuthGroupSeq()));
      user.setRoleUrlList(menuService.selectMyMenuPageUrlList(user.getAuthGroupSeq()));
      
      SessionUtils.setSessionInfo(user);
      this.updateSuccessLoginTryCnt(user);
      
      return user;
    }
    
    /**
     * 로그인횟수 초기화
     * @param user
     */
    public void updateSuccessLoginTryCnt(UserVO user ) {
      user.initLoginTryCnt();
      userMapper.updateLoginTryCnt(user);
    }
    /**
     * 로그인실패 /트랜잭션 분리
     * @param user
     */
    public void updateFailLoginTryCnt(UserVO user ) {
      TransactionStatus status =transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
      try {
        user.increaseLoginTryCnt();
        userMapper.updateLoginTryCnt(user);
      } catch (Exception e) {
        transactionManager.rollback(status);
        throw e;
      } finally {
        if (status.isRollbackOnly()) {
          transactionManager.rollback(status);
        } else {
          transactionManager.commit(status);
        }
      }
    }
    
    public UserVO selectUser(UserVO userVO)  {
        return userMapper.selectUser(userVO);
    }

    public int insertUser(UserVO userVO)  {
        if(userMapper.selectUserIdCnt(userVO) > 0) {
            throw new BaseRuntimeException("이미등록된 아이디 입니다.");
        }
        userVO.setPasswd(passwordEncoder.encode(userVO.getPasswd()));
        return userMapper.insertUser(userVO);
    }

    public int updateUser(UserVO userVO)  {
        return userMapper.updateUser(userVO);
    }

    public void deleteUser(UserVO[] list)  {
        Arrays.stream(list).forEach(r->
          userMapper.deleteUser(r)
        );
    }

    public void updatePasswordReset(UserVO[] list) {
      Arrays.stream(list).forEach(r->{
        //TODO 규칙확정후 수정(ex 휴대폰번호등)
        r.setPasswd(passwordEncoder.encode(r.getEmailAddr()));
        userMapper.updatePasswordReset(r);
      });
      
    }
    
}
