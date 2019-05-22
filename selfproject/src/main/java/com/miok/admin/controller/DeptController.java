package com.miok.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.service.DeptSvc;
import com.miok.admin.vo.DeptVO;
import com.miok.common.TreeMaker;
import com.miok.common.UtilEtc;
import com.miok.etc.service.EtcSvc;

@Controller
public class DeptController {

	@Autowired
	private DeptSvc deptSvc;
	
	@Autowired
	private EtcSvc etcSvc;
	
	// 부서 리스트
	@RequestMapping(value = "/adDepartment")
	public String deptList(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		List<DeptVO> listview = deptSvc.selectDept();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		model.addAttribute("treeStr", treeStr);
		
		return "admin/organ/Department";
	}
	
	// 부서 등록
	@RequestMapping(value = "/adDepartmentSave")
	public void deptSave(HttpServletResponse response, DeptVO deptVO) {
		deptSvc.insertDept(deptVO);
		
		UtilEtc.responseJsonValue(response, deptVO);
	}
	
	// 부서 정보 (하나)
	@RequestMapping(value = "/adDepartmentRead")
	public void deptRead(HttpServletRequest request, HttpServletResponse response) {
		String deptno = request.getParameter("deptno");
		
		DeptVO deptInfo = deptSvc.selectDeptOne(deptno);
		
		UtilEtc.responseJsonValue(response, deptInfo);
	}
	
	// 부서 삭제
	@RequestMapping(value = "/adDepartmentDelete")
	public void deptDelete(HttpServletRequest request, HttpServletResponse response) {
		String deptno = request.getParameter("deptno");
		
		deptSvc.deleteDept(deptno);
		
		UtilEtc.responseJsonValue(response, "OK");
	}
}
