package com.miok.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.board.service.BoardSvc;
import com.miok.board.vo.BoardSearchVO;
import com.miok.board.vo.BoardVO;
import com.miok.common.CountVO;
import com.miok.common.MakeExcel;
import com.miok.common.SearchVO;
import com.miok.common.Util4calen;
import com.miok.etc.service.EtcSvc;
import com.miok.main.service.SampleSvc;

@Controller
public class SampleController {
	
	@Autowired
	private SampleSvc sampleSvc;
	
	@Autowired
	private EtcSvc etcSvc;
	
	@Autowired
	private BoardSvc boardSvc;
	
	// 샘플1: 조직도/사용자 선택 샘플
	@RequestMapping(value = "/sample1")
	public String sample1(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		return "main/sample1";
	}
	
	// 샘플2: 날짜 선택 샘플
	@RequestMapping(value = "/sample2")
	public String sample2(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		String today = Util4calen.date2Str(Util4calen.getToday());
		model.addAttribute("today", today);
		
		return "main/sample2";
	}
	
	// 샘플3: 차트 사용 샘플
	@RequestMapping(value = "/sample3")
	public String sample3(HttpServletRequest request, Model model) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		List<CountVO> listview = sampleSvc.selectBoardGroupCount4Statistic();
		model.addAttribute("listview", listview);
		
		return "main/sample3";
	}
	
	// 샘플4: List & Excel 사용 샘플
	@RequestMapping(value = "/sample4")
	public String sample4(HttpServletRequest request, Model model, BoardSearchVO searchVO) {
		String userno = (String)request.getSession().getAttribute("userno");
		
		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);
		
		searchVO.pageCalculate(boardSvc.selectBoardCount(searchVO));
		List<BoardVO> listview = boardSvc.selectBoardList(searchVO);
		
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("listview", listview);
		
		return "main/sample4";
	}
	
	// Excel 생성 및 다운로드
	@RequestMapping(value = "/sample4Excel")
	public void sample4Excel(HttpServletRequest request, HttpServletResponse response, BoardSearchVO searchVO) {
		// 엑셀은 모든 리스트를 출력해야 하므로 페이징 처리를 하지 않음
		List<BoardVO> listview = boardSvc.selectBoardList(searchVO);
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("listview", listview);
		
		MakeExcel me = new MakeExcel();
		me.download(request, response, beans, me.get_Filename("selfproject"), "board.xlsx");
	}
	
	// 샘플5: Multi Checkbox
	@RequestMapping(value = "/sample5")
	public String sample5(HttpServletRequest request, Model model, BoardSearchVO searchVO) {
		String userno = (String) request.getSession().getAttribute("userno");

		Integer alertcount = etcSvc.selectAlertCount(userno);
		model.addAttribute("alertcount", alertcount);

		searchVO.pageCalculate(boardSvc.selectBoardCount(searchVO));
		List<BoardVO> listview = boardSvc.selectBoardList(searchVO);

		model.addAttribute("searchVO", searchVO);
		model.addAttribute("listview", listview);

		return "main/sample5";
	}
	
	// 체크박스 선택 삭제
	@RequestMapping(value = "/chkDelete")
    public String chkDelete(HttpServletRequest request, String[] checkRow) {
    	
        sampleSvc.deleteChk(checkRow);

        return "redirect:/sample5";
    }
}
