package com.miok.admin.service;

import java.util.List;

import com.miok.admin.vo.BoardGroupVO;

public interface BoardGroupSvc {
	public List<BoardGroupVO> selectBoardGroupList();

	public void insertBoardGroup(BoardGroupVO bgVO);

	public BoardGroupVO selectBoardGroupOne(String bgno);

	public void deleteBoardGroup(String bgno);
}
