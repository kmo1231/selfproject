package com.miok.main.service;

import java.util.List;

import com.miok.board.vo.BoardVO;

public interface IndexSvc {
	public List<?> selectRecentNews();
	public List<?> selectTimeLine();
	public List<?> selectNoticeListTop5();
}
