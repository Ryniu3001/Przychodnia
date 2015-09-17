package com.ai.przychodnia.service;

import java.util.Date;
import java.util.List;

import com.ai.przychodnia.model.Visit;

public interface VisitService
{
	Visit findById(int id);

	void saveVisit(Visit visit);

	//void updateUser(User user);

	void deleteVisitById(int id);

	List<Visit> findAllVisits();

	Visit findVisitById(int id);
	
	List<Date> takenTerms(int cid, int did);
	
	List<Date> takenTerms(int cid);
	
	List<Date> doctorTerms(int did);
	
	List<Visit> userVisits(int uid);
	
	List<Visit> doctorVisits(int did);
	
	void updateVisit(Visit visit);

}
