package com.miok.etc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;

@Repository
public interface EtcDAO {
	public Integer selectAlertCount(String userno);

	public List<BoardVO> selectAlertList4Ajax(String param);

	public List<BoardVO> selectAlertList(String param);

	public Integer selectList4UserCount(BoardSearchVO boardSerchVO);

	public List<?> selectList4User(BoardSearchVO boardSerchVO);
}
