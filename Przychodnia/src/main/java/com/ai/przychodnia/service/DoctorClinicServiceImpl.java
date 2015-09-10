package com.ai.przychodnia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.ClinicDao;
import com.ai.przychodnia.dao.DoctorClinicDao;
import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;

@Service
@Transactional
public class DoctorClinicServiceImpl implements DoctorClinicService {

	@Autowired
	private DoctorClinicDao dao;
	
	@Override
	public Doctor_Clinic findAssignById(DoctorClinicId id) {
		return dao.findAssignById(id);
	}

	@Override
	public List<Doctor_Clinic> findAllAssignations() {
		return dao.findAllAssignations();
	}

	@Override
	public void newAssignation(Doctor_Clinic assign) {
		dao.newAssignation(assign);
	}

	@Override
	public void deleteAssignationById(DoctorClinicId id) {
		dao.deleteAssignationById(id);
	}

	@Override
	public void deleteAssignation(Doctor_Clinic assign) {
		dao.deleteAssignation(assign);
	}

}
