package com.miok.board.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.miok.board.dao.BoardDAO;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.FileVO;
import com.miok.common.SearchVO;

/*@Service*/
public class BoardServiceImp implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private DataSourceTransactionManager txManager;
	
	@Override
	public List<BoardVO> selectBoardList(SearchVO searchVO) {
		return boardDAO.selectBoardList(searchVO);
	}

	@Override
	public void insertBoard(BoardVO boardVO, List<FileVO> filelist, String[] fileno) {
		
		/* 트랜젝션적용
		 * 첨부파일오류시 게시물 저장도 적용 안되도록 rollback
		 */
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		try {
			if(boardVO.getBrdno() == null || "".equals(boardVO.getBrdno())){
				boardDAO.insertBoard(boardVO);
			}else {
				boardDAO.updateBoard(boardVO);
			}
			
			if(fileno != null) {
				HashMap delFile = new HashMap();
				delFile.put("fileno", fileno);
				boardDAO.deleteBoardFile(delFile);
			}
			
			for(FileVO fileVO : filelist) {
				fileVO.setParentPK(boardVO.getBrdno());
				boardDAO.insertBoardFile(fileVO);
			}
			txManager.commit(status);
		}catch (TransactionException e) {
			txManager.rollback(status);
			throw e;
		}

	}

	@Override
	public BoardVO selectBoardOne(String brdno) {
		return boardDAO.selectBoardOne(brdno);
	}

	@Override
	public void deleteBoardOne(String brdno) {
		boardDAO.deleteBoardOne(brdno);
	}

	@Override
	public int selectBoardCount(SearchVO searchVO) {
		return boardDAO.selectBoardCount(searchVO);
	}

	@Override
	public void updateBoardHit(String brdno) {
		boardDAO.updateBoardHit(brdno);
	}
	
	@Override
	public List<FileVO> selectBoardFileList(String brdno) {
		return boardDAO.selectBoardFileList(brdno);
	}

	@Override
	public void insertBoardReply(BoardReplyVO replyInfo) {
		
		if(replyInfo.getReno() == null || "".equals(replyInfo.getReno())) {
			if(replyInfo.getReparent() != null) {
				BoardReplyVO replyVO = boardDAO.selectBoardReplyParent(replyInfo.getReparent());
				replyInfo.setRedepth(replyVO.getRedepth());
				
				Integer reorder = replyVO.getReorder()+boardDAO.selectBoardReplyChild(replyInfo.getReparent()) + 1;
				replyInfo.setReorder(reorder);
				
				//insert하는 replyInfo이후의 댓글은 reorder를 전부 1증가
				replyVO.setReorder(reorder-1);
				boardDAO.updateBoardReplyOrder(replyVO);
			} else {
				Integer reorder = boardDAO.selectBoardReplyMaxOrder(replyInfo.getBrdno());
				replyInfo.setReorder(reorder);
			}
			boardDAO.insertBoardReply(replyInfo);
		}else {
			boardDAO.updateBoardReply(replyInfo);
		}
	}
	
	public List<BoardReplyVO> selectBoardReplyList(String brdno){
		return boardDAO.selectBoardReplyList(brdno);
	}
	
	@Override
	public boolean deleteBoardReply(String reno) {
		Integer cnt = boardDAO.selectBoardReplyChild(reno);
		
		if(cnt > 0) {
			return false;
		}
		
		boardDAO.updateBoardReplyOrderDelete(reno);
		boardDAO.deleteBoardReply(reno);
		
		return true;
	}
}
