package com.arisen.shenpi.service;

import java.util.List;

import com.arisen.shenpi.model.ShenPi;

public interface ShenPiService {
	ShenPi findById(long id);
	List<ShenPi> findAll();
	int deleteById(long id);
	int save(ShenPi shenpi);
	int update(ShenPi shenpi);
}
