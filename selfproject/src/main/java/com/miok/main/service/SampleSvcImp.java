package com.miok.main.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miok.common.CountVO;
import com.miok.main.dao.SampleDAO;

@Service
public class SampleSvcImp implements SampleSvc{

	@Autowired
	private SampleDAO SampleDAO;
	
	@Override
	public List<CountVO> selectBoardGroupCount4Statistic() {
		return SampleDAO.selectBoardGroupCount4Statistic();
	}

	@Override
	public void deleteChk(String[] param) {
    	HashMap hm = new HashMap();
    	hm.put("list", param) ;
    	
    	SampleDAO.deleteChk(hm);
    }
}
