package com.miok.main.service;

import java.util.List;

import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;

public interface IndexSvc {
	public List<BoardVO> selectRecentNews();
	public List<BoardReplyVO> selectTimeLine();
	public List<BoardVO> selectNoticeListTop5();
}
