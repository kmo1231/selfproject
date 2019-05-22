package com.miok.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.admin.dao.DeptDAO;
import com.miok.admin.vo.DeptVO;

@Service
public class DeptSvcImp implements DeptSvc{
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Override
	public List<DeptVO> selectDept() {
		return deptDAO.selectDept();
	}

	@Override
	public void insertDept(DeptVO deptVO) {
		if("".equals(deptVO.getParentno())) {
			deptVO.setParentno(null);
		}
		
		if(deptVO.getDeptno() == null || "".equals(deptVO.getDeptno())) {
			deptDAO.insertDept(deptVO);
		} else {
			deptDAO.updateDept(deptVO);
		}
	}

	@Override
	public DeptVO selectDeptOne(String deptno) {
		return deptDAO.selectDeptOne(deptno);
	}

	@Override
	public void deleteDept(String deptno) {
		deptDAO.deleteDept(deptno);
	}

}
