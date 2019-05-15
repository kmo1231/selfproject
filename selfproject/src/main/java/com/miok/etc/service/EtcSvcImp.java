package com.miok.etc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.etc.dao.EtcDAO;

@Service
public class EtcSvcImp implements EtcSvc{
	
	@Autowired
	private EtcDAO etcDAO;
	
	@Override
	public Integer selectAlertCount(String userno) {
		return etcDAO.selectAlertCount(userno);
	}

	@Override
	public List<BoardVO> selectAlertList4Ajax(String param) {
		return etcDAO.selectAlertList4Ajax(param);
	}

	@Override
	public List<BoardVO> selectAlertList(String param) {
		return etcDAO.selectAlertList(param);
	}

	@Override
	public Integer selectList4UserCount(BoardSearchVO boardSerchVO) {
		return etcDAO.selectList4UserCount(boardSerchVO);
	}

	@Override
	public List<?> selectList4User(BoardSearchVO boardSerchVO) {
		return etcDAO.selectList4User(boardSerchVO);
	}

}
