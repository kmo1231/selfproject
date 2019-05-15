package com.miok.board.service;

import java.util.List;

import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;

public interface BoardSvc {
	public List<BoardVO> selectBoardList(SearchVO searchVO);
	public void insertBoard(BoardVO boardVO, List<FileVO> filelist, String[] fileno);
	public BoardVO selectBoardOne(String brdno);
	public void deleteBoardOne(String brdno);
	public int selectBoardCount(SearchVO searchVO);
	public void updateBoardHit(String brdno);
	public List<FileVO> selectBoardFileList(String brdno);
	
	public List<BoardReplyVO> selectBoardReplyList(String brdno);
	public void insertBoardReply(BoardReplyVO replyInfo);
	public boolean deleteBoardReply(String reno);
}
