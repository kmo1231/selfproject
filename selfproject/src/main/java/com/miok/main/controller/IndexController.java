package com.miok.main.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miok.common.DateVO;
import com.miok.common.Util4calen;
import com.miok.main.service.IndexSvc;

@Controller
public class IndexController {

	@Autowired
	private IndexSvc indexSvc;
	
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, Model model) {
		//주간일정
		Date today = Util4calen.getToday();
		calCalen(today, model);
		
		//RecentNews
		List<?> listview = indexSvc.selectRecentNews();
		
		model.addAttribute("listview", listview);
		
		return "main/index";
	}
	
	
    /**
     * 메인페이지에서 이전, 다음주 이동시 Ajax.
     */
    @RequestMapping(value = "/moveDate")
    public String moveDate(HttpServletRequest request, Model model) {
        String date = request.getParameter("date");

        Date today = Util4calen.getToday(date);
        
        calCalen(today, model);
        
        return "main/indexCalen";
    }
	
	/**
	 * 일자표시.
	 */
	private String calCalen(Date targetDay, Model model) {
        List<DateVO> calenList = new ArrayList<DateVO>();
        
        Date today = Util4calen.getToday();					//오늘 일자
        int month = Util4calen.getMonth(targetDay);			//월
        int week = Util4calen.getWeekOfMonth(targetDay);	//주
        
        Date fweek = Util4calen.getFirstOfWeek(targetDay);	//한 주의 시작일자
        Date lweek = Util4calen.getLastOfWeek(targetDay);	//한 주의 마지막일자
        Date preWeek = Util4calen.dateAdd(fweek, -1);		//이전 주
        Date nextWeek = Util4calen.dateAdd(lweek, 1);		//다음 주
        
        //오늘일자를 기준으로 일~월까지 한주를 calenList에 add
        while (fweek.compareTo(lweek) <= 0) {
            DateVO dvo = Util4calen.date2VO(fweek);
            dvo.setIstoday(Util4calen.dateDiff(fweek, today) == 0);
            calenList.add(dvo);
            
            fweek = Util4calen.dateAdd(fweek, 1);
        }
        
        model.addAttribute("month", month);
        model.addAttribute("week", week);
        model.addAttribute("calenList", calenList);
        model.addAttribute("preWeek", Util4calen.date2Str(preWeek));
        model.addAttribute("nextWeek", Util4calen.date2Str(nextWeek));

        return "main/index";
    }
}
