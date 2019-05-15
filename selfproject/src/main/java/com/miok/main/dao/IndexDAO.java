package com.miok.main.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.board.vo.BoardVO;

@Repository
public interface IndexDAO {
	public List<?> selectRecentNews();
	public List<?> selectTimeLine();
	public List<?> selectNoticeListTop5();
}
