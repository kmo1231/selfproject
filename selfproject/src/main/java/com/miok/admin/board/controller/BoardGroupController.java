package com.miok.admin.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.admin.board.service.BoardGroupSvc;
import com.miok.admin.board.vo.BoardGroupVO;
import com.miok.common.TreeMaker;

/*@Controller*/
public class BoardGroupController {
	
	@Autowired
	private BoardGroupSvc boardGroupSvc;
	
	//게시판 그룹 리스트
	@RequestMapping(value = "/boardGroupList")
	public String boardGroupList(Model model) {
		List<BoardGroupVO> boardList = boardGroupSvc.selectBoardGroupList();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(boardList);
		
		model.addAttribute("treeStr", treeStr);
		
		return "/boardgroup/BoardGroupList";
	}
	
	@RequestMapping(value = "/testajax")
	public void testajax(HttpServletResponse response) {
		List<BoardGroupVO> boardList = boardGroupSvc.selectBoardGroupList();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(boardList);
		
		//model.addAttribute("treeStr", treeStr);
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			response.getWriter().print(mapper.writeValueAsString(tm.makeTreeByHierarchy(boardList)));
		} catch (IOException e) {
			System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
		}
	}
	
	//게시판 그룹 읽기
	@RequestMapping(value = "/boardGroupView")
	public void boardGroupView(HttpServletRequest request, HttpServletResponse response) {
		String bgno = request.getParameter("bgno");
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOne(bgno);
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			response.getWriter().print(mapper.writeValueAsString(bgInfo));
		} catch (IOException e) {
			System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
		}
	}
	
	@RequestMapping(value = "/boardGroupSave")
	public void boardGroupSave(HttpServletResponse response, BoardGroupVO bgInfo) {
		boardGroupSvc.insertBoardGroup(bgInfo);
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			response.getWriter().print(mapper.writeValueAsString(bgInfo));
		} catch (IOException e) {
			System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
		}
	}
	
	@RequestMapping(value = "/boardGroupDelete")
	public void boardGroupDelete(HttpServletRequest request, HttpServletResponse response) {
		String bgno = request.getParameter("bgno");
		boardGroupSvc.deleteBoardGroup(bgno);
		
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			response.getWriter().print(mapper.writeValueAsString("OK"));
		} catch (IOException e) {
			System.out.println("오류: 게시판 그룹에 문제가 발생했습니다.");
		}
	}
}
