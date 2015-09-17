package com.ai.przychodnia.dao;

import java.util.Date;
import java.util.List;

import com.ai.przychodnia.model.Visit;

public interface VisitDao
{
	Visit findById(int id);

	void saveVisit(Visit visit);

	void deleteVisitById(int id);

	List<Visit> findAllVisits();
	
	void deleteVisit(Visit visit);
	
	List<Date> takenTerms(int cid, int did);
	
	List<Date> takenTerms(int cid);
	
	List<Date> doctorTerms(int did);
	
	List<Visit> userVisits(int uid);
	
	List<Visit> doctorVisits(int did);
}
