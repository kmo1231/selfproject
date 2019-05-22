package com.miok.etc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.service.DeptSvc;
import com.miok.admin.service.UserSvc;
import com.miok.admin.vo.DeptVO;
import com.miok.common.SearchVO;
import com.miok.common.TreeMaker;
import com.miok.member.vo.UserVO;

@Controller
public class PopUserController {

	@Autowired
	private DeptSvc deptSvc;
	
	@Autowired
	private UserSvc userSvc;
	
	// 부서 리스트
	@RequestMapping(value = "/popupDept")
	public String popupDept(Model model) {
		List<DeptVO> listview = deptSvc.selectDept();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		model.addAttribute("treeStr", treeStr);
		
		return "etc/popupDept";
	}
	
	// 사용자 선택시 필요한 부서 리스트
	@RequestMapping(value = "/popupUser")
	public String popupUser(Model model) {
		List<DeptVO> listview = deptSvc.selectDept();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		model.addAttribute("treeStr", treeStr);
		
		return "etc/popupUser";
	}
	
	// 선택한 부서의 사용자 리스트
	@RequestMapping(value = "/popupUsersByDept")
	public String popupUsersByDept(HttpServletRequest request,SearchVO searchVO, Model model) {
		String deptno = request.getParameter("deptno");
		searchVO.setSearchExt1(deptno);
		
		List<UserVO> listview = userSvc.selectUserListWithDept(searchVO);
		
		model.addAttribute("listview", listview);
		
		return "etc/popupUsersByDept";
	}
	
	// 사용자들 선택시 필요한 부서 리스트
	@RequestMapping(value = "/popupUsers")
	public String popupUsers(Model model) {
		popupUser(model);
		
		return "etc/popupUsers";
	}
	
	// 사용자들 선택시 사용할 사용자 리스트
	@RequestMapping(value = "/popupUsers4Users")
	public String popupUsers4Users(HttpServletRequest request, SearchVO searchVO, Model model) {
		popupUsersByDept(request, searchVO, model);
		
		return "etc/popupUsers4Users";
	}
}
