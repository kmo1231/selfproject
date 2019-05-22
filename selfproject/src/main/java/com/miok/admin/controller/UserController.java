package com.miok.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.service.DeptSvc;
import com.miok.admin.service.UserSvc;
import com.miok.admin.vo.DeptVO;
import com.miok.common.FileUtil;
import com.miok.common.FileVO;
import com.miok.common.TreeMaker;
import com.miok.common.UtilEtc;
import com.miok.etc.service.EtcSvc;
import com.miok.member.vo.UserVO;

@Controller
public class UserController {
	
	@Autowired
	private DeptSvc deptSvc;
	
	@Autowired
	private UserSvc userSvc;
	
	@Autowired
	private EtcSvc etcSvc;
	
	
	// 부서 리스트
	@RequestMapping(value = "/adUser")
	public String deptList(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		List<DeptVO> listview = deptSvc.selectDept();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		model.addAttribute("treeStr", treeStr);
		
		return "admin/organ/User";
		
	}
	
	// 사용자 리스트
	@RequestMapping(value = "/adUserList")
	public String userList(HttpServletRequest request, Model model) {
		String deptno = request.getParameter("deptno");
		
		return common_UserList(model, deptno);
	}
	
	// 선택한 부서의 사용자 리스트
	public String common_UserList(Model model, String deptno) {
		List<UserVO> listview = userSvc.selectUserList(deptno);
		
		model.addAttribute("listview", listview);
		
		return "admin/organ/UserList";
	}
	
	// 사용자 저장
	@RequestMapping(value = "/adUserSave")
	public String userSave(HttpServletResponse response, Model model, UserVO userVO) {
		
		// 신규 사용자 일 경우
		if(userVO.getUserno() == null || "".equals(userVO.getUserno())) {
			// 중복 확인
			String userid = userSvc.selectUserID(userVO.getUserid());
			if(userid != null) {
				return "common/blank";
			}
		}
		FileUtil fs = new FileUtil();
		// 사진이 있을 경우 사진 저장 및 uservo에 실제파일명 설정 
		FileVO fileInfo = fs.saveFile(userVO.getPhotoFile());
		if(fileInfo != null) {
			userVO.setPhoto(fileInfo.getRealname());
		}
		userSvc.insertUser(userVO);
		
		return common_UserList(model, userVO.getDeptno());
	}
	
	// id 중복확인
	@RequestMapping(value = "/chkUserid")
	public void chkUserid(HttpServletRequest request, HttpServletResponse response) {
		String userid = request.getParameter("userid");
		
		userid = userSvc.selectUserID(userid);
		
		UtilEtc.responseJsonValue(response, userid);
	}
	
	// 사용자 조회
	@RequestMapping(value = "/adUserRead")
	public void userRead(HttpServletRequest request, HttpServletResponse response) {
		String userno = request.getParameter("userno");
		
		UserVO userInfo = userSvc.selectUserOne(userno);
		
		UtilEtc.responseJsonValue(response, userInfo);
	}
	
	// 사용자 삭제
	@RequestMapping(value = "/adUserDelete")
	public String userDelete(HttpServletRequest request, Model model, UserVO userVO) {
		userSvc.deleteUser(userVO.getUserno());
		
		return common_UserList(model, userVO.getDeptno());
	}
}
