package com.miok.etc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.board.vo.BoardVO;
import com.miok.etc.service.EtcSvc;

@Controller
public class AlertMsgController {
	
	@Autowired
	private EtcSvc etcSvc;
	
	/**
	 * alert list 전체.
	 */
	@RequestMapping(value = "/alertList")
	public String alertList(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		List<BoardVO> listview = etcSvc.selectAlertList(userno);
		model.addAttribute("listview", listview);
		
		return "etc/alertList";
	}
	
	/**
	 * alert list top 5.
	 */
	@RequestMapping(value = "/alertList4Ajax")
	public String alertList4Ajax(HttpServletRequest request, Model model) {
//		String userno = (String)request.getSession().getAttribute("userno");
		String userno = "2";
		
		List<BoardVO> listview = etcSvc.selectAlertList4Ajax(userno);
		
		model.addAttribute("listview", listview);
		
		return "etc/alertList4Ajax";
	}
}
