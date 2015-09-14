package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Clinic;
import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;
import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.ClinicService;
import com.ai.przychodnia.service.UserService;

@Repository("doctorClinicDao")
public class DoctorClinicDaoImpl extends AbstractDao<DoctorClinicId, Doctor_Clinic> implements DoctorClinicDao {

	@Autowired
	UserService userService;
	
	@Autowired
	ClinicService clinicService;
	
	@Override
	public Doctor_Clinic findAssignById(DoctorClinicId id) {
		User doctor = userService.findById(id.getDoctor().getId());
		Clinic clinic = clinicService.findById(id.getClinic().getId());
		id.setClinic(clinic);
		id.setDoctor(doctor);
		
		return this.getByKey(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Doctor_Clinic> findAllAssignations() {
		Criteria criteria = createEntityCriteria();;
		return (List<Doctor_Clinic>) criteria.list();
	}

	@Override
	public void newAssignation(Doctor_Clinic assign) {
		persist(assign);
	}

	@Override
	public void deleteAssignationById(DoctorClinicId id) {
		Query hql = getSession().createSQLQuery(
				"delete from DOCTOR_CLINIC where USER_ID = :uid and CLINIC_ID = :cid and DAYOFWEEK = :dow");
		hql.setString("uid", Integer.toString(id.getDoctor().getId()));
		hql.setString("cid", Integer.toString(id.getClinic().getId()));
		hql.setString("dow", id.getDayOfWeek());
		hql.executeUpdate();
	}

	@Override
	public void deleteAssignation(Doctor_Clinic assign) {
		delete(assign);
	}

}
