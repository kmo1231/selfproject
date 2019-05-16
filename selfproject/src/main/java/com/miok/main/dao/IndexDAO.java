package com.miok.main.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;

@Repository
public interface IndexDAO {
	public List<BoardVO> selectRecentNews();

	public List<BoardReplyVO> selectTimeLine();

	public List<BoardVO> selectNoticeListTop5();
}
