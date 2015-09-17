package com.ai.przychodnia.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;

public interface DoctorClinicService {
	
	Doctor_Clinic findAssignById(DoctorClinicId id);
	
	List<Doctor_Clinic> findAllAssignations();

	void newAssignation(Doctor_Clinic assign);
	
	void newAssignation(Doctor_Clinic assign, String[] days) throws DataIntegrityViolationException, ParseException;

	void deleteAssignationById(DoctorClinicId id);
	
	void deleteAssignation(Doctor_Clinic assign);
}
