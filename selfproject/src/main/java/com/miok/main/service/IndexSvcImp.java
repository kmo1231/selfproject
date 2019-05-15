package com.miok.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;
import com.miok.main.dao.IndexDAO;

@Service
public class IndexSvcImp implements IndexSvc{
	@Autowired
	private IndexDAO indexDao;
	
	@Override
	public List<BoardVO> selectRecentNews() {
		return indexDao.selectRecentNews();
	}

	@Override
	public List<BoardReplyVO> selectTimeLine() {
		return indexDao.selectTimeLine();
	}

	@Override
	public List<BoardVO> selectNoticeListTop5() {
		return indexDao.selectNoticeListTop5();
	}

}
