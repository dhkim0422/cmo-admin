package com.insilicogen.common.inst.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insilicogen.common.inst.mapper.InstMapper;

@Service
public class InstService {

	@Autowired
    private InstMapper instMapper;

	public List<InstVO> selectInstList(InstVO instVO)  {
        return instMapper.selectInstList(instVO);
    }

	public InstVO selectInst(InstVO instVO)  {
		return instMapper.selectInst(instVO);
	}

	public int insertInst(InstVO instVO)  {
		return instMapper.insertInst(instVO);
	}

	public int updateInst(InstVO instVO)  {
		return instMapper.updateInst(instVO);
	}

	public int deleteInst(InstVO[] list)  {
		int cnt = 0;
		for(InstVO p : list) {
			cnt += instMapper.deleteInst(p);
		}
		return cnt;
	}
}
