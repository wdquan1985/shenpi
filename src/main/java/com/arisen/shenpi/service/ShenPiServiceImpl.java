package com.arisen.shenpi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arisen.shenpi.dao.ShenPiMyBatisRepository;
import com.arisen.shenpi.model.ShenPi;

//@Service("shenPiService")
@Component("shenPiService")
@Transactional
public class ShenPiServiceImpl implements ShenPiService{
	@Autowired
	ShenPiMyBatisRepository repository;

	@Override
	public ShenPi findById(long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public int deleteById(long id) {
		// TODO Auto-generated method stub
		return repository.deleteById(id);
	}

	@Override
	public int save(ShenPi shenpi) {
		// TODO Auto-generated method stub
		return repository.insert(shenpi);
	}

	@Override
	public int update(ShenPi shenpi) {
		// TODO Auto-generated method stub
		return repository.update(shenpi);
	}

	@Override
	public List<ShenPi> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	
}
