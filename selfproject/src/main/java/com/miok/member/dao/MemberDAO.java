package com.miok.member.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.common.SearchVO;
import com.miok.member.vo.LoginVO;
import com.miok.member.vo.UserVO;

@Repository
public interface MemberDAO {
	public Integer selectSearchMemberCount(SearchVO searchVO);

	public List<?> selectSearchMemberList(SearchVO searchVO);

	public UserVO selectMember4Login(LoginVO loginVO);

	public void insertLogin(String userno);

	public void insertLogout(String userno);

}
