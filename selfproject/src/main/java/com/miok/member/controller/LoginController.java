package com.miok.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.member.service.MemberSvc;
import com.miok.member.vo.LoginVO;
import com.miok.member.vo.UserVO;

@Controller
public class LoginController {
	// 쿠키만료기한
	private static final Integer cookieExpire = 60 * 60 * 24 * 30; // 1 month
	
	@Autowired
	private MemberSvc memberSvc;
	
	// 로그인 화면
	@RequestMapping(value = "/memberLogin")
	public String memberLogin(HttpServletRequest request, Model model) {
		String userid = get_cookie("sid", request);
		
		model.addAttribute("userid", userid);
		
		return "member/memberLogin";
	}
	
	// 로그인 처리
	@RequestMapping(value = "memberLoginChk")
	public String memberLoginChk(HttpServletRequest request, HttpServletResponse response, LoginVO loginInfo, Model model) {
		
		// id, pw 확인
		UserVO loginUserVO = memberSvc.selectMember4Login(loginInfo);
		
		// 로그인 실패
		if(loginUserVO == null) {
			model.addAttribute("msg", "로그인 할 수 없습니다.");
			return "common/message";
		}
		
		// 로그인 정보 DB 저장
		memberSvc.insertLogin(loginUserVO.getUserno());
		
		// 로그인 정보 session 저장
		HttpSession session = request.getSession();
		session.setAttribute("userid", loginUserVO.getUserid());
		session.setAttribute("userrole", loginUserVO.getUserrole());
		session.setAttribute("userno", loginUserVO.getUserno());
		session.setAttribute("usernm", loginUserVO.getUsernm());
		
		// remember me에 체크 시 쿠키에 아이디 저장
		if("Y".equals(loginInfo.getRemember())) {
			set_cookie("sid", loginInfo.getUserid(), response);
		} else {
			set_cookie("sid", "", response);
		}
		
		return "redirect:/index";
	}
	
	// 로그아웃 처리
	@RequestMapping(value = "memberLogout")
	public String memberLogout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		String userno = (String)session.getAttribute("userno");
		
		// 로그아웃 정보 DB 저장
		memberSvc.insertLogout(userno);
		
		// 세션에 저장된 정보 제거
		session.removeAttribute("userid");
		session.removeAttribute("userrole");
		session.removeAttribute("userno");
		session.removeAttribute("usernm");
		
		return "redirect:/memberLogin";
	}
	
	// 사용자가 관리자페이지 접근 시 오류페이지 출력.
	@RequestMapping(value = "noAuthMessage")
	public String noAuthMessage() {
		return "common/noAuth";
	}
	
	//==================================
	
	// 쿠키 저장
	public static void set_cookie(String cid, String value, HttpServletResponse response) {
		Cookie ck = new Cookie(cid, value);
		ck.setPath("/");
		ck.setMaxAge(cookieExpire);
		response.addCookie(ck);
	}
	
	// 쿠키 가져오기
	public static String get_cookie(String cid, HttpServletRequest request) {
		String ret = "";

		if (request == null) {
		    return ret;
		}
		
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
		    return ret;
		}
		
		for (Cookie ck : cookies) {
		    if (ck.getName().equals(cid)) {
		        ret = ck.getValue();
		        
		        ck.setMaxAge(cookieExpire);
		        break; 
		    }
		  }
		return ret;
	}
}
