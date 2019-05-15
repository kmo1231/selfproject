package com.miok.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.adminboard.service.BoardGroupSvc;
import com.miok.adminboard.vo.BoardGroupVO;
import com.miok.board.service.BoardSvc;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.FileUtil;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;


/*@Controller*/
public class boardController {

	@Autowired
	private BoardSvc boardService;
	@Autowired
	private BoardGroupSvc boardGroupSvc;

	// 리스트
	@RequestMapping(value = "/boardList")
	public String boardList(SearchVO searchVO, Model model) {
		
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOneUsed(searchVO.getBgno());
		if(bgInfo == null) {
			return "/boardgroup/BoardGroupFail";
		}
		
		if(searchVO.getBgno() == null) {
			searchVO.setBgno("1");
		}
		
		searchVO.pageCalculate(boardService.selectBoardCount(searchVO));
		
		List<BoardVO> boardList = boardService.selectBoardList(searchVO);

		model.addAttribute("boardList", boardList);
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("bgInfo", bgInfo);
		
		return "/board/boardList";
	}

	// 글쓰기
	@RequestMapping(value = "/boardForm")
	public String boardForm(HttpServletRequest request, Model model) {
		String bgno = request.getParameter("bgno");
		String brdno = request.getParameter("brdno");
		
		//수정일 경우
		if(brdno != null) {
			BoardVO boardInfo = boardService.selectBoardOne(brdno);
			List<FileVO> filelist = boardService.selectBoardFileList(brdno);
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
	}
	
	// 글쓰기 저장
	@RequestMapping(value = "/boardSave")
   	public String boardSave(HttpServletRequest request, BoardVO boardInfo) {
   		String[] fileno = request.getParameterValues("fileno");
   		
   		FileUtil fs = new FileUtil();
   		List<FileVO> filelist = fs.saveAllFiles(boardInfo.getUploadfile());
		
		boardService.insertBoard(boardInfo, filelist, fileno);

   		return "redirect:/boardList?bgno="+boardInfo.getBgno();
    }
	
	// 글읽기
	@RequestMapping(value = "/boardView")
	public String boardView(HttpServletRequest request, Model model) {
		String brdno = request.getParameter("brdno");
		
		boardService.updateBoardHit(brdno);
		BoardVO boardInfo = boardService.selectBoardOne(brdno);
		List<FileVO> filelist = boardService.selectBoardFileList(brdno);
		
		List<BoardReplyVO> replylist = boardService.selectBoardReplyList(brdno);
		
		BoardGroupVO bgInfo = boardGroupSvc.selectBoardGroupOneUsed(boardInfo.getBgno());
		if(bgInfo == null) {
			return "/boardgroup/BoardGroupFail";
		}
		
		model.addAttribute("boardInfo", boardInfo);
		model.addAttribute("filelist", filelist);
		model.addAttribute("replylist", replylist);
		model.addAttribute("bgInfo", bgInfo);
		
		return "/board/boardView";
	}

	// 글삭제
	@RequestMapping(value = "/boardDelete")
	public String boardDelete(HttpServletRequest request) {
		String brdno = request.getParameter("brdno");
		String bgno = request.getParameter("bgno");
		
		boardService.deleteBoardOne(brdno);

		return "redirect:/boardList?bgno="+bgno;
	}
	
	// 댓글저장
	@RequestMapping(value = "/boardReplySave")
	public String boardReplySave(HttpServletRequest request, BoardReplyVO replyInfo) {
		boardService.insertBoardReply(replyInfo);
		
		return "redirect:/boardView?brdno="+replyInfo.getBrdno();
	}
	
	// 댓글저장(ajax_서버에서 반환될 때 JSP를 이용하여 정리된 값을 만들어넘김)
	@RequestMapping(value = "/boardReplySaveAjaxJSP")
    public String board7ReplySaveAjaxJSP(BoardReplyVO ReplyInfo, Model model) {
        
        boardService.insertBoardReply(ReplyInfo);

        model.addAttribute("replyInfo", ReplyInfo);
        
        return "BoardViewAjaxReply";
    }
	
	// 댓글삭제
	@RequestMapping(value = "/boardReplyDelete")
	public String boardReplyDelete(BoardReplyVO replyInfo) {
		if(!boardService.deleteBoardReply(replyInfo.getReno())) {
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
			if(!boardService.deleteBoardReply(replyInfo.getReno())) {
				response.getWriter().print(mapper.writeValueAsString("Fail"));
			} else {
				response.getWriter().print(mapper.writeValueAsString("OK"));
			}
		} catch (IOException e) {
			System.out.println("오류: 댓글 삭제에 문제가 발생했습니다.");
		}
	}
}
