package com.miok.board.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;

/*@Repository*/
public interface BoardDAO {
	public List<BoardVO> selectBoardList(SearchVO searchVO);
	public void insertBoard(BoardVO boardVO);
	public void updateBoard(BoardVO boardVO);
	public BoardVO selectBoardOne(String brdno);
	public void deleteBoardOne(String brdno);
	public int selectBoardCount(SearchVO searchVO);
	public void updateBoardHit(String brdno);
	public void insertBoardFile(FileVO fileVO);
	public List<FileVO> selectBoardFileList(String brdno);
	public void deleteBoardFile(HashMap delFile);
	
	/*댓글*/
	public List<BoardReplyVO> selectBoardReplyList(String brdno);
	public void insertBoardReply(BoardReplyVO replyInfo);
	public void updateBoardReply(BoardReplyVO replyInfo);
	public void deleteBoardReply(String reno);
	
	/*무한댓글*/
	public BoardReplyVO selectBoardReplyParent(String reparent);
	public void updateBoardReplyOrder(BoardReplyVO replyVO);
	public Integer selectBoardReplyMaxOrder(String brdno);
	public Integer selectBoardReplyChild(String reno);
	public void updateBoardReplyOrderDelete(String reno);
}
