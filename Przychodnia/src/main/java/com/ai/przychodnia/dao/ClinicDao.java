package com.ai.przychodnia.dao;

import java.util.List;

import com.ai.przychodnia.model.Clinic;

public interface ClinicDao {
	Clinic findById(int id);
	
	List<Clinic> findByName(String name);
	
	List<Clinic> findAllClinics();

	void newClinic(Clinic clinic);

	void deleteClinicById(int id);
	
	void deleteClinic(Clinic clinic);
}
