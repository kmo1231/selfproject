package com.miok.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.common.SearchVO;
import com.miok.member.dao.MemberDAO;
import com.miok.member.vo.LoginVO;
import com.miok.member.vo.UserVO;

@Service
public class MemberSvcImp implements MemberSvc{

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public Integer selectSearchMemberCount(SearchVO searchVO) {
		return memberDAO.selectSearchMemberCount(searchVO);
	}

	@Override
	public List<UserVO> selectSearchMemberList(SearchVO searchVO) {
		return memberDAO.selectSearchMemberList(searchVO);
	}

	@Override
	public UserVO selectMember4Login(LoginVO loginVO) {
		return memberDAO.selectMember4Login(loginVO);
	}

	@Override
	public void insertLogin(String userno) {
		memberDAO.insertLogin(userno);
	}

	@Override
	public void insertLogout(String userno) {
		memberDAO.insertLogout(userno);
	}

}
