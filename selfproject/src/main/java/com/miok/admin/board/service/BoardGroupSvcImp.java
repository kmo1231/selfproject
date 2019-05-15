package com.miok.admin.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.admin.board.dao.BoardGroupDAO;
import com.miok.admin.board.vo.BoardGroupVO;

/*@Service*/
public class BoardGroupSvcImp implements BoardGroupSvc{
	@Autowired
	BoardGroupDAO boardGroupDAO;

	@Override
	public List<BoardGroupVO> selectBoardGroupList() {
		return boardGroupDAO.selectBoardGroupList();
	}

	//게시판 그룹 수정 및 생성
	@Override
	public void insertBoardGroup(BoardGroupVO bgVO) {
		if("".equals(bgVO.getBgparent())){
			bgVO.setBgparent(null);
		}
		
		if(bgVO.getBgno() == null || "".equals(bgVO.getBgno())) {
			boardGroupDAO.insertBoardGroup(bgVO);
		} else {
			boardGroupDAO.updateBoardGroup(bgVO);
		}
		
	}

	@Override
	public BoardGroupVO selectBoardGroupOne(String bgno) {
		return boardGroupDAO.selectBoardGroupOne(bgno);
	}

	@Override
	public BoardGroupVO selectBoardGroupOneUsed(String bgno) {
		return boardGroupDAO.selectBoardGroupOneUsed(bgno);
	}

	@Override
	public void deleteBoardGroup(String bgno) {
		boardGroupDAO.deleteBoardGroup(bgno);
	}

}
