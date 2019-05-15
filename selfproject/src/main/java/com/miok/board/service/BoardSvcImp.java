package com.miok.board.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.miok.adminboard.vo.BoardGroupVO;
import com.miok.board.dao.BoardDAO;
import com.miok.board.vo.BoardReplyVO;
import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.Field3VO;
import com.miok.common.FileVO;

@Service
public class BoardSvcImp implements BoardSvc{
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private DataSourceTransactionManager txManager;
	
	static final Logger LOGGER = LoggerFactory.getLogger(BoardSvc.class);
	
	
	/**
	 * 게시판그룹 정보.
	 */
	@Override
	public BoardGroupVO selectBoardGroupOne4Used(String bgno) {
		return boardDAO.selectBoardGroupOne4Used(bgno);
	}
	
	/**
	 * 게시판 관련 메소드--------------------------------------------
	 */
	
	// 게시판 글 수
	@Override
	public Integer selectBoardCount(BoardSearchVO boardSearchVO) {
		return boardDAO.selectBoardCount(boardSearchVO);
	}
	
	// 게시판 글 리스트
	@Override
	public List<BoardVO> selectBoardList(BoardSearchVO boardSearchVO) {
		return boardDAO.selectBoardList(boardSearchVO);
	}
	
	// 공지사항 리스트
	@Override
	public List<BoardVO> selectNoticeList(BoardSearchVO boardSearchVO) {
		return boardDAO.selectNoticeList(boardSearchVO);
	}

	// 글 저장
	@Override
	public void insertBoard(BoardVO boardVO, List<FileVO> filelist, String[] fileno) {
		
		// 트랜젝션적용 : 첨부파일오류시 게시물 저장도 적용 안되도록 rollback
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
				HashMap<String, String[]> delFile = new HashMap<String, String[]>();
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
			LOGGER.error("insertBoard");
		}

	}

	// 게시글 정보 조회
	@Override
	public BoardVO selectBoardOne(Field3VO field3VO) {
		return boardDAO.selectBoardOne(field3VO);
	}
	
	// 게시판 수정 & 삭제 권한 확인
	@Override
	public String selectBoardAuthChk(BoardVO boardVO) {
		return boardDAO.selectBoardAuthChk(boardVO);
	}
	
	// 글 읽은 정보 저장
	@Override
	public void updateBoardRead(Field3VO field3VO) {
		boardDAO.updateBoardRead(field3VO);
	}
	
	// 게시글 하나 삭제
	@Override
	public void deleteBoardOne(String brdno) {
		boardDAO.deleteBoardOne(brdno);
	}

	// 종아요 저장
	@Override
	public void insertBoardLike(Field3VO field3VO) {
		// 트랜젝션 설정
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try {
        	// 좋아요 정보 DB 저장
        	boardDAO.insertBoardLike(field3VO);
        	// 좋아요 1 증가
        	boardDAO.updateBoard4Like(field3VO);
        	
        	txManager.commit(status);
        } catch (TransactionException e) {
        	txManager.rollback(status);
        	LOGGER.error("insertBoardLike");
		}
	}
	
	@Override
	public List<FileVO> selectBoardFileList(String brdno) {
		return boardDAO.selectBoardFileList(brdno);
	}

	//=====================================
	
	// 해당 게시글 댓글 리스트 조회
	public List<BoardReplyVO> selectBoardReplyList(String brdno){
		return boardDAO.selectBoardReplyList(brdno);
	}
	
	// 댓글 저장
	@Override
	public BoardReplyVO insertBoardReply(BoardReplyVO boardReplyVO) {
		// 댓글 추가
		if(boardReplyVO.getReno() == null || "".equals(boardReplyVO.getReno())) {
			// 자식 댓글일 경우 (다른 댓글 아래에 추가한 댓글)
			if(boardReplyVO.getReparent() != null) {
				// 부모 댓글 정보
				BoardReplyVO replyVO = boardDAO.selectBoardReplyParent(boardReplyVO.getReparent());
				
				// depth 설정
				boardReplyVO.setRedepth(replyVO.getRedepth());
				
				// 자식댓글 정렬 오름차순을 위한 reorder설정
				Integer reorder = replyVO.getReorder()+boardDAO.selectBoardReplyChild(boardReplyVO.getReparent()) + 1;
				boardReplyVO.setReorder(reorder);
				
				// insert하는 boardReplyVO이후의 댓글은 reorder를 전부 1증가
				replyVO.setReorder(reorder-1);
				boardDAO.updateBoardReplyOrder(replyVO);
			} else {
				// 부모 댓글일 경우 (1단계)
				Integer reorder = boardDAO.selectBoardReplyMaxOrder(boardReplyVO.getBrdno());
				boardReplyVO.setReorder(reorder);
			}
			boardDAO.insertBoardReply(boardReplyVO);
		
		}else { 
			//댓글 수정
			boardDAO.updateBoardReply(boardReplyVO);
		}
		return boardDAO.selectBoardReplyOne(boardReplyVO.getReno());
	}
	
	// 댓글 수정 & 삭제 권한
	@Override
	public String selectBoardReplyAuthChk(BoardReplyVO boardReplyVO) {
		return boardDAO.selectBoardReplyAuthChk(boardReplyVO);
	}
	
	// 댓글 삭제
	@Override
	public boolean deleteBoardReply(String reno) {
		// 자식 댓글이 있으면 삭제 안됨
		Integer cnt = boardDAO.selectBoardReplyChild(reno);
		if(cnt > 0) {
			return false;
		}
		
		boardDAO.updateBoardReplyOrder4Delete(boardDAO.selectBoardReplyOne(reno));
		boardDAO.deleteBoardReply(reno);
		
		return true;
	}
}
