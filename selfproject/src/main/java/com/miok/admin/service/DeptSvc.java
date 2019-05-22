package com.miok.admin.service;

import java.util.List;

import com.miok.admin.vo.DeptVO;

public interface DeptSvc {
	public List<DeptVO> selectDept();
	
	public void insertDept(DeptVO deptVO);
	
	public DeptVO selectDeptOne(String deptno);
	
	public void deleteDept(String deptno);
	
}
