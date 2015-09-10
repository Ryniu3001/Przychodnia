package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.DoctorClinicId;
import com.ai.przychodnia.model.Doctor_Clinic;

@Repository("doctorClinicDao")
public class DoctorClinicDaoImpl extends AbstractDao<DoctorClinicId, Doctor_Clinic> implements DoctorClinicDao {

	@Override
	public Doctor_Clinic findAssignById(DoctorClinicId id) {
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
				"delete from DOCTOR_CLINIC where USER_ID = :uid and CLINIC_ID = :cid");
		hql.setString("uid", Integer.toString(id.getDoctor().getId()));
		hql.setString("cid", Integer.toString(id.getClinic().getId()));
		hql.executeUpdate();
	}

	@Override
	public void deleteAssignation(Doctor_Clinic assign) {
		delete(assign);
	}

}
