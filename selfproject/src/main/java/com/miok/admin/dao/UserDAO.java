package com.miok.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.common.SearchVO;
import com.miok.member.vo.UserVO;

@Repository
public interface UserDAO {
	public List<UserVO> selectUserList(String deptno);
	
	public List<UserVO> selectUserListWithDept(SearchVO searchVO);
	
	public void insertUser(UserVO userVO);
	
	public void updateUser(UserVO userVO);
	
	public String selectUserID(String userid);
	
	public UserVO selectUserOne(String userno);
	
	public void deleteUser(String userno);
	
	public void updateUserByMe(UserVO userVO);
	
	public void updateUserPassword(UserVO userVO);
}
