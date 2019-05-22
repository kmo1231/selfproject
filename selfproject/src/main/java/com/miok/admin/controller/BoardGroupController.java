package com.miok.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.service.BoardGroupSvc;
import com.miok.admin.vo.BoardGroupVO;
import com.miok.common.TreeMaker;
import com.miok.common.UtilEtc;
import com.miok.etc.service.EtcSvc;

@Controller
public class BoardGroupController {
	
	@Autowired
	private BoardGroupSvc boardGroupSvc;
	
	@Autowired
	private EtcSvc etcSvc;
	
	//게시판 그룹 리스트
	@RequestMapping(value = "/adBoardGroupList")
	public String boardGroupList(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		List<BoardGroupVO> listview = boardGroupSvc.selectBoardGroupList();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		model.addAttribute("treeStr", treeStr);
		
		return "admin/board/BoardGroupList";
	}
	
	// 게시판 그룹 추가 저장
	@RequestMapping(value = "/adBoardGroupSave")
	public void boardGroupSave(HttpServletResponse response, BoardGroupVO bgInfo) {
		boardGroupSvc.insertBoardGroup(bgInfo);
		
		UtilEtc.responseJsonValue(response, bgInfo);
	}
	
	
	
	//게시판 그룹 조회
	@RequestMapping(value = "/adBoardGroupRead")
	public void boardGroupRead(HttpServletRequest request, HttpServletResponse response) {
		String bgno = request.getParameter("bgno");
		
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOne(bgno);
		
		UtilEtc.responseJsonValue(response, bgInfo);
	}
	
	
	// 게시판 그룹 삭제
	@RequestMapping(value = "/adBoardGroupDelete")
	public void boardGroupDelete(HttpServletRequest request, HttpServletResponse response) {
		String bgno = request.getParameter("bgno");
		
		boardGroupSvc.deleteBoardGroup(bgno);
		
		UtilEtc.responseJsonValue(response, "OK");
	}
}
