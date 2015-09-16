package com.ai.przychodnia.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ai.przychodnia.model.Clinic;

@Repository("clinicDao")
public class ClinicDaoImpl extends AbstractDao<Integer, Clinic> implements ClinicDao {

	@Override
	public Clinic findById(int id) {
		return getByKey(id);
	}

	//TODO Zmienic na unikalna nazwe przychodni?
	@SuppressWarnings("unchecked")
	@Override
	public List<Clinic> findByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Clinic> findAllClinics() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		return (List<Clinic>) criteria.list();
	}

	@Override
	public void newClinic(Clinic clinic) {
		persist(clinic);		
	}

	@Override
	public void deleteClinicById(int id) {
		Query hql = getSession().createSQLQuery(
				"delete from Clinic where id = :id");
		hql.setString("id", Integer.toString(id));
		hql.executeUpdate();		
	}

	@Override
	public void deleteClinic(Clinic clinic) {
		this.delete(clinic);
	}

}
