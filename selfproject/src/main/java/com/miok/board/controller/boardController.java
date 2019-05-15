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
import com.miok.common.FileUtil;
import com.miok.common.FileVO;
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

	// 리스트
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
		
		// 전체리스트 출력?
		if(boardSearchVO.getBgno() == null || "".equals(boardSearchVO.getBgno())) {
			return "board/BoardListAll";
		}
		
		return "board/boardList";
	}

	// 글쓰기
	/*@RequestMapping(value = "/boardForm")
	public String boardForm(HttpServletRequest request, Model model) {
		String bgno = request.getParameter("bgno");
		String brdno = request.getParameter("brdno");
		
		//수정일 경우
		if(brdno != null) {
			BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
			List<FileVO> filelist = boardSvc.selectBoardFileList(brdno);
			bgno = boardInfo.getBgno();
			
			model.addAttribute("boardInfo", boardInfo);
			model.addAttribute("filelist", filelist);
		}
		
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOneUsed(bgno);
		if(bgInfo == null) {
			return "/boardgroup/BoardGroupFail";
		}
		
		model.addAttribute("bgno", bgno);
		model.addAttribute("bgInfo", bgInfo);
		
		return "/board/boardForm";
	}*/
	
	// 글쓰기 저장
	@RequestMapping(value = "/boardSave")
   	public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
   		String[] fileno = request.getParameterValues("fileno");
   		
   		FileUtil fs = new FileUtil();
   		List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());
		
		boardSvc.insertBoard(boardInfo, filelist, fileno);

   		return "redirect:/boardList?bgno="+boardInfo.getBgno();
    }
	
	// 글읽기
	/*@RequestMapping(value = "/boardView")
	public String boardView(HttpServletRequest request, Model model) {
		String brdno = request.getParameter("brdno");
		
		boardSvc.updateBoardHit(brdno);
		BoardVO boardInfo = boardSvc.selectBoardOne(brdno);
		List<FileVO> filelist = boardSvc.selectBoardFileList(brdno);
		
		List<BoardReplyVO> replylist = boardSvc.selectBoardReplyList(brdno);
		
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOneUsed(boardInfo.getBgno());
		if(bgInfo == null) {
			return "/boardgroup/BoardGroupFail";
		}
		
		model.addAttribute("boardInfo", boardInfo);
		model.addAttribute("filelist", filelist);
		model.addAttribute("replylist", replylist);
		model.addAttribute("bgInfo", bgInfo);
		
		return "/board/boardView";
	}*/

	// 글삭제
	@RequestMapping(value = "/boardDelete")
	public String boardDelete(HttpServletRequest request) {
		String brdno = request.getParameter("brdno");
		String bgno = request.getParameter("bgno");
		
		boardSvc.deleteBoardOne(brdno);

		return "redirect:/boardList?bgno="+bgno;
	}
	
	// 댓글저장
	@RequestMapping(value = "/boardReplySave")
	public String boardReplySave(HttpServletRequest request, BoardReplyVO replyInfo) {
		boardSvc.insertBoardReply(replyInfo);
		
		return "redirect:/boardView?brdno="+replyInfo.getBrdno();
	}
	
	// 댓글저장(ajax_서버에서 반환될 때 JSP를 이용하여 정리된 값을 만들어넘김)
	@RequestMapping(value = "/boardReplySaveAjaxJSP")
    public String board7ReplySaveAjaxJSP(BoardReplyVO ReplyInfo, Model model) {
        
        boardSvc.insertBoardReply(ReplyInfo);

        model.addAttribute("replyInfo", ReplyInfo);
        
        return "BoardViewAjaxReply";
    }
	
	// 댓글삭제
	@RequestMapping(value = "/boardReplyDelete")
	public String boardReplyDelete(BoardReplyVO replyInfo) {
		if(!boardSvc.deleteBoardReply(replyInfo.getReno())) {
			return "BoardFailure";
		}
		
		return "redirect:/boardView?brdno="+replyInfo.getBrdno();
	}
	
	// 댓글삭제(ajax)
	@RequestMapping(value="/boardReplyDeleteAjax")
	public void boardReplyDeleteAjax(HttpServletResponse response, BoardReplyVO replyInfo) {
		ObjectMapper mapper=new ObjectMapper();
		response.setContentType("application/json:charset=UTF-8");
		
		try {
			if(!boardSvc.deleteBoardReply(replyInfo.getReno())) {
				response.getWriter().print(mapper.writeValueAsString("Fail"));
			} else {
				response.getWriter().print(mapper.writeValueAsString("OK"));
			}
		} catch (IOException e) {
			System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
		}
	}
}
