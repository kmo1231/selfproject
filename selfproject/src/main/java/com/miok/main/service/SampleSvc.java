package com.miok.main.service;

import java.util.List;

import com.miok.common.CountVO;

public interface SampleSvc {
	public List<CountVO> selectBoardGroupCount4Statistic();
	
	public void deleteChk(String[] chk);
}
