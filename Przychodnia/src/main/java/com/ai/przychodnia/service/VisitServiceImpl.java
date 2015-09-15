package com.ai.przychodnia.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.UserDao;
import com.ai.przychodnia.dao.VisitDao;
import com.ai.przychodnia.model.Visit;

@Service
@Transactional
public class VisitServiceImpl implements VisitService
{
	@Autowired
	private VisitDao dao;
	
	@Autowired
	private UserDao userDao;

	public Visit findById(int id) {
		return dao.findById(id);
	}

	public void saveVisit(Visit visit) {
		//User patient = userDao.findById(patientId);
		dao.saveVisit(visit);
	}

	public void deleteVisitById(int id) {
		dao.deleteVisitById(id);

	}

	public List<Visit> findAllVisits() {
		return dao.findAllVisits();
	}

	public Visit findVisitById(int id) {
		return dao.findById(id);
	}
	
	public List<Date> takenTerms(int cid, int did){
		return dao.takenTerms(cid, did);
	}

}
