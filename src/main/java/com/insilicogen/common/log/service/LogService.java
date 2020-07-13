package com.insilicogen.common.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insilicogen.common.log.mapper.LogMapper;

@Service
public class LogService {

	@Autowired
    private LogMapper logMapper;

	public int insertLog(LogVO logVO)  {
		return logMapper.insertLog(logVO);
	}

}
