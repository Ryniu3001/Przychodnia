package com.ai.przychodnia.dao;

import java.util.List;

import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;

public interface DoctorClinicDao {
	
	Doctor_Clinic findAssignById(DoctorClinicId id);
		
	List<Doctor_Clinic> findAllAssignations();

	void newAssignation(Doctor_Clinic assign);

	void deleteAssignationById(DoctorClinicId id);
	
	void deleteAssignation(Doctor_Clinic assign);
}
