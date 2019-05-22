package com.miok.board.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.admin.vo.BoardGroupVO;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.Field3VO;
import com.miok.common.FileVO;

@Repository
public interface BoardDAO {
	public BoardGroupVO selectBoardGroupOne4Used(String param);
	
	public Integer selectBoardCount(BoardSearchVO boardSearchVO);
	
	public List<BoardVO> selectBoardList(BoardSearchVO boardSearchVO);
	
	public List<BoardVO> selectNoticeList(BoardSearchVO boardSearchVO);
	
	public void insertBoard(BoardVO boardVO);
	
	public void updateBoard(BoardVO boardVO);
	
	public BoardVO selectBoardOne(Field3VO field3VO);
	
	public String selectBoardAuthChk(BoardVO boardVO);
	
	public void updateBoardRead(Field3VO field3VO);
	
	public void insertBoardLike(Field3VO param);
	
	public void updateBoard4Like(Field3VO field3vo);
	
	public void deleteBoardOne(String brdno);
	
	//==============================
	
	public List<FileVO> selectBoardFileList(String brdno);
	
	public void insertBoardFile(FileVO fileVO);
	
	public void deleteBoardFile(HashMap map);
	
	//===============================
	
	public List<BoardReplyVO> selectBoardReplyList(String brdno);
	
	public void insertBoardReply(BoardReplyVO boardReplyVO);
	
	public BoardReplyVO selectBoardReplyOne(String reno);
	
	public BoardReplyVO selectBoardReplyParent(String reparent);
	
	public void updateBoardReplyOrder(BoardReplyVO boardReplyVO);
	
	public Integer selectBoardReplyMaxOrder(String brdno);
	
	public Integer selectBoardReplyChild(String reparent);
	
	public String selectBoardReplyAuthChk(BoardReplyVO param);
	
	public boolean deleteBoardReply(String reno);
	
	public void updateBoardReplyOrder4Delete(BoardReplyVO boardReplyVO);
	
	public void updateBoardReply(BoardReplyVO boardReplyVO);
	
}
