package com.miok.admin.service;

import java.util.List;

import com.miok.common.SearchVO;
import com.miok.member.vo.UserVO;

public interface UserSvc {
	public List<UserVO> selectUserList(String deptno);

	public List<UserVO> selectUserListWithDept(SearchVO searchVO);

	public void insertUser(UserVO userVO);

	public String selectUserID(String userid);

	public UserVO selectUserOne(String userno);

	public void deleteUser(String userno);

	public void updateUserByMe(UserVO userVO);

	public void updateUserPassword(UserVO userVO);
}
