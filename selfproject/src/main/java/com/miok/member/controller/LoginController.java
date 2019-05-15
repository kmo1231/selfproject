package com.miok.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.member.service.MemberSvc;

/*@Controller*/
public class LoginController {
	//쿠키만료기한
	private static final Integer cookieExpire = 60 * 60 * 24 * 30; // 1 month
	
	@Autowired
	private MemberSvc memberSvc;
	
	@RequestMapping(value = "/memberLogin")
	public String memberLogin(HttpServletRequest request, Model model) {
		return null;
	}

	/*
	 * 쿠키관련메소드 -----------------------------------------
	 */
	
	//쿠키 저장
	public static void set_cookie(String cid, String value, HttpServletResponse response) {
		Cookie ck = new Cookie(cid, value);
		ck.setPath("/");
		ck.setMaxAge(cookieExpire);
		response.addCookie(ck);
	}
	
	//쿠키 가져오기
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
