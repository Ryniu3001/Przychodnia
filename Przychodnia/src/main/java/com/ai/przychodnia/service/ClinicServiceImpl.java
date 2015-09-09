package com.ai.przychodnia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.przychodnia.dao.ClinicDao;
import com.ai.przychodnia.model.Clinic;

@Service
@Transactional
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	private ClinicDao dao;
	
	@Override
	public Clinic findById(int id) {
		return dao.findById(id);
	}

	@Override
	public List<Clinic> findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Clinic> findAllClinics() {
		return dao.findAllClinics();
	}

	@Override
	public void newClinic(Clinic clinic) {
		dao.newClinic(clinic);
	}

	@Override
	public void deleteClinicById(int id) {
		dao.deleteClinicById(id);
	}

	@Override
	public void deleteClinic(Clinic clinic) {
		dao.deleteClinic(clinic);
	}

	@Override
	public void updateClinic(Clinic clinic) {
		Clinic entity = dao.findById(clinic.getId());
		if (entity != null) {
			entity.setName(clinic.getName());
		}
	}
}
