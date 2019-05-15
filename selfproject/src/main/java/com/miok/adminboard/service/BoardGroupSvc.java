package com.miok.adminboard.service;

import java.util.List;

import com.miok.adminboard.vo.BoardGroupVO;

public interface BoardGroupSvc {
	public List<BoardGroupVO> selectBoardGroupList();
	public void insertBoardGroup(BoardGroupVO bgVO);
	public BoardGroupVO selectBoardGroupOne(String bgno);
	public BoardGroupVO selectBoardGroupOneUsed(String bgno);
	public void deleteBoardGroup(String bgno);
}
