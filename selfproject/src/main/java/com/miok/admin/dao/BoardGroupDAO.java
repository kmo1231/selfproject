package com.miok.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.admin.vo.BoardGroupVO;

@Repository
public interface BoardGroupDAO {
	public List<BoardGroupVO> selectBoardGroupList();

	public void insertBoardGroup(BoardGroupVO bgVO);

	public void updateBoardGroup(BoardGroupVO bgVO);

	public BoardGroupVO selectBoardGroupOne(String bgno);

	public void deleteBoardGroup(String bgno);

}
