/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.insilicogen.common.code.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insilicogen.common.code.mapper.CodeMapper;
import com.insilicogen.common.util.EgovStringUtil;

@Service
public class CodeService {

	@Autowired
    private CodeMapper codeMapper;

	public List<GroupCodeVO> selectCodeList(GroupCodeVO groupCodeVO) {
        return codeMapper.selectCodeList(groupCodeVO);
    }

	public GroupCodeVO selectCode(GroupCodeVO groupCodeVO) {
		return codeMapper.selectCode(groupCodeVO);
	}

	public int insertCode(GroupCodeVO groupCodeVO) {
		int cnt = codeMapper.insertGroupCode(groupCodeVO);

		if(groupCodeVO.getCodeList() != null) {
			for(CodeVO codeVO : groupCodeVO.getCodeList()) {
				if(codeVO.getCode()==null) {
					continue;
				}
				codeVO.setGroupCd(groupCodeVO.getGroupCd());
				codeVO.setRegistId(groupCodeVO.getRegistId());
				codeMapper.insertCode(codeVO);
			}
		}

		return cnt;
	}

	public int updateCode(GroupCodeVO groupCodeVO) {
		int cnt = codeMapper.updateGroupCode(groupCodeVO);

		if(groupCodeVO.getDelList() != null) {
			for(String code : groupCodeVO.getDelList()) {
				if(EgovStringUtil.nullConvert(code).equals("")) {

				}else {
					CodeVO codeVO = new CodeVO();
					codeVO.setGroupCd(groupCodeVO.getGroupCd());
					codeVO.setCode(code);
					codeMapper.deleteCode(codeVO);
				}
			}
		}

		if(groupCodeVO.getCodeList() != null) {
			for(CodeVO codeVO : groupCodeVO.getCodeList()) {
				if(codeVO.getCode()==null) {
					continue;
				}
				String code = codeVO.getOriCode();
				codeVO.setGroupCd(groupCodeVO.getGroupCd());
				codeVO.setUpdateId(groupCodeVO.getUpdateId());
				codeVO.setRegistId(groupCodeVO.getUpdateId());
				if(EgovStringUtil.nullConvert(code).equals("")) {
					codeMapper.insertCode(codeVO);
				}else {
					codeMapper.updateCode(codeVO);
				}
			}
		}
		return cnt;
	}

	public int deleteGroupCode(GroupCodeVO[] list) {
		int cnt = 0;
		for(GroupCodeVO p : list) {
			CodeVO codeVO = new CodeVO();
			codeVO.setGroupCd(p.getGroupCd());
			codeMapper.deleteCode(codeVO);
			cnt += codeMapper.deleteGroupCode(p);
		}
		return cnt;
	}

	public List<CodeVO> selectCodeListByGroup(String groupCd, String useAt) {
		CodeVO codeVO  = new CodeVO();
		codeVO.setGroupCd(groupCd);
		codeVO.setUseAt(useAt);
		return codeMapper.selectCodeListByGroup(codeVO);
	}
}
