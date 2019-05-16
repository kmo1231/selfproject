package com.miok.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.adminboard.service.BoardGroupSvc;
import com.miok.adminboard.vo.BoardGroupVO;
import com.miok.board.service.BoardSvc;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.Field3VO;
import com.miok.common.FileUtil;
import com.miok.common.FileVO;
import com.miok.common.TreeMaker;
import com.miok.common.UtilEtc;
import com.miok.etc.service.EtcSvc;

@Controller
public class boardController {

	@Autowired
	private BoardSvc boardSvc;

	@Autowired
	private BoardGroupSvc boardGroupSvc;

	@Autowired
	private EtcSvc etcSvc;
	
	static final Logger LOGGER = LoggerFactory.getLogger(boardController.class);

	// 글 목록
	@RequestMapping(value = "/boardList")
	public String boardList(HttpServletRequest request, BoardSearchVO boardSearchVO, Model model) {
		// 다국어지원을 위한 설정
		String globalkeyword = request.getParameter("globalkeyword");
		if(globalkeyword != null || !"".equals(globalkeyword)) {
			boardSearchVO.setSearchKeyword(globalkeyword);
		}
		
		// 사용자 정보 가져오기
		String userno = (String)request.getSession().getAttribute("userno");
		
		// 사용자 알림 개수 저장
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		// 생성되지 않은 게시판일 경우
		if(boardSearchVO.getBgno() != null && !"".equals(boardSearchVO.getBgno())) {
			BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne4Used(boardSearchVO.getBgno());
			if(bgInfo == null) {
				return "board/BoardGroupFail";
			}
			model.addAttribute("bgInfo", bgInfo);
		}
		
		List<BoardVO> noticelist = boardSvc.selectNoticeList(boardSearchVO);
		
		// 페이지계산용
		boardSearchVO.pageCalculate(boardSvc.selectBoardCount(boardSearchVO));
		
		List<BoardVO> listview = boardSvc.selectBoardList(boardSearchVO);
		
		model.addAttribute("listview", listview);
		model.addAttribute("searchVO", boardSearchVO);
		model.addAttribute("noticelist", noticelist);
		
		// 전체리스트 출력
		if(boardSearchVO.getBgno() == null || "".equals(boardSearchVO.getBgno())) {
			return "board/BoardListAll";
		}
		
		return "board/BoardList";
	}
	
	// 게시판 종류 리스트
	@RequestMapping(value = "/boardListByAjax")
	public void boardListByAjax(HttpServletResponse response, Model model) {
		List<BoardGroupVO> listview = boardGroupSvc.selectBoardGroupList();
		
		TreeMaker tm = new TreeMaker();
		String treeStr = tm.makeTreeByHierarchy(listview);
		
		response.setContentType("application/json;charset=UTF-8");
		
		try {
			response.getWriter().print(treeStr);
		} catch (IOException e) {
			LOGGER.error("boardListByAjax");
		}
		
	}

	// 글 쓰기
	@RequestMapping(value = "/boardForm")
	public String boardForm(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		String bgno = request.getParameter("bgno");
		String brdno = request.getParameter("brdno");
		
		// 수정일 경우
		if(brdno != null) {
			BoardVO boardInfo = boardSvc.selectBoardOne(new Field3VO(brdno, null, null));
			// 파일 리스트
			List<FileVO> listview = boardSvc.selectBoardFileList(brdno);
			
			model.addAttribute("boardInfo", boardInfo);
			model.addAttribute("listview", listview);
			
			bgno = boardInfo.getBgno();
		}
		
		// 사용할 수 있는 게시판인지 체크
		BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne4Used(bgno);
		if(bgInfo == null) {
			return "/board/BoardGroupFail";
		}
		
		model.addAttribute("bgno", bgno);
		model.addAttribute("bgInfo", bgInfo);
		
		return "board/BoardForm";
	}
	
	// 글 저장
	@RequestMapping(value = "/boardSave")
   	public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
   		String userno = (String)request.getSession().getAttribute("userno");
   		boardInfo.setUserno(userno);
		
   		// 글 수정일 경우 권한 확인
   		if(boardInfo.getBrdno() != null && !"".equals(boardInfo.getBrdno())) {
   			String chk = boardSvc.selectBoardAuthChk(boardInfo);
   			if(chk == null) {
   				return "common/noAuth";
   			}
   		}
   		
		String[] fileno = request.getParameterValues("fileno");
   		
   		FileUtil fs = new FileUtil();
   		List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());
		
		boardSvc.insertBoard(boardInfo, filelist, fileno);

   		return "redirect:/boardList?bgno="+boardInfo.getBgno();
    }
	
	// 글읽기
	@RequestMapping(value = "/boardRead")
	public String boardRead(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		String bgno = request.getParameter("bgno");
		String brdno = request.getParameter("brdno");
		
		Field3VO f3 = new Field3VO(brdno, userno, null);
		
		// 글 읽은 정보 DB 저장
		boardSvc.updateBoardRead(f3);
		
		BoardVO boardInfo = boardSvc.selectBoardOne(f3);
		List<FileVO> listview = boardSvc.selectBoardFileList(brdno);
		List<BoardReplyVO> replylist = boardSvc.selectBoardReplyList(brdno);
		
		BoardGroupVO bgInfo = boardSvc.selectBoardGroupOne4Used(boardInfo.getBgno());
		// 사용 가능한 게시판인지 확인
		if(bgInfo == null) {
			return "/board/BoardGroupFail";
		}
		
		model.addAttribute("boardInfo", boardInfo);
		model.addAttribute("listview", listview);
		model.addAttribute("replylist", replylist);
		model.addAttribute("bgno", bgno);
		model.addAttribute("bgInfo", bgInfo);
		
		return "board/BoardRead";
	}

	// 글삭제
	@RequestMapping(value = "/boardDelete")
	public String boardDelete(HttpServletRequest request) {
		String userno = (String)request.getSession().getAttribute("userno");
		String brdno = request.getParameter("brdno");
		String bgno = request.getParameter("bgno");
		
		BoardVO boardInfo = new BoardVO();
		boardInfo.setBrdno(brdno);
		boardInfo.setUserno(userno);
		String chk = boardSvc.selectBoardAuthChk(boardInfo);
		if(chk == null) {
			return "common/noAuth";
		}
		
		boardSvc.deleteBoardOne(brdno);

		return "redirect:/boardList?bgno="+bgno;
	}
	
	//=====================================
	
	// 좋아요 저장
	@RequestMapping(value = "/addBoardLike")
	public void addBoardLike(HttpServletRequest request, HttpServletResponse response) {
		String userno = (String)request.getSession().getAttribute("userno");
		String brdno = request.getParameter("brdno");
		
		boardSvc.insertBoardLike(new Field3VO(brdno, userno, null));
		
		UtilEtc.responseJsonValue(response, "OK");
	}
	
	//=====================================
	
	// 댓글저장
	@RequestMapping(value = "/boardReplySave")
	public String boardReplySave(HttpServletRequest request, HttpServletResponse response, BoardReplyVO replyInfo, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		replyInfo.setUserno(userno);
		
		// 댓글 수정 일 경우 권한 확인
		if(replyInfo.getReno() != null && !"".equals(replyInfo.getReno())) {
			String chk = boardSvc.selectBoardReplyAuthChk(replyInfo);
			if(chk == null) {
				UtilEtc.responseJsonValue(response, "");
				return null;
			}
		}
		
		replyInfo = boardSvc.insertBoardReply(replyInfo);
		model.addAttribute("replyInfo", replyInfo);
		
		return "board/BoardReadAjax4Reply";
	}
	
	// 댓글삭제
	@RequestMapping(value = "/boardReplyDelete")
	public void boardReplyDelete(HttpServletRequest request, HttpServletResponse response, BoardReplyVO replyInfo) {
		String userno = (String)request.getSession().getAttribute("userno");
		replyInfo.setUserno(userno);
		
		// 댓글삭제 권한 확인
		if(replyInfo.getReno() != null && !"".equals(replyInfo.getRedate())) {
			String chk = boardSvc.selectBoardReplyAuthChk(replyInfo);
			if(chk == null) {
				UtilEtc.responseJsonValue(response, "FailAuth");
			}
			
		}
		
		// 결과 반환
		if(!boardSvc.deleteBoardReply(replyInfo.getReno())) {
			UtilEtc.responseJsonValue(response, "Fail");
		} else {
			UtilEtc.responseJsonValue(response, "OK");
		}
		
	}
	
}
