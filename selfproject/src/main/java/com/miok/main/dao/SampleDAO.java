package com.miok.main.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.miok.common.CountVO;

@Repository
public interface SampleDAO {
	public List<CountVO> selectBoardGroupCount4Statistic();
	
	public void deleteChk(HashMap chk);
}
