package com.insilicogen.common.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insilicogen.common.comm.service.CommonVO;
import lombok.Data;

@Data
public class UserVO extends CommonVO{

    private String userId;
    private String userNm;
    private String passwd;
    private String emailAddr;
    private String adres;
    private String detailAdres;
    private String postNo;
    private String userStatus;
    private String userStatusNm;
    private int loginTryCnt;

    private int instId;
    private int authGroupSeq;
    private String authGroupNm;
    
    @JsonIgnore
    private List<Map> menuList = new ArrayList<>();
    @JsonIgnore
    private List<Map> roleUrlList = new ArrayList<>();;
    
    public final void increaseLoginTryCnt() {
      setLoginTryCnt(getLoginTryCnt() + 1);
    }

    public final void initLoginTryCnt() {
      setLoginTryCnt(0);
    }
}
