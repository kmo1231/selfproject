package com.miok.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.admin.vo.DeptVO;

@Repository
public interface DeptDAO {
	public List<DeptVO> selectDept();
	
	public void insertDept(DeptVO deptVO);
	
	public void updateDept(DeptVO deptVO);
	
	public DeptVO selectDeptOne(String deptno);
	
	public void deleteDept(String deptno);
}
