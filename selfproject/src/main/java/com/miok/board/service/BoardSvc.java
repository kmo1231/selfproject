package com.miok.board.service;

import java.util.List;

import com.miok.adminboard.vo.BoardGroupVO;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.Field3VO;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;

public interface BoardSvc {
	public BoardGroupVO selectBoardGroupOne4Used(String bgno);

	public Integer selectBoardCount(BoardSearchVO boardSearchVO);

	public List<BoardVO> selectBoardList(BoardSearchVO boardSearchVO);

	public List<BoardVO> selectNoticeList(BoardSearchVO boardSearchVO);

	public void insertBoard(BoardVO boardVO, List<FileVO> filelist, String[] fileno);

	public BoardVO selectBoardOne(Field3VO field3VO);

	public String selectBoardAuthChk(BoardVO param);

	public void updateBoardRead(Field3VO param);

	public void deleteBoardOne(String param);

	public void insertBoardLike(Field3VO param);

	public List<FileVO> selectBoardFileList(String param);

	public List<BoardReplyVO> selectBoardReplyList(String brdno);

	public BoardReplyVO insertBoardReply(BoardReplyVO replyInfo);

	public String selectBoardReplyAuthChk(BoardReplyVO param);

	public boolean deleteBoardReply(String reno);

}
