package com.miok.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.admin.dao.UserDAO;
import com.miok.common.SearchVO;
import com.miok.member.vo.UserVO;

@Service
public class UserSvcImp implements UserSvc{
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public List<UserVO> selectUserList(String deptno) {
		return userDAO.selectUserList(deptno);
	}

	@Override
	public List<UserVO> selectUserListWithDept(SearchVO searchVO) {
		return userDAO.selectUserListWithDept(searchVO);
	}

	// 사용자 저장
	@Override
	public void insertUser(UserVO userVO) {
		if(userVO.getUserno() == null || "".equals(userVO.getUserno())) {
			userDAO.insertUser(userVO);
		} else {
			userDAO.updateUser(userVO);
		}
	}

	@Override
	public String selectUserID(String userid) {
		return userDAO.selectUserID(userid);
	}

	@Override
	public UserVO selectUserOne(String userno) {
		return userDAO.selectUserOne(userno);
	}

	@Override
	public void deleteUser(String userno) {
		userDAO.deleteUser(userno);
	}

	@Override
	public void updateUserByMe(UserVO userVO) {
		userDAO.updateUserByMe(userVO);
	}

	@Override
	public void updateUserPassword(UserVO userVO) {
		userDAO.updateUserPassword(userVO);
	}
	
}
