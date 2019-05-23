package com.miok.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.service.UserSvc;
import com.miok.common.FileUtil;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;
import com.miok.common.UtilEtc;
import com.miok.member.service.MemberSvc;
import com.miok.member.vo.UserVO;

@Controller
public class MemberController {

	@Autowired
	private UserSvc userSvc;
	
	@Autowired
	private MemberSvc memberSvc;
	
	// 내정보
	@RequestMapping(value = "/memberForm")
	public String memberForm(HttpServletRequest request, Model model) {
		String save = request.getParameter("save");
		
		String userno = (String)request.getSession().getAttribute("userno");
		
		UserVO userInfo = userSvc.selectUserOne(userno);
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("save", save);
		
		return "member/memberForm";
	}
	
	// 사용자 저장
	@RequestMapping(value = "/userSave")
	public String userSave(HttpServletRequest request, Model model, UserVO userInfo) {
		String userno = (String)request.getSession().getAttribute("userno");
		userInfo.setUserno(userno);
		
		FileUtil fs = new FileUtil();
		FileVO fileInfo = fs.saveImage(userInfo.getPhotoFile());
		if(fileInfo != null) {
			userInfo.setPhoto(fileInfo.getRealname());
		}
		userSvc.updateUserByMe(userInfo);
		
		return "redirect:/memberForm?save=OK";
	}
	
	// 비밀번호 변경
	@RequestMapping(value = "/changePWSave")
	public void changePWSave(HttpServletRequest request, HttpServletResponse response, UserVO userInfo) {
		String userno = (String)request.getSession().getAttribute("userno");
		userInfo.setUserno(userno);
		
		userSvc.updateUserPassword(userInfo);
		
		UtilEtc.responseJsonValue(response, "OK");
	}
	
	// 직원 조회
	@RequestMapping(value = "/searchMember")
	public String searchMember(SearchVO searchVO, Model model) {
		
		if(searchVO.getSearchKeyword() != null && !"".equals(searchVO.getSearchKeyword())) {
			List<UserVO> listview = memberSvc.selectSearchMemberList(searchVO);
			model.addAttribute("listview", listview);
		}
		
		model.addAttribute("searchVO", searchVO);
		
		return "member/searchMember";
	}
	
}
