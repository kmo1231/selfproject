package com.miok.member.service;

import java.util.List;

import com.miok.common.SearchVO;
import com.miok.member.vo.LoginVO;
import com.miok.member.vo.UserVO;

public interface MemberSvc {
	public Integer selectSearchMemberCount(SearchVO searchVO);

	public List<?> selectSearchMemberList(SearchVO searchVO);

	public UserVO selectMember4Login(LoginVO loginVO);

	public void insertLogin(String userno);

	public void insertLogout(String userno);

}
